package trade.math.domain.wantListItem;

import trade.math.domain.tradeItem.TradeItem;
import trade.math.domain.tradeItem.TradeItemService;

import java.util.List;

public interface WantListService {

    List<WantListItemDTO> findWantListByOfferTradeItem(TradeItem offerTradeItem);
    WantListEntry save(WantListEntry wantListEntry);
    boolean update(WantListEntry wantListEntry);
    boolean delete(WantListEntry wantListEntry, TradeItemService tradeItemService);
}
