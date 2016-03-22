package trade.math.service.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trade.math.form.NewTradeItemForm;
import trade.math.model.*;
import trade.math.model.dto.TradeItemDTO;
import trade.math.repository.TradeItemRepository;
import trade.math.repository.TradeUserRepository;
import trade.math.service.BggIdToTitleService;
import trade.math.service.TradeBoardGamePropertiesService;
import trade.math.service.TradeItemService;
import trade.math.service.TradeListService;
import trade.math.wrappers.PageWrapper;
import trade.math.wrappers.TradeItemPageWrapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by karol on 17.02.16.
 */
@Service
@Transactional
public class SimpleTradeItemService implements TradeItemService {

    private final TradeItemRepository tradeItemRepository;

    private final BggIdToTitleService bggIdToTitleService;

    private final TradeUserRepository tradeUserRepository;

    private final TradeListService tradeListService;

    private final TradeBoardGamePropertiesService tradeBoardGamePropertiesService;

    @Autowired
    public SimpleTradeItemService(TradeItemRepository tradeItemRepository,
                                  BggIdToTitleService bggIdToTitleService,
                                  TradeUserRepository tradeUserRepository,
                                  TradeListService tradeListService,
                                  TradeBoardGamePropertiesService tradeBoardGamePropertiesService) {
        this.tradeItemRepository = tradeItemRepository;
        this.bggIdToTitleService = bggIdToTitleService;
        this.tradeUserRepository = tradeUserRepository;
        this.tradeListService = tradeListService;
        this.tradeBoardGamePropertiesService = tradeBoardGamePropertiesService;
    }

    @Override
    public TradeItem save(NewTradeItemForm tradeItemForm, String username) {
        TradeList list = tradeListService.findMostRecentList();
        if (list != null && list.getState() == TradeListState.CLOSED)
            list = null;
        return save(tradeItemForm, username, list);
    }

    @Override
    public TradeItem save(NewTradeItemForm newTradeItemForm, String username, TradeList tradeList) {
        TradeItem tradeItem = new TradeItem();

        tradeItem.setOwner(tradeUserRepository.findOneByUsername(username));

        tradeItem.setDescription(newTradeItemForm.getDescription());
        tradeItem.setForTrade(false);
        tradeItem.setTitle(newTradeItemForm.getTitle());
        tradeItem.setImgUrl(newTradeItemForm.getImageUrl());
        tradeItem.setTradeList(tradeList);
        tradeItem.setCategory(newTradeItemForm.getCategory());

        tradeItem = tradeItemRepository.save(tradeItem);

        handleSaveProperties(tradeItem, newTradeItemForm);

        return tradeItem;
    }

    @Override
    public TradeItem update(TradeItem item) {
        return tradeItemRepository.save(item);
    }

    @Override
    public List<TradeItem> findByTradeList(TradeList tradeList) {
        return tradeItemRepository.findByTradeList(tradeList);
    }

    @Override
    public List<TradeItem> findByTradeList(Long tradeListId) {
        return findByTradeList(tradeListService.findById(tradeListId));
    }

    @Override
    public List<TradeItem> findByRecentTradeList() {
        return findByTradeList(tradeListService.findMostRecentList());
    }

    @Override
    public List<TradeItem> findAll() {
        return tradeItemRepository.findAll();
    }

    @Override
    public void deleteAll(boolean isAdmin) { //TODO: WTF?
        if (isAdmin) {
            tradeBoardGamePropertiesService.deleteAll();
            tradeItemRepository.deleteAll();
        }
    }

    @Override
    public boolean deleteById(Long itemId, boolean isAdmin, String userName) {
        TradeItem item = findById(itemId);
        if (item == null || !checkPermission(item.getOwner(), isAdmin, userName))
            return false;

        handleDeleteProperties(item);

        try {
            tradeItemRepository.delete(itemId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public TradeItem findById(Long itemId) {
        return tradeItemRepository.findOne(itemId);
    }

    @Override
    public PageWrapper<TradeItemDTO> findAll(Pageable pageable) {
        return findAll(pageable, false, "");
    }

    @Override
    public PageWrapper<TradeItemDTO> findAll(Pageable pageable, boolean isAdmin, String userName) {
        //return new TradeItemPageWrapper(
        // prepareTradeItemDTOList(tradeItemRepository.findAll(pageable).getContent(), isAdmin, userName),
        // pageable,
        // tradeItemRepository.count());
        return new TradeItemPageWrapper(
                tradeItemRepository.findAll(pageable).getContent().stream().parallel().map(item -> tradeItemToDTO(item, isAdmin, userName)).collect(Collectors.toList()),
                pageable,
                tradeItemRepository.count());
    }

    private TradeItemDTO tradeItemToDTO(TradeItem item, boolean isAdmin, String userName) {
        return new TradeItemDTO(
                item.getId(),
                item.getTitle(),
                item.getDescription(),
                item.getImgUrl(),
                checkPermission(item.getOwner(), isAdmin, userName));
    }

    private boolean checkPermission(TradeUser owner, boolean isAdmin, String userName) {
        if (isAdmin)
            return true;

        if ("".equals(userName) || userName == null)
            return false;

        return userName.equals(owner.getUsername());
    }

    private void handleSaveProperties(TradeItem tradeItem, NewTradeItemForm newTradeItemForm) {
        if (tradeItem == null)
            return;

        switch (newTradeItemForm.getCategory()) {
            case BOARD_GAME:
                tradeBoardGamePropertiesService.save(new TradeBoardGameProperties(tradeItem, newTradeItemForm.getBggId()));
                break;
        }
    }

    private void handleDeleteProperties(TradeItem item) {
        tradeBoardGamePropertiesService.deleteByTradeItem(item);
    }

}
