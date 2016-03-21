package trade.math.service.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trade.math.model.TradeBoardGameProperties;
import trade.math.model.TradeItem;
import trade.math.repository.TradeBoardGamePropertiesRepository;
import trade.math.service.TradeBoardGamePropertiesService;

import javax.transaction.Transactional;

/**
 * Created by daniel on 11.03.16.
 */
@Service
@Transactional
public class SimpleTradeBoardGamePropertiesService implements TradeBoardGamePropertiesService {

    private TradeBoardGamePropertiesRepository tradeBoardGamePropertiesRepository;

    @Autowired
    public SimpleTradeBoardGamePropertiesService(TradeBoardGamePropertiesRepository tradeBoardGamePropertiesRepository) {
        this.tradeBoardGamePropertiesRepository = tradeBoardGamePropertiesRepository;
    }

    @Override
    public TradeBoardGameProperties findByTradeItem(TradeItem tradeItem) {
        return tradeBoardGamePropertiesRepository.findByTradeItem(tradeItem);
    }

    @Override
    public TradeBoardGameProperties save(TradeBoardGameProperties tradeBoardGameProperties) {
        if(tradeBoardGameProperties == null || findByTradeItem(tradeBoardGameProperties.getTradeItem()) != null)
            return null;

        return tradeBoardGamePropertiesRepository.save(tradeBoardGameProperties);
    }

    @Override
    public boolean deleteByTradeItem(TradeItem tradeItem) {

        TradeBoardGameProperties tradeBoardGameProperties = findByTradeItem(tradeItem);

        if(tradeBoardGameProperties != null) {
            tradeBoardGamePropertiesRepository.delete(tradeBoardGameProperties);

            return true;
        }

        return false;
    }

    @Override
    public void deleteAll() {
        tradeBoardGamePropertiesRepository.deleteAll();
    }
}
