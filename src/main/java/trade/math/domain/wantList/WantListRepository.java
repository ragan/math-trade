package trade.math.domain.wantList;

import org.springframework.data.jpa.repository.JpaRepository;
import trade.math.domain.tradeItem.TradeItem;

import java.util.Optional;

interface WantListRepository extends JpaRepository<WantList, Long> {
    Optional<WantList> findByItem(TradeItem item);
}
