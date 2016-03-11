package trade.math.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import trade.math.model.TradeList;

@Transactional
public interface TradeListRepository extends JpaRepository<TradeList, Long> {

    @Query("select t from TradeList t where t.creationTime = (select max(tt.creationTime) from TradeList tt)")
    TradeList findRecentList();

}
