package trade.math.domain.tradeList;

import java.util.List;
import java.util.Optional;

public interface TradeListService {

    /**
     * Creates new trade list with no items and open state. Sets all other list state to archival state.
     *
     * @return list created
     */
    TradeList createNewList();

    List<TradeList> findAll();

    Optional<TradeList> findMostRecentList();

    void deleteAll();

    void setState(TradeListState closed);

    TradeList findById(Long tradeListId);

    TradeListStatusDTO getTradeListStatus();

}
