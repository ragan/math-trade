package trade.math.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import trade.math.model.TradeBoardGameTitle;

import java.util.List;

public interface TradeBoardGameTitleRepository extends JpaRepository<TradeBoardGameTitle, Long> {

    @Query("select t from TradeBoardGameTitle t where t.title like %:title%")
    List<TradeBoardGameTitle> findByTitle(@Param("title") String title);

}
