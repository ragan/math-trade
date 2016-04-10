package trade.math.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trade.math.TradeUserRole;
import trade.math.model.TradeUser;

import java.util.List;

public interface TradeUserRepository extends JpaRepository<TradeUser, Long> {

    TradeUser findOneByUsername(String username);

    List<TradeUser> findAllByRole(TradeUserRole tradeUserRole);

}
