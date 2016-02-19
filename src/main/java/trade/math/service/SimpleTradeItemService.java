package trade.math.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    private final BggIdToTitleService bggIdToTitleService;

    @Autowired
    public SimpleTradeItemService(TradeItemRepository tradeItemRepository,
                                  BggIdToTitleService bggIdToTitleService) {
        this.tradeItemRepository = tradeItemRepository;
        this.bggIdToTitleService = bggIdToTitleService;
    }

    @Override
    public TradeItem save(NewTradeItemForm tradeItemForm) {
        TradeItem tradeItem = new TradeItem();
        tradeItem.setBggId(tradeItemForm.getBggId());
        tradeItem.setDescription(tradeItemForm.getDescription());
        tradeItem.setForTrade(false);
        tradeItem.setTitle(bggIdToTitleService.getTitle(tradeItemForm.getBggId()));
        return tradeItemRepository.save(tradeItem);
    }

    @Override
    public List<TradeItem> findAll() {
        return tradeItemRepository.findAll();
    }

    @Override
    public List<TradeItem> findWithPagination(int page, int itemPerPage) {
        return tradeItemRepository.findAll(new PageRequest(page, itemPerPage)).getContent();
    }

    @Override
    public void clearTradeItems() {
        tradeItemRepository.deleteAll();
    }

    @Override
    public TradeItem findById(Long itemId) {
        return tradeItemRepository.findOne(itemId);
    }
}
