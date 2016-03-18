package trade.math.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trade.math.model.TradeItem;
import trade.math.model.TradeList;

import java.util.List;

/**
 * Created by karol on 17.02.16.
 */
public interface TradeItemRepository extends JpaRepository<TradeItem, Long> {
    List<TradeItem> findByTradeList(TradeList tradeList);
    List<TradeItem> findByTradeListAndTitleAllIgnoreCaseContainingOrderByTitleAsc(TradeList tradeList, String title);
    List<TradeItem> findByTradeListAndTitleAllIgnoreCaseOrderByTitleAsc(TradeList tradeList, String title);
    List<TradeItem> findByTitleAllIgnoreCaseContainingOrderByTitleAsc(String title);
    List<TradeItem> findByTitleAllIgnoreCaseOrderByTitleAsc(String title);
}
