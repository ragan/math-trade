package trade.math.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import trade.math.MtApplication;
import trade.math.domain.tradeItem.TradeItemService;
import trade.math.domain.wantListItem.WantListService;
import trade.math.form.NewTradeItemForm;
import trade.math.form.NewTradeUserForm;
import trade.math.domain.tradeItem.TradeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daniel on 24.03.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MtApplication.class)
@ActiveProfiles("test")
public class WantListEntryServiceTest {

    @Autowired
    private TradeItemService tradeItemService;

    @Autowired
    private TradeUserService tradeUserService;

    @Autowired
    private WantListService wantListItemService;

    @Before
    public void setUp() throws Exception {
        tradeItemService.deleteAll(true);

        tradeUserService.deleteAll();
        tradeUserService.save(new NewTradeUserForm("username", "some@email.com", "password", "password"));
        tradeUserService.save(new NewTradeUserForm("username1", "some1@email.com", "password", "password"));
    }

    @Test
    public void saveWantListItemTest() {
//        List<TradeItem> list1 = generateArrayItems(10, "test", "username");
//        List<TradeItem> list2 = generateArrayItems(15, "want", "username1");
//
//        WantListItem item = new WantListItem();
//
//        item.setOffer(list1.get(0));
//        item.setWant(list2.get(0));
//        item.setPriority(1);
//
//        item = wantListItemService.save(item);
//
//        assertNotNull(item);
//
//        TradeItem tradeItem = tradeItemService.findById(list1.get(0).getId());
//
//        List<WantListItem> wantList = tradeItem.getWantList();
//
//        assertEquals(1, wantList.size());
//        assertTrue(wantList.get(0).getWant().getTitle().equals(list2.get(0).getTitle()));
//
//
//        for (int i = 0; i < 4; i++) {
//            item = new WantListItem();
//            item.setOffer(list1.get(0));
//            item.setWant(list2.get(i));
//            item.setPriority(i);
//
//            wantListItemService.save(item);
//        }
//
//        tradeItem = tradeItemService.findById(list1.get(0).getId());
//
//        assertEquals(4, tradeItem.getWantList().size());
    }

    @Test
    public void updateWantListItemTest() {
//        TradeItem item = tradeItemService.save(new NewTradeItemForm("test", "test", null), "username");
//        TradeItem wantItem = tradeItemService.save(new NewTradeItemForm("want", "want", null), "username1");
//
//        WantListItem wantListItem = new WantListItem();
//
//        wantListItem.setPriority(1);
//        wantListItem.setOffer(item);
//        wantListItem.setWant(wantItem);
//
//        wantListItem = wantListItemService.save(wantListItem);
//
//        item = tradeItemService.findById(item.getId());
//
//        assertEquals(1, item.getWantList().size());
//        assertEquals(1, item.getWantList().get(0).getPriority());
//
//        wantListItem.setPriority(4);
//
//        wantListItemService.update(wantListItem);
//
//        item = tradeItemService.findById(item.getId());
//        assertEquals(4, item.getWantList().get(0).getPriority());
    }

    @Test
    public void deleteWantListItemTest() {
//        TradeItem item = tradeItemService.save(new NewTradeItemForm("test", "test", null), "username");
//        TradeItem wantItem = tradeItemService.save(new NewTradeItemForm("want", "want", null), "username1");
//
//        WantListItem wantListItem = new WantListItem();
//
//        wantListItem.setPriority(6);
//        wantListItem.setOffer(item);
//        wantListItem.setWant(wantItem);
//
//        wantListItem = wantListItemService.save(wantListItem);
//
//        item = tradeItemService.findById(item.getId());
//
//        assertEquals(1, item.getWantList().size());
//        assertEquals(6, item.getWantList().get(0).getPriority());
//
//        boolean result = wantListItemService.delete(wantListItem, tradeItemService);
//
//        assertTrue(result);
//
//        item = tradeItemService.findById(item.getId());
//
//        assertEquals(0, item.getWantList().size());
    }

    @Test
    public void updateWantListTest() {
//        List<TradeItem> list1 = generateArrayItems(10, "test", "username");
//        List<TradeItem> list2 = generateArrayItems(15, "want", "username1");
//
//        //Check adding
//        tradeItemService.updateWantList(list1.get(0).getId(), ArrayUtils.toObject(list2.stream().limit(5).mapToLong(value -> value.getId()).toArray()));
//        TradeItem item = tradeItemService.findById(list1.get(0).getId());
//        assertEquals(5, item.getWantList().size());
//
//        //Check deleting
//        tradeItemService.updateWantList(item.getId(), new Long[]{});
//        item = tradeItemService.findById(list1.get(0).getId());
//        assertEquals(0, item.getWantList().size());
//
//        //Check failure id
//        Long[] array = ArrayUtils.toObject(list2.stream().limit(5).mapToLong(value -> value.getId()).toArray());
//        array[4] = 192736L;
//        tradeItemService.updateWantList(item.getId(), array);
//        item = tradeItemService.findById(list1.get(0).getId());
//        assertEquals(4, item.getWantList().size());
//
//        //Check change priority
//        array = ArrayUtils.toObject(list2.stream().limit(5).mapToLong(value -> value.getId()).toArray());
//        tradeItemService.updateWantList(list1.get(0).getId(), array);
//        item = tradeItemService.findById(list1.get(0).getId());
//        assertEquals(5, item.getWantList().size());
//
//        Long pickedWantListId = array[3];
//        assertEquals(4, item.getWantList().stream().filter(wantListItem -> wantListItem.getWant().getId() == pickedWantListId).findFirst().get().getPriority());
//
//        array[3] = array[0];
//        array[0] = pickedWantListId;
//
//        tradeItemService.updateWantList(list1.get(0).getId(), array);
//        item = tradeItemService.findById(list1.get(0).getId());
//        assertEquals(5, item.getWantList().size());
//        assertEquals(1, item.getWantList().stream().filter(wantListItem -> wantListItem.getWant().getId() == pickedWantListId).findFirst().get().getPriority());
    }


    private List<TradeItem> generateArrayItems(int num, String prefix, String username) {
        List<TradeItem> list = new ArrayList<>();

        for (int i = 0; i < num; i++)
            list.add(tradeItemService.save(new NewTradeItemForm(prefix + String.valueOf(i), prefix + String.valueOf(i), null), username));

        return list;
    }

}
