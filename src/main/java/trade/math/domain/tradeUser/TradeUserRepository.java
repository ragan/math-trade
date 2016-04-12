package trade.math.domain.tradeUser;

import org.springframework.data.jpa.repository.JpaRepository;
import trade.math.TradeUserRole;
import trade.math.model.TradeUser;

import java.util.List;
import java.util.Optional;

interface TradeUserRepository extends JpaRepository<TradeUser, Long> {

    Optional<TradeUser> findOneByUsername(String username);

    List<TradeUser> findAllByRole(TradeUserRole tradeUserRole);

}
