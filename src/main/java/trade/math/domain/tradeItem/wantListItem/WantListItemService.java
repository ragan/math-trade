package trade.math.domain.tradeItem.wantListItem;

import trade.math.domain.tradeItem.TradeItem;
import trade.math.domain.tradeItem.TradeItemService;

import java.util.List;

/**
 * Created by daniel on 24.03.16.
 */
public interface WantListItemService {

    List<WantListItemDTO> findWantListByOfferTradeItem(TradeItem offerTradeItem);
    WantListItem save(WantListItem wantListItem);
    boolean update(WantListItem wantListItem);
    boolean delete(WantListItem wantListItem, TradeItemService tradeItemService);
}
