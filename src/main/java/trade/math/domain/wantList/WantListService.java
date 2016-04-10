package trade.math.domain.wantList;

import trade.math.domain.tradeItem.TradeItem;
import trade.math.domain.tradeItem.TradeItemService;

import java.util.List;

public interface WantListService {

    /**
     * Minimal value of priority for want list entry.
     */
    int PRIORITY_MIN = 1;

    /**
     * Maximal value of priority for want list entry.
     */
    int PRIORITY_MAX = 100;

    WantList findByItem(TradeItem item);

    WantList save(WantList wantList);

    WantListEntry setWant(TradeItem offer, TradeItem want);

    /**
     * @param offer Item offered
     * @param want Item wanted
     * @param p priority value (between PRIORITY_MIN and PRIORITY_MAX)
     * @throws IllegalArgumentException when priority value is out of set bounds.
     */
    void setPriority(TradeItem offer, TradeItem want, int p);

    void deleteAll();

    void deleteWant(TradeItem offer, TradeItem want);

    WantListEntry findEntry(TradeItem offer, TradeItem want);

    void setWants(TradeItem offer, List<TradeItem> wants);
}
