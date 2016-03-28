package trade.math;

import trade.math.model.dto.TradeItemDTO;

public interface PropertyProvider<T> {

    T getProperty(TradeItemDTO tradeItemDTO);

}
