package trade.math.domain.wantList;

import trade.math.domain.tradeItem.TradeItem;
import trade.math.domain.tradeItem.TradeItemService;
import trade.math.model.TradeUser;

import java.util.List;
import java.util.Map;

public interface WantListService {

    WantList findByItem(TradeItem item);

    List<WantList> findByItems(List<TradeItem> items);

    WantList save(WantList wantList);

    void addWant(TradeItem offer, TradeItem want);

    void setWants(TradeItem offer, List<TradeItem> wants);

    void setWants(WantList wantList, List<TradeItem> wants);

    void deleteAll();

    void deleteWantList(TradeItem offer);

    void deleteWant(TradeItem offer, TradeItem want);

    WantListEntry findEntry(TradeItem offer, TradeItem want);

    List<WantListEntry> findEntries(TradeItem offer);

/*
    void setWants(TradeItem offer, Map<TradeItem, Integer> wantsAndPriorities);
*/
}
