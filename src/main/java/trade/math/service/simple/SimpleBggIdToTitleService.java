package trade.math.service.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trade.math.model.TradeBoardGameTitle;
import trade.math.repository.TradeBoardGameRepository;
import trade.math.service.BggIdToTitleService;

/**
 * Created by karol on 17.02.16.
 */
@Service
@Transactional
public class SimpleBggIdToTitleService implements BggIdToTitleService {

    @Autowired
    private TradeBoardGameRepository tradeBoardGameRepository;

    @Override
    public TradeBoardGameTitle getBoardGameTitle(int bggId) {
        return tradeBoardGameRepository.findOneByBggId(bggId).map(g -> g.getNames().get(0))
                .orElse(null);
    }

    @Override
    public String getTitle(int bggId) {
        return tradeBoardGameRepository.findOneByBggId(bggId).map(g -> g.getNames().get(0).getTitle()).orElse("");
    }

}
