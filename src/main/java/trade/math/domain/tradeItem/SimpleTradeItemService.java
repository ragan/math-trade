package trade.math.domain.tradeItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trade.math.TradeUserRole;
import trade.math.domain.groupList.ItemGroupService;
import trade.math.domain.tradeList.TradeList;
import trade.math.domain.tradeList.TradeListService;
import trade.math.domain.tradeList.TradeListState;
import trade.math.domain.tradeUser.TradeUserService;
import trade.math.domain.wantList.WantList;
import trade.math.domain.wantList.WantListEntryDTO;
import trade.math.domain.wantList.WantListService;
import trade.math.form.NewTradeItemForm;
import trade.math.model.TradeUser;
import trade.math.wrappers.PageWrapper;
import trade.math.wrappers.TradeItemPageWrapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
@Transactional
public class SimpleTradeItemService implements TradeItemService {

    private final TradeItemRepository tradeItemRepository;

    private final TradeListService tradeListService;

    private final WantListService wantListService;

    private final ItemGroupService itemGroupService;

    @Autowired
    public SimpleTradeItemService(
            TradeItemRepository tradeItemRepository,
            TradeListService tradeListService,
            ItemGroupService itemGroupService,
            WantListService wantListService,
            TradeUserService tradeUserService
    ) {
        this.tradeItemRepository = tradeItemRepository;
        this.tradeListService = tradeListService;
        this.itemGroupService = itemGroupService;
        this.wantListService = wantListService;
    }

    @Override
    public TradeItem save(NewTradeItemForm tradeItemForm, TradeUser user) {
        TradeList list = tradeListService.findMostRecentList().orElse(null);
        if (list != null && list.getState() == TradeListState.CLOSED)
            list = null;
        return save(tradeItemForm, user, list);
    }

    @Override
    public TradeItem save(NewTradeItemForm newTradeItemForm, TradeUser user, TradeList tradeList) {
        TradeItem tradeItem = new TradeItem();

        tradeItem.setOwner(user);

        tradeItem.setDescription(newTradeItemForm.getDescription());
        tradeItem.setForTrade(false);
        tradeItem.setTitle(newTradeItemForm.getTitle());
        tradeItem.setImgUrl(newTradeItemForm.getImageUrl());
        tradeItem.setTradeList(tradeList);
        tradeItem.setCategory(newTradeItemForm.getCategory());
        tradeItem.setBggId(newTradeItemForm.getBggId());

        tradeItem = tradeItemRepository.save(tradeItem);

        WantList wantList = new WantList();
        wantList.setItem(tradeItem);
        wantListService.save(wantList);

        return tradeItem;
    }

    @Override
    public List<TradeItem> findByTradeList(TradeList tradeList) {
        return tradeItemRepository.findByTradeList(tradeList);
    }

    @Override
    public List<TradeItem> findByRecentTradeListAndNameAndNotOwner(String name, TradeUser user) {
        TradeList recentList = tradeListService.findMostRecentList().orElse(null);

        if (user == null)
            return null;

        List<TradeItem> exact = tradeItemRepository.findByTradeListAndTitleAllIgnoreCaseAndOwnerNotOrderByTitleAsc(recentList, name.toLowerCase(), user);

        List<TradeItem> containing = tradeItemRepository.findByTradeListAndTitleAllIgnoreCaseContainingAndOwnerNotOrderByTitleAsc(recentList, name.toLowerCase(), user);
        exact.addAll(containing);

        return exact;
    }

    @Override
    public List<TradeItem> findByRecentTradeList() {
        return findByTradeList(tradeListService.findMostRecentList().orElse(null));
    }

    @Override
    public List<TradeItem> findByRecentTradeListAndOwner(TradeUser user) {
        return tradeItemRepository.findByTradeListAndOwner(tradeListService.findMostRecentList().orElse(null), user);
    }

