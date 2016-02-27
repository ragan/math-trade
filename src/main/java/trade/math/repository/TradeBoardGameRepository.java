package trade.math.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import trade.math.model.TradeBoardGame;

import java.util.List;

public interface TradeBoardGameRepository extends JpaRepository<TradeBoardGame, Long> {

    @Query(value = "SELECT NAMES FROM TRADE_BOARD_GAME_NAMES WHERE NAMES LIKE %:name%", nativeQuery = true)
    List<TradeBoardGame> findByName(@Param(value = "name") String name);

}
