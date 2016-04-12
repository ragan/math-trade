package trade.math.domain.tradeItem;

import org.springframework.data.domain.Pageable;
import trade.math.domain.tradeList.TradeList;
import trade.math.form.NewTradeItemForm;
import trade.math.model.TradeUser;
import trade.math.wrappers.PageWrapper;

import java.util.List;
import java.util.Optional;

public interface TradeItemService {

    TradeItem save(NewTradeItemForm newTradeItemForm, TradeUser user);

    TradeItem save(NewTradeItemForm newTradeItemForm, TradeUser user, TradeList tradeList);

    List<TradeItem> findByTradeList(TradeList tradeList);

    List<TradeItem> findByRecentTradeListAndNameAndNotOwner(String name, TradeUser user);

    List<TradeItem> findByRecentTradeList();

    List<TradeItem> findByRecentTradeListAndOwner(TradeUser user);

    List<TradeItem> findAll();

    TradeItem findById(Long itemId);

    List<TradeItem> findByIds(List<Long> ids);

    List<TradeItem> findByOwner(TradeUser owner);

    PageWrapper<TradeItemDTO> findAll(Pageable pageable);

    PageWrapper<TradeItemDTO> findAll(Pageable pageable, boolean isAdmin, TradeUser user);

    PageWrapper<TradeItemDTO> findAllByRecentTradeList(Pageable pageable, boolean isAdmin, TradeUser user);

    void deleteById(Long itemId);

    void deleteAll();

    boolean canDelete(TradeItem item, TradeUser user);

    boolean canDelete(TradeItem item, Optional<TradeUser> user);

    String generateTradeWantListTM(TradeUser user);
}
