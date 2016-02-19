package trade.math.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import trade.math.form.NewTradeItemForm;
import trade.math.model.TradeItem;
import trade.math.repository.TradeItemRepository;

import java.util.ArrayList;
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
    public List<TradeItem> findWithPagination(int page, int itemsPerPage) {
        if(page < 1) page = 1;
        if (itemsPerPage < 1) itemsPerPage = 10;
        return tradeItemRepository.findAll(new PageRequest(page - 1, itemsPerPage)).getContent();
    }

    @Override
    public int getPageCount(int itemsPerPage) {
        if (itemsPerPage < 1) itemsPerPage = 10;

        float count = tradeItemRepository.count();
        if (count == 0)
            count = 1;

        return (int) Math.ceil(count / itemsPerPage);
    }

    @Override
    public List<Integer> getPaginationList(int activePage, int itemsPerPage, int paginatorLength) {
        if (itemsPerPage < 0) itemsPerPage = 10;
        if (paginatorLength < 0) paginatorLength = 5;

        int pageCount = getPageCount(itemsPerPage);

        activePage = Math.max(1, Math.min(pageCount, activePage));

        ArrayList<Integer> list = new ArrayList<Integer>();

        //len 5
        //active 3
        //pages 10
        // 1 2 _3_ 4 5 ... 10

        //len 5
        //active 7
        //pages 10
        // 1 ... 5 6 _7_ 8 9 ... 10
        int page = activePage - (paginatorLength - 1) / 2;

        if (page > 1) {
            list.add(1);
            list.add(-1);
        }

        if (page < 1)
            page = 1;
        else if (page > pageCount - paginatorLength + 1)
            page = pageCount - paginatorLength + 1;


        for (int i = 0; i < paginatorLength; i++) {
            if (page > pageCount)
                break;
            list.add(page);
            page++;
        }

        if (page < pageCount) {
            list.add(-1);
            list.add(pageCount);
        }

        return list;
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
