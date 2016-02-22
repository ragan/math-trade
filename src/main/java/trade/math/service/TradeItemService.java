package trade.math.service;

import org.springframework.data.domain.Pageable;
import trade.math.form.NewTradeItemForm;
import trade.math.model.TradeItem;
import trade.math.wrappers.PageWrapper;

import java.util.List;

/**
 * Created by karol on 17.02.16.
 */
public interface TradeItemService {

    TradeItem save(NewTradeItemForm tradeItemForm);

    List<TradeItem> findAll();

    TradeItem findById(Long itemId);

    PageWrapper<TradeItem> findAll(Pageable pageable);

    void clearTradeItems();
}