    @Override
    public List<TradeItem> findAll() {
        return tradeItemRepository.findAll();
    }

    @Override
    public void deleteById(Long itemId) {
        TradeItem item = tradeItemRepository.findOne(itemId);
        wantListService.deleteWantList(item);
        tradeItemRepository.delete(itemId);
    }

    @Override
    public void deleteAll() {
        wantListService.deleteAll();
        tradeItemRepository.deleteAll();
    }

    @Override
    public TradeItem findById(Long itemId) {
        TradeItem item = tradeItemRepository.findOne(itemId);
        if (item == null)
            throw new IllegalArgumentException("Item not found.");
        return item;
    }

    @Override
    public List<TradeItem> findByIds(List<Long> ids) {
        return ids.stream().map(tradeItemRepository::findOne).collect(toList());
    }

    @Override
    public List<TradeItem> findByOwner(TradeUser owner) {
        return tradeItemRepository.findByOwner(owner);
    }

    @Override
    public PageWrapper<TradeItemDTO> findAll(Pageable pageable) {
        return findAll(pageable, false, null);
    }

    @Override
    public PageWrapper<TradeItemDTO> findAll(Pageable pageable, boolean isAdmin, TradeUser user) {
        return toPageWrapper(pageable, getItemsWithCanDelete(pageable, user), tradeItemRepository.count());
    }

    private Map<TradeItem, Boolean> getItemsWithCanDelete(Pageable pageable, TradeUser user) {
        return getItemsWithCanDelete(tradeItemRepository.findAll(pageable).getContent(), user);
    }

    private Map<TradeItem, Boolean> getItemsWithCanDelete(List<TradeItem> items, TradeUser user) {
        return items.stream().collect(toMap(i -> i, i -> canDelete(i, user)));
    }

    private PageWrapper<TradeItemDTO> toPageWrapper(Pageable pageable, Map<TradeItem, Boolean> items, long itemCount) {
        List<TradeItemDTO> dtos = items.entrySet()
                .stream()
                .map(e -> new TradeItemDTO(e.getKey(), e.getValue()))
                .collect(toList());
        return new TradeItemPageWrapper(dtos, pageable, itemCount);
    }

    @Override
    public PageWrapper<TradeItemDTO> findAllByRecentTradeList(Pageable pageable, boolean isAdmin, TradeUser user) {
        TradeList tradeList = tradeListService.findMostRecentList().orElse(null);
        return toPageWrapper(pageable, getItemsWithCanDelete(tradeItemRepository.findByTradeList(tradeList), user),
                tradeItemRepository.count());
    }

    @Override
    public boolean canDelete(TradeItem item, TradeUser user) {
        return canDelete(item, Optional.ofNullable(user));
    }

    @Override
    public boolean canDelete(TradeItem item, Optional<TradeUser> user) {
        if (!user.isPresent()) {
            return false;
        }
        TradeUser tradeUser = user.get();
        return tradeUser.getRole() == TradeUserRole.ROLE_ADMIN ||
                item.getOwner().getUsername().equals(tradeUser.getUsername());
    }

    @Override
    public String generateTradeWantListTM(TradeUser user) {
        return "";
//        String tmText = "";
//        List<TradeItem> tradeItems = findByRecentTradeListAndOwner(userName);
//
//        if (tradeItems == null)
//            return tmText;
//
//
//        for (TradeItem tradeItem : tradeItems) {
//            if (tradeItem.getWantList() == null || tradeItem.getWantList().getWant().size() == 0)
//                continue;
//            tmText += "\n(" + userName + ") " + tradeItem.getId() + " : ";
//            for (WantListEntryDTO wantItem : wantListService.findWantListByOfferTradeItem(tradeItem).stream().sorted((o1, o2) -> Integer.compare(o1.getPriority(), o2.getPriority())).collect(Collectors.toList()))
//                tmText += wantItem.getWantTradeItemId() + " ";
//        }
//        return tmText;
    }

}
