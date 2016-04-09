package trade.math.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trade.math.domain.wantListItem.WantListEntry;

/**
 * Created by daniel on 23.03.16.
 */
public interface WantListItemRepository extends JpaRepository<WantListEntry, Long> {
//    List<WantListItem> findByOfferTradeItem(TradeItem offerTradeItem);
}
