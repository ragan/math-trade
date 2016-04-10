package trade.math.domain.wantList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trade.math.domain.tradeItem.TradeItem;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WantListServiceImpl implements WantListService {

    private final WantListRepository wantListRepository;

    private final WantListEntryRepository wantListEntryRepository;

    @Autowired
    public WantListServiceImpl(
            WantListRepository wantListRepository,
            WantListEntryRepository wantListEntryRepository
    ) {
        this.wantListRepository = wantListRepository;
        this.wantListEntryRepository = wantListEntryRepository;
    }

    @Override
    public WantList findByItem(TradeItem item) {
        return wantListRepository.findByItem(item).orElseThrow(WantListNotFoundException::new);
    }

    @Override
    @Transactional
    public WantList save(WantList wantList) {
        return wantListRepository.save(wantList);
    }

    @Override
    public WantListEntry setWant(TradeItem offer, TradeItem want) {
        if (offer.getOwner().getId().equals(want.getOwner().getId()))
            throw new IllegalArgumentException("Items have same owner");
        WantList wantList = findByItem(offer);
        wantListEntryRepository.findByWantListAndItem(wantList, want).ifPresent(e -> {
            throw new IllegalStateException("Offer is already present.");
        });
        WantListEntry entry = new WantListEntry();
        entry.setItem(want);
        entry.setPriority(PRIORITY_MIN);
        entry.setWantList(wantList);
        wantListEntryRepository.save(entry);

        wantList.getEntries().add(entry);
        save(wantList);

        return entry;
    }

    @Override
    public void setPriority(TradeItem offer, TradeItem want, int p) {
        if (p < PRIORITY_MIN || p > PRIORITY_MAX)
            throw new IllegalArgumentException("Priority not between bounds.");
        WantListEntry entry = findEntry(offer, want);
        entry.setPriority(p);
        wantListEntryRepository.save(entry);
    }

    @Override
    public void deleteAll() {
        wantListEntryRepository.deleteAll();
        wantListRepository.deleteAll();
    }

    @Override
    public void deleteWant(TradeItem offer, TradeItem want) {
        wantListEntryRepository.delete(findEntry(offer, want));
    }

    @Override
    public WantListEntry findEntry(TradeItem offer, TradeItem want) {
        WantList wantList = findByItem(offer);
        return findEntry(wantList, want);
    }

    private WantListEntry findEntry(WantList wantList, TradeItem want) {
        return wantListEntryRepository.findByWantListAndItem(wantList, want)
                .orElseThrow(WantListEntryNotFoundException::new);
    }

    @Override
    public void setWants(TradeItem offer, List<TradeItem> wants) {
        WantList wantList = findByItem(offer);
        wantListEntryRepository.delete(wantList.getEntries());
        List<WantListEntry> entries = wants.stream().map(item -> {
            WantListEntry entry = new WantListEntry();
            entry.setWantList(wantList);
            entry.setPriority(PRIORITY_MIN);
            entry.setItem(item);
            return entry;
        }).collect(Collectors.toList());
        wantListEntryRepository.save(entries);
        wantList.setEntries(entries);
        wantListRepository.save(wantList);
    }
}
