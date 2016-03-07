package trade.math.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trade.math.model.TradeBoardGameTitle;

import java.util.List;
import java.util.Optional;

public interface TradeBoardGameTitleRepository extends JpaRepository<TradeBoardGameTitle, Long> {

    List<TradeBoardGameTitle> findByTitleAllIgnoreCaseContainingOrderByTitleAsc(String title);

    List<TradeBoardGameTitle> findByTitleAllIgnoreCaseOrderByTitleAsc(String title);

    Optional<TradeBoardGameTitle> findOneByTitleAllIgnoreCase(String title);
}
