package trade.math.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trade.math.domain.tradeItem.wantListItem.WantListItem;

/**
 * Created by daniel on 23.03.16.
 */
public interface WantListItemRepository extends JpaRepository<WantListItem, Long> {
}
