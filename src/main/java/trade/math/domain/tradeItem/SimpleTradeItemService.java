package trade.math.domain.tradeItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trade.math.domain.tradeItem.wantListItem.WantListItem;
import trade.math.domain.tradeItem.wantListItem.WantListItemDTO;
import trade.math.domain.tradeItem.wantListItem.WantListItemService;
import trade.math.GroupListItemWrapper;
import trade.math.domain.groupList.GroupListItem;
import trade.math.domain.groupList.GroupListService;
import trade.math.domain.tradeList.TradeList;
import trade.math.domain.tradeList.TradeListService;
import trade.math.domain.tradeList.TradeListState;
import trade.math.form.NewTradeItemForm;
import trade.math.model.*;
import trade.math.repository.TradeItemRepository;
import trade.math.repository.TradeUserRepository;
import trade.math.service.BggIdToTitleService;
import trade.math.service.TradeBoardGamePropertiesService;
import trade.math.wrappers.PageWrapper;
import trade.math.wrappers.TradeItemPageWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private final WantListItemService wantListItemService;

    private final GroupListService groupListService;

    @Autowired
    public SimpleTradeItemService(TradeItemRepository tradeItemRepository,
                                  BggIdToTitleService bggIdToTitleService,
                                  TradeUserRepository tradeUserRepository,
                                  TradeListService tradeListService,
                                  TradeBoardGamePropertiesService tradeBoardGamePropertiesService,
                                  GroupListService groupListService,
                                  WantListItemService wantListItemService) {
        this.tradeItemRepository = tradeItemRepository;
        this.bggIdToTitleService = bggIdToTitleService;
        this.tradeUserRepository = tradeUserRepository;
        this.tradeListService = tradeListService;
        this.tradeBoardGamePropertiesService = tradeBoardGamePropertiesService;
        this.groupListService = groupListService;
        this.wantListItemService = wantListItemService;
    }

    @Override
    public TradeItem save(NewTradeItemForm tradeItemForm, String username) {
        TradeList list = tradeListService.findMostRecentList().orElse(null);
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
    public boolean updateWantList(Long tradeItemId, Long[] wantIds) {
        TradeItem tradeItem = tradeItemRepository.findOne(tradeItemId);

        if (tradeItem == null)
            return false;

        List<WantListItem> wantList = tradeItem.getWantList();

        if (wantList == null)
            wantList = new ArrayList<>();


        //Remove unused
        List<WantListItem> toRemove = new ArrayList<>();

        for (WantListItem wantListItem : wantList)
            if (wantListItem.getOfferTradeItem().getId() != tradeItemId ||
                    !Stream.of(wantIds).anyMatch(value -> value.equals(wantListItem.getWantTradeItem().getId())))
                toRemove.add(wantListItem);

        for (int i = 0; i < toRemove.size(); i++)
            wantListItemService.delete(toRemove.get(i), this);
        toRemove.clear();

        //Update and create new
        for (int i = 0; i < wantIds.length; i++) {
            int priority = i;
            WantListItem item = wantList.stream().filter(wantListItem -> wantListItem.getWantTradeItem().getId().equals(wantIds[priority])).findFirst().orElse(null);

            if (item != null) {
                item.setPriority(i + 1);
                wantListItemService.update(item);
            } else {
                TradeItem wantItem = tradeItemRepository.findOne(wantIds[i]);
                if (wantItem == null)
                    continue;

                item = new WantListItem();
                item.setWantTradeItem(tradeItemRepository.findOne(wantIds[i]));
                item.setPriority(i + 1);
                item.setOfferTradeItem(tradeItem);
                wantListItemService.save(item);
            }
        }

        return true;
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
    public List<TradeItemDTO> findByRecentTradeListAndNameAndNotOwner(String name, String userName) {
        TradeList recentList = tradeListService.findMostRecentList().orElse(null);
        TradeUser owner = tradeUserRepository.findOneByUsername(userName);

        if (owner == null)
            return null;

        List<TradeItem> exact = tradeItemRepository.findByTradeListAndTitleAllIgnoreCaseAndOwnerNotOrderByTitleAsc(recentList, name.toLowerCase(), owner);

        List<TradeItem> containing = tradeItemRepository.findByTradeListAndTitleAllIgnoreCaseContainingAndOwnerNotOrderByTitleAsc(recentList, name.toLowerCase(), owner);
        exact.addAll(containing);

        return exact.stream()
                .distinct()
                .limit(10)
                .map(tradeItem -> new TradeItemDTO(
                        tradeItem.getId(),
                        tradeItem.getTitle(),
                        tradeItem.getDescription(),
                        tradeItem.getImgUrl(),
                        false
                )).collect(Collectors.toList());
    }

    @Override
    public List<TradeItem> findByRecentTradeList() {
        return findByTradeList(tradeListService.findMostRecentList().orElse(null));
    }

    @Override
    public List<TradeItem> findByRecentTradeListAndOwner(String userName) {
        return tradeItemRepository.findByTradeListAndOwner(tradeListService.findMostRecentList().orElse(null), tradeUserRepository.findOneByUsername(userName));
    }

    @Override
    public List<TradeItem> findAll() {
        return tradeItemRepository.findAll();
    }

    @Override
    public void deleteAll() {
        deleteAll(true);
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
    public WantListItemDTO findByIdWantItem(Long itemId) {
        TradeItem item = tradeItemRepository.findOne(itemId);
        return item != null ? new WantListItemDTO(item.getId(), item.getTitle(), 0) : new WantListItemDTO(0L, "", 0);
    }

    @Override
    public PageWrapper<TradeItemDTO> findAll(Pageable pageable) {
        return findAll(pageable, false, "");
    }

    @Override
    public PageWrapper<TradeItemDTO> findAll(Pageable pageable, boolean isAdmin, String userName) {
        return new TradeItemPageWrapper(
                tradeItemRepository.findAll(pageable)
                        .getContent()
                        .stream()
                        .parallel()
                        .map(ti -> new TradeItemDTO(
                                ti, checkPermission(ti.getOwner(), isAdmin, userName), Optional.ofNullable(tradeBoardGamePropertiesService.findByTradeItem(ti))))
                        .collect(Collectors.toList()),
                pageable,
                tradeItemRepository.count());
    }

    @Override
    public PageWrapper<TradeItemDTO> findAllByRecentTradeList(Pageable pageable, boolean isAdmin, String userName) {
        TradeList tradeList = tradeListService.findMostRecentList().orElse(null);
        return new TradeItemPageWrapper(
                tradeItemRepository.findByTradeList(tradeList, pageable)
                        .getContent()
                        .stream()
                        .parallel()
                        .map(ti -> new TradeItemDTO(ti, checkPermission(ti.getOwner(), isAdmin, userName), Optional.ofNullable(tradeBoardGamePropertiesService.findByTradeItem(ti))))
                        .collect(Collectors.toList()),
                pageable,
                tradeItemRepository.findByTradeList(tradeList).size());
    }

    private boolean checkPermission(TradeUser owner, boolean isAdmin, String userName) {
        return isAdmin || owner != null && userName.equals(owner.getUsername());
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

    @Override
    public void makeGroupLists() {
        makeGroupLists(findByRecentTradeList().stream()
                .map(ti -> new TradeItemDTO(ti,
                        false,
                        Optional.ofNullable(tradeBoardGamePropertiesService.findByTradeItem(ti))))
                .collect(Collectors.toList()));
    }

    private void makeGroupLists(List<TradeItemDTO> tradeItemDTOs) {
//        Map<TradeItemCategory, List<TradeItemDTO>> collect = tradeItemDTOs.stream()
//                .collect(Collectors.groupingBy(dto -> dto.getCategory()));
//        collect.entrySet().forEach(entry -> {
//            Map<GroupListDTO, List<GroupListItem>> map =
//                    groupListService.makeGroupLists(makeGroupListItems(entry.getValue()));
//            map.forEach((k, v) -> {
//                v.forEach(ti -> );
//            });
//        });
    }

    private List<GroupListItem> makeGroupListItems(List<TradeItemDTO> tradeItemDTOs) {
        return tradeItemDTOs.stream().map(this::makeGroupListItem).collect(Collectors.toList());
    }

    private GroupListItem makeGroupListItem(TradeItemDTO tradeItemDTO) {
        switch (tradeItemDTO.getCategory()) {
            case NONE:
                return new GroupListItemWrapper<>(tradeItemDTO, TradeItemDTO::getCategory);
            case BOARD_GAME:
                return new GroupListItemWrapper<>(tradeItemDTO, TradeItemDTO::getBggId);
            default:
                throw new IllegalArgumentException(String.format("Unknown category: %s. Don't know what to do.",
                        tradeItemDTO.getCategory()));
        }
    }
}
