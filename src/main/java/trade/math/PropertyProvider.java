package trade.math;


import trade.math.domain.tradeItem.TradeItemDTO;

public interface PropertyProvider<T> {

    T getProperty(TradeItemDTO tradeItemDTO);

}
