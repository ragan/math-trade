package trade.math.service;

import trade.math.model.TradeBoardGame;
import trade.math.model.dto.TradeBoardGameDTO;

import java.util.List;

/**
 * Created by karol on 25.02.16.
 */
public interface TradeBoardGameService {

    List<TradeBoardGame> save(List<TradeBoardGameDTO> tradeBoardGames);

    TradeBoardGame save(TradeBoardGameDTO tradeBoardGame);

    List<String> searchTitlesLike(String title);

    List<TradeBoardGameDTO> searchByName(String name);

    List<TradeBoardGameDTO> findAll();

    void deleteAll();
}
