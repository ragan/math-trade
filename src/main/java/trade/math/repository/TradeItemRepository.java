package trade.math.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import trade.math.model.TradeItem;
import trade.math.domain.tradeList.TradeList;
import trade.math.model.TradeUser;

import java.util.List;

/**
 * Created by karol on 17.02.16.
 */
public interface TradeItemRepository extends JpaRepository<TradeItem, Long> {
    List<TradeItem> findByTradeList(TradeList tradeList);
    Page<TradeItem> findByTradeList(TradeList tradeList, Pageable pageable);
    List<TradeItem> findByTradeListAndOwner(TradeList tradeList, TradeUser tradeUser);
    List<TradeItem> findByTradeListAndTitleAllIgnoreCaseContainingAndOwnerNotOrderByTitleAsc(TradeList tradeList, String title, TradeUser owner);
    List<TradeItem> findByTradeListAndTitleAllIgnoreCaseAndOwnerNotOrderByTitleAsc(TradeList tradeList, String title, TradeUser owner);
//    List<TradeItem> findByTitleAllIgnoreCaseContainingOrderByTitleAsc(String title);
//    List<TradeItem> findByTitleAllIgnoreCaseOrderByTitleAsc(String title);
}
