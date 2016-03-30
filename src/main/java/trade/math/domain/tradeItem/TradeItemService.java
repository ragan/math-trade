package trade.math.domain.tradeItem;

import org.springframework.data.domain.Pageable;
import trade.math.form.NewTradeItemForm;
import trade.math.domain.tradeList.TradeList;
import trade.math.wrappers.PageWrapper;

import java.util.List;

/**
 * Created by karol on 17.02.16.
 */
public interface TradeItemService {

    TradeItem save(NewTradeItemForm newTradeItemForm, String username);

    TradeItem save(NewTradeItemForm newTradeItemForm, String username, TradeList tradeList);

    List<TradeItem> findByTradeList(TradeList tradeList);

    List<TradeItem> findByTradeList(Long tradeListId);

    List<TradeItemDTO> findByRecentTradeListAndNameAndNotOwner(String name, String userName);

    List<TradeItem> findByRecentTradeList();

    List<TradeItem> findByRecentTradeListAndOwner(String userName);

    TradeItem update(TradeItem item);

    boolean updateWantList(Long tradeItemId, Long[] wantIds);

    List<TradeItem> findAll();

    TradeItem findById(Long itemId);

    PageWrapper<TradeItemDTO> findAll(Pageable pageable);

    PageWrapper<TradeItemDTO> findAll(Pageable pageable, boolean isAdmin, String userName);

    PageWrapper<TradeItemDTO> findAllByRecentTradeList(Pageable pageable, boolean isAdmin, String userName);

    void deleteAll(boolean isAdmin);

    boolean deleteById(Long itemId, boolean isAdmin, String userName);
}
