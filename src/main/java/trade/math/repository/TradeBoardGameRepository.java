package trade.math.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trade.math.model.TradeBoardGame;

public interface TradeBoardGameRepository extends JpaRepository<TradeBoardGame, Long> {
}
