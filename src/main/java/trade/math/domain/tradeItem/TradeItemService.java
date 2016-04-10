package trade.math.domain.tradeItem;

import org.springframework.data.domain.Pageable;
import trade.math.domain.wantList.WantListDTO;
import trade.math.form.NewTradeItemForm;
import trade.math.domain.tradeList.TradeList;
import trade.math.model.TradeUser;
import trade.math.wrappers.PageWrapper;

import java.util.List;

public interface TradeItemService {

    TradeItem save(NewTradeItemForm newTradeItemForm, String username);

    TradeItem save(NewTradeItemForm newTradeItemForm, String username, TradeList tradeList);

    List<TradeItem> findByTradeList(TradeList tradeList);

    List<TradeItemDTO> findByRecentTradeListAndNameAndNotOwner(String name, String userName);

    List<TradeItem> findByRecentTradeList();

    List<TradeItem> findByRecentTradeListAndOwner(String userName);

    TradeItem update(TradeItem item);

    List<TradeItem> findAll();

    TradeItem findById(Long itemId);

    WantListDTO findByIdWantItem(Long itemId);

    PageWrapper<TradeItemDTO> findAll(Pageable pageable);

    PageWrapper<TradeItemDTO> findAll(Pageable pageable, boolean isAdmin, String userName);

    PageWrapper<TradeItemDTO> findAllByRecentTradeList(Pageable pageable, boolean isAdmin, String userName);

    void deleteById(Long itemId);

    void deleteAll();

    boolean canDelete(TradeItem item, TradeUser tradeUser);

    boolean canDelete(TradeItem item, String username);

    void makeGroupLists();

    String generateTradeWantListTM(String userName);
}
