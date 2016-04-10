package trade.math.domain.tradeItem;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trade.math.TradeUserRole;
import trade.math.domain.groupList.ItemGroupService;
import trade.math.domain.tradeList.TradeList;
import trade.math.domain.tradeList.TradeListService;
import trade.math.domain.tradeList.TradeListState;
import trade.math.domain.wantList.WantList;
import trade.math.domain.wantList.WantListDTO;
import trade.math.domain.wantList.WantListService;
import trade.math.form.NewTradeItemForm;
import trade.math.model.TradeUser;
import trade.math.repository.TradeUserRepository;
import trade.math.service.BggIdToTitleService;
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

    private final BggIdToTitleService bggIdToTitleService;

    private final TradeUserRepository tradeUserRepository;

    private final TradeListService tradeListService;

    private final WantListService wantListService;

    private final ItemGroupService itemGroupService;

    @Autowired
    public SimpleTradeItemService(
            TradeItemRepository tradeItemRepository,
            BggIdToTitleService bggIdToTitleService,
            TradeUserRepository tradeUserRepository,
            TradeListService tradeListService,
            ItemGroupService itemGroupService,
            WantListService wantListService
    ) {
        this.tradeItemRepository = tradeItemRepository;
        this.bggIdToTitleService = bggIdToTitleService;
        this.tradeUserRepository = tradeUserRepository;
        this.tradeListService = tradeListService;
        this.itemGroupService = itemGroupService;
        this.wantListService = wantListService;
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
        tradeItem.setBggId(newTradeItemForm.getBggId());

        tradeItem = tradeItemRepository.save(tradeItem);

        WantList wantList = new WantList();
        wantList.setItem(tradeItem);
        wantListService.save(wantList);

        return tradeItem;
    }

    @Override
    public TradeItem update(TradeItem item) {
        return tradeItemRepository.save(item);
    }

//    @Override
//    public boolean updateWantList(Long tradeItemId, Long[] wantIds) {
//        return false;
////        TradeItem tradeItem = tradeItemRepository.findOne(tradeItemId);
////
////        if (tradeItem == null)
////            return false;
////
////        List<WantListItem> wantList = tradeItem.getWantList();
////
////        if (wantList == null)
////            wantList = new ArrayList<>();
////
////
////        //Remove unused
////        List<WantListItem> toRemove = new ArrayList<>();
////
////        for (WantListItem wantList : wantList)
////            if (wantList.getItem().getId() != tradeItemId ||
////                    !Stream.of(wantIds).anyMatch(value -> value.equals(wantList.getWant().getId())))
////                toRemove.add(wantList);
////
////        for (int i = 0; i < toRemove.size(); i++)
////            wantListService.delete(toRemove.get(i), this);
////        toRemove.clear();
////
////        //Update and create new
////        for (int i = 0; i < wantIds.length; i++) {
////            int priority = i;
////            WantListItem item = wantList.stream().filter(wantList -> wantList.getWant().getId().equals(wantIds[priority])).findFirst().orElse(null);
////
////            if (item != null) {
////                item.setPriority(i + 1);
////                wantListService.update(item);
////            } else {
////                TradeItem wantItem = tradeItemRepository.findOne(wantIds[i]);
////                if (wantItem == null)
////                    continue;
////
////                item = new WantListItem();
////                item.setWant(tradeItemRepository.findOne(wantIds[i]));
////                item.setPriority(i + 1);
////                item.setItem(tradeItem);
////                wantListService.save(item);
////            }
////        }
////        return true;
//    }

    @Override
    public List<TradeItem> findByTradeList(TradeList tradeList) {
        return tradeItemRepository.findByTradeList(tradeList);
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
                )).collect(toList());
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
        return tradeItemRepository.findOne(itemId);
    }

    @Override
    public WantListDTO findByIdWantItem(Long itemId) {
        TradeItem item = tradeItemRepository.findOne(itemId);
        return item != null ? new WantListDTO(item.getId(), item.getTitle(), 0) : new WantListDTO(0L, "", 0);
    }

    @Override
    public PageWrapper<TradeItemDTO> findAll(Pageable pageable) {
        return findAll(pageable, false, "");
    }

    @Override
    public PageWrapper<TradeItemDTO> findAll(Pageable pageable, boolean isAdmin, String userName) {
        return toPageWrapper(pageable, getItemsWithCanDelete(pageable, userName), tradeItemRepository.count());
    }

    private Map<TradeItem, Boolean> getItemsWithCanDelete(Pageable pageable, String username) {
        return getItemsWithCanDelete(tradeItemRepository.findAll(pageable).getContent(), username);
    }

    private Map<TradeItem, Boolean> getItemsWithCanDelete(List<TradeItem> items, String username) {
        return items.stream().collect(toMap(i -> i, i -> canDelete(i, username)));
    }

    private PageWrapper<TradeItemDTO> toPageWrapper(Pageable pageable, Map<TradeItem, Boolean> items, long itemCount) {
        List<TradeItemDTO> dtos = items.entrySet()
                .stream()
                .map(e -> new TradeItemDTO(e.getKey(), e.getValue()))
                .collect(toList());
        return new TradeItemPageWrapper(dtos, pageable, itemCount);
    }

    @Override
    public PageWrapper<TradeItemDTO> findAllByRecentTradeList(Pageable pageable, boolean isAdmin, String userName) {
        TradeList tradeList = tradeListService.findMostRecentList().orElse(null);
        return toPageWrapper(pageable, getItemsWithCanDelete(tradeItemRepository.findByTradeList(tradeList), userName),
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
    public boolean canDelete(TradeItem item, String username) {
        TradeUser user = tradeUserRepository.findOneByUsername(username);
        return canDelete(item, Optional.ofNullable(user));
    }

    @Override
    public void makeGroupLists() {
    }

    @Override
    public String generateTradeWantListTM(String userName) {
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
//            for (WantListDTO wantItem : wantListService.findWantListByOfferTradeItem(tradeItem).stream().sorted((o1, o2) -> Integer.compare(o1.getPriority(), o2.getPriority())).collect(Collectors.toList()))
//                tmText += wantItem.getWantTradeItemId() + " ";
//        }
//        return tmText;
    }

}
