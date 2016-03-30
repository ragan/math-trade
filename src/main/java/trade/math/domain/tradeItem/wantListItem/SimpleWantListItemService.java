package trade.math.domain.tradeItem.wantListItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trade.math.domain.tradeItem.TradeItemService;
import trade.math.repository.WantListItemRepository;

import javax.transaction.Transactional;

/**
 * Created by daniel on 25.03.16.
 */
@Service
@Transactional
public class SimpleWantListItemService implements WantListItemService {

    private final WantListItemRepository wantListItemRepository;

    @Autowired
    public SimpleWantListItemService(WantListItemRepository wantListItemRepository) {
        this.wantListItemRepository = wantListItemRepository;
    }

    @Override
    public WantListItem save(WantListItem wantListItem) {
        return wantListItemRepository.save(wantListItem);
    }

    @Override
    public boolean update(WantListItem wantListItem) {
        try{
            wantListItemRepository.save(wantListItem);
            return true;
        }catch (Exception exception){
            exception.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(WantListItem wantListItem, TradeItemService tradeItemService) {
        try {
            wantListItem.getOfferTradeItem().getWantList().remove(wantListItem);
            tradeItemService.update(wantListItem.getOfferTradeItem());

            wantListItemRepository.delete(wantListItem);
            return true;
        }catch (Exception exception){
            exception.printStackTrace();
        }

        return false;
    }
}
