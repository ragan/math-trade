package trade.math.service;

import trade.math.model.TradeBoardGameTitle;

/**
 * Created by karol on 17.02.16.
 */
public interface BggIdToTitleService {

    TradeBoardGameTitle getBoardGameTitle(int bggId);

    String getTitle(int bggId);

}
