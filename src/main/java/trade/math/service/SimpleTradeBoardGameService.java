package trade.math.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trade.math.bgsearch.BoardGameSearchResult;
import trade.math.bgsearch.bgg.BggBoardGameSearchResultItem;
import trade.math.bgsearch.bgg.BggGameSearchResult;
import trade.math.model.TradeBoardGame;
import trade.math.model.TradeBoardGameTitle;
import trade.math.model.dto.TradeBoardGameDTO;
import trade.math.repository.TradeBoardGameRepository;
import trade.math.repository.TradeBoardGameTitleRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class SimpleTradeBoardGameService implements TradeBoardGameService {

    private final TradeBoardGameRepository tradeBoardGameRepository;
    private final TradeBoardGameTitleRepository tradeBoardGameTitleRepository;

    @Autowired
    public SimpleTradeBoardGameService(TradeBoardGameRepository tradeBoardGameRepository,
                                       TradeBoardGameTitleRepository tradeBoardGameTitleRepository) {
        this.tradeBoardGameRepository = tradeBoardGameRepository;
        this.tradeBoardGameTitleRepository = tradeBoardGameTitleRepository;
    }

    @Override
    public List<TradeBoardGame> save(List<TradeBoardGameDTO> tradeBoardGames) {
        //nie wiem czy tak jest ok... Powinno chyba zapisywać wszystkie w jednej tranzakcji
        return tradeBoardGameRepository.save(tradeBoardGames.stream().map(this::save).collect(toList()));
    }

    @Override
    public TradeBoardGame save(TradeBoardGameDTO tradeBoardGameDTO) {
        TradeBoardGame tradeBoardGame = tradeBoardGameDTO.makeTradeBoardGame();
        tradeBoardGameTitleRepository.save(tradeBoardGame.getNames());
        return tradeBoardGameRepository.save(tradeBoardGame);
    }

    @Override
    public List<String> searchTitlesLike(String title) {
        return tradeBoardGameTitleRepository.findByTitle(title).stream()
                .map(TradeBoardGameTitle::getTitle)
                .collect(toList());
    }

    @Override
    public BoardGameSearchResult searchByName(String name) {
        if ("".equals(name) || name.length() < 3)
            return new BggGameSearchResult();
        return new BggGameSearchResult(
                tradeBoardGameTitleRepository.findByTitle(name.toLowerCase())
                        .stream()
                        .map(t -> new BggBoardGameSearchResultItem(
                                t.getTitle(), t.getTradeBoardGame().getBggId(), t.getTradeBoardGame().getThumbnailUrl()
                        ))
                        .limit(10)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<TradeBoardGameDTO> findAll() {
        return tradeBoardGameRepository.findAll().stream().map(TradeBoardGameDTO::new).collect(toList());
    }

    @Override
    public void deleteAll() {
        tradeBoardGameTitleRepository.deleteAll();
        tradeBoardGameRepository.deleteAll();
    }
}
