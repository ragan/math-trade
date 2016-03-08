package trade.math.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trade.math.model.TradeCategory;

/**
 * Created by daniel on 08.03.16.
 */
public interface TradeCategoryRepository extends JpaRepository<TradeCategory, Long> {
}
