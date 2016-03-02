package trade.math.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trade.math.model.TradeBoardGame;

import java.util.Optional;

public interface TradeBoardGameRepository extends JpaRepository<TradeBoardGame, Long> {
    Optional<TradeBoardGame> findOneByBggId(int bggId);
}
