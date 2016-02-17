package trade.math.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trade.math.form.NewTradeItemForm;
import trade.math.model.TradeItem;
import trade.math.repository.TradeItemRepository;

import java.util.List;

/**
 * Created by karol on 17.02.16.
 */
@Service
public class SimpleTradeItemService implements TradeItemService {

    private final TradeItemRepository tradeItemRepository;

    @Autowired
    public SimpleTradeItemService(TradeItemRepository tradeItemRepository) {
        this.tradeItemRepository = tradeItemRepository;
    }

    @Override
    public TradeItem save(NewTradeItemForm tradeItemForm) {
        TradeItem tradeItem = new TradeItem();
        tradeItem.setBggId(tradeItemForm.getBggId());
        tradeItem.setDescription(tradeItemForm.getDescription());
        tradeItem.setForTrade(false);
        return tradeItemRepository.save(tradeItem);
    }

    @Override
    public List<TradeItem> findAll() {
        return tradeItemRepository.findAll();
    }

    @Override
    public TradeItem findById(Long itemId) {
        return tradeItemRepository.findOne(itemId);
    }
}
