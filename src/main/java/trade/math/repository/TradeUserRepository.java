package trade.math.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trade.math.model.TradeUser;

/**
 * Created by karol on 18.02.16.
 */
public interface TradeUserRepository extends JpaRepository<TradeUser, Long> {
    TradeUser findOneByUsername(String username);
}
