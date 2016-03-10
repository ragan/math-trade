package trade.math.service;

import trade.math.model.TradeItem;
import trade.math.model.TradeList;
import trade.math.model.TradeListState;

import java.util.List;

public interface TradeListService {

    /**
     * Creates new trade list with no items and open state. Sets all other list state to archival state.
     *
     * @return list created
     */
    TradeList createNewList();

    List<TradeList> findAll();

    TradeList findMostRecentList();

    void deleteAll();

    void setState(TradeListState closed);

    TradeList findById(Long tradeListId);

//    void addItemToList(TradeItem tradeItem);
}
