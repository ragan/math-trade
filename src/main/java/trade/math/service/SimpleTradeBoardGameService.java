package trade.math.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trade.math.model.TradeBoardGame;
import trade.math.model.TradeBoardGameTitle;
import trade.math.model.dto.TradeBoardGameDTO;
import trade.math.repository.TradeBoardGameRepository;
import trade.math.repository.TradeBoardGameTitleRepository;

import java.util.List;

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
        return tradeBoardGameRepository.save(tradeBoardGames.stream().map(this::save).collect(toList()));
    }

    @Override
    public TradeBoardGame save(TradeBoardGameDTO tradeBoardGameDTO) {
        TradeBoardGame tradeBoardGame = tradeBoardGameDTO.getTradeBoardGame();
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
    public List<TradeBoardGameDTO> searchByName(String name) {
        return tradeBoardGameTitleRepository.findByTitle(name).stream()
                .map(t -> new TradeBoardGameDTO(t.getTradeBoardGame()))
                .collect(toList());
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
