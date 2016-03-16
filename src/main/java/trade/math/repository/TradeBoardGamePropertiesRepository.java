package trade.math.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trade.math.model.TradeBoardGameProperties;
import trade.math.model.TradeItem;

/**
 * Created by daniel on 11.03.16.
 */
public interface TradeBoardGamePropertiesRepository extends JpaRepository<TradeBoardGameProperties, Long> {

    TradeBoardGameProperties findByTradeItem(TradeItem tradeItem);

}
