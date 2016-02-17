package trade.math.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trade.math.model.TradeItem;

/**
 * Created by karol on 17.02.16.
 */
public interface TradeItemRepository extends JpaRepository<TradeItem, Long> {
}
