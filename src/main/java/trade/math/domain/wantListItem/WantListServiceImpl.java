package trade.math.domain.wantListItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trade.math.domain.tradeItem.TradeItem;
import trade.math.domain.tradeItem.TradeItemService;
import trade.math.repository.WantListItemRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class WantListServiceImpl implements WantListService {

    private final WantListItemRepository wantListItemRepository;

    @Autowired
    public WantListServiceImpl(WantListItemRepository wantListItemRepository) {
        this.wantListItemRepository = wantListItemRepository;
    }

    @Override
    public List<WantListItemDTO> findWantListByOfferTradeItem(TradeItem offerTradeItem) {
//        return wantListItemRepository.findByOfferTradeItem(offerTradeItem).stream()
//                .parallel()
//                .map(wantListItem -> new WantListItemDTO(wantListItem.getWant().getId(),
//                        wantListItem.getWant().getTitle(),
//                        wantListItem.getPriority()))
//                .sorted((o1, o2) -> Integer.compare(o1.getPriority(), o2.getPriority()))
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public WantListEntry save(WantListEntry wantListEntry) {
        return wantListItemRepository.save(wantListEntry);
    }

    @Override
    public boolean update(WantListEntry wantListEntry) {
        try {
            wantListItemRepository.save(wantListEntry);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(WantListEntry wantListEntry, TradeItemService tradeItemService) {
//        try {
//            wantListItem.getOffer().getWantList().remove(wantListItem);
//            tradeItemService.update(wantListItem.getOffer());
//
//            wantListItemRepository.delete(wantListItem);
//            return true;
//        }catch (Exception exception){
//            exception.printStackTrace();
//        }
//
//        return false;
        wantListItemRepository.delete(wantListEntry);
        return true;
    }
}
