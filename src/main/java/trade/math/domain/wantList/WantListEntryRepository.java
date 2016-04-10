package trade.math.domain.wantList;

import org.springframework.data.jpa.repository.JpaRepository;
import trade.math.domain.tradeItem.TradeItem;

import java.util.Optional;

interface WantListEntryRepository extends JpaRepository<WantListEntry, Long> {
    Optional<WantListEntry> findByWantListAndItem(WantList wantList, TradeItem item);
}
