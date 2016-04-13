package trade.math.domain.wantList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trade.math.domain.tradeItem.TradeItem;
import trade.math.model.TradeUser;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

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
    public List<WantList> findByItems(List<TradeItem> items) {
        return items.stream().map(this::findByItem).collect(toList());
    }

    @Override
    @Transactional
    public WantList save(WantList wantList) {
        return wantListRepository.save(wantList);
    }

    @Override
    public void addWant(TradeItem offer, TradeItem want) {
        WantList wantList = findByItem(offer);
        List<TradeItem> items = wantList.getEntries().stream().map(WantListEntry::getItem).collect(toList());
        items.add(want);
        setWants(offer, items);
    }

    @Override
    public void setWants(TradeItem offer, List<TradeItem> wants) {
        setWants(findByItem(offer), wants);
    }

    @Override
    public void setWants(WantList wantList, List<TradeItem> wants) {
        if (isSameOwner(wantList.getItem(), wants))
            throw new IllegalArgumentException("Items have same owner");
        List<WantListEntry> entries = wants.stream().map(i -> new WantListEntry(wantList, i, wants.indexOf(i) + 1)).collect(toList());
        wantListEntryRepository.delete(wantList.getEntries());
        wantListEntryRepository.save(entries);
    }

    private boolean isSameOwner(TradeItem item, List<TradeItem> items) {
        return !items.stream().filter(i -> isSameOwner(item, i)).collect(toList()).isEmpty();
    }

    private boolean isSameOwner(TradeItem item0, TradeItem item1) {
        return item0.getOwner().getId().equals(item1.getOwner().getId());
    }

    @Override
    public void deleteAll() {
        wantListEntryRepository.deleteAll();
        wantListRepository.deleteAll();
    }

    @Override
    public void deleteWantList(TradeItem offer) {
        WantList wantList = findByItem(offer);
        wantListEntryRepository.delete(wantList.getEntries());
        wantListRepository.delete(wantList);
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

    @Override
    public List<WantListEntry> findEntries(TradeItem offer) {
        return findByItem(offer).getEntries();
    }

    private WantListEntry findEntry(WantList wantList, TradeItem want) {
        return wantListEntryRepository.findByWantListAndItem(wantList, want)
                .orElseThrow(WantListEntryNotFoundException::new);
    }

}
