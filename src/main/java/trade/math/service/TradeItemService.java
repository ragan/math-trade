package trade.math.service;

import trade.math.form.NewTradeItemForm;
import trade.math.model.TradeItem;

import java.util.List;

/**
 * Created by karol on 17.02.16.
 */
public interface TradeItemService {

    TradeItem save(NewTradeItemForm tradeItemForm);

    List<TradeItem> findAll();

    TradeItem findById(Long itemId);
}
