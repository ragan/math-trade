package trade.math.service;

import org.springframework.data.domain.Pageable;
import trade.math.form.NewTradeItemForm;
import trade.math.model.TradeItem;
import trade.math.model.TradeList;
import trade.math.model.dto.TradeItemDTO;
import trade.math.wrappers.PageWrapper;

import java.security.Principal;
import java.util.List;

/**
 * Created by karol on 17.02.16.
 */
public interface TradeItemService {

    TradeItem save(NewTradeItemForm newTradeItemForm, String username);

    TradeItem save(NewTradeItemForm newTradeItemForm, String username, TradeList tradeList);

    List<TradeItem> findByTradeList(TradeList tradeList);

    List<TradeItem> findByTradeList(Long tradeListId);

    List<TradeItem> findByRecentTradeList();

    List<TradeItem> findAll();

    TradeItem findById(Long itemId);

    PageWrapper<TradeItemDTO> findAll(Pageable pageable);

    PageWrapper<TradeItemDTO> findAll(Pageable pageable, boolean isAdmin, String userName);

    void deleteAll(boolean isAdmin);

    boolean deleteById(Long itemId, boolean isAdmin, String userName);
}
