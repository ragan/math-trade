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
import trade.math.domain.tradeUser.TradeUserService;
import trade.math.domain.wantList.WantList;
import trade.math.domain.wantList.WantListEntry;
import trade.math.domain.wantList.WantListService;
import trade.math.form.NewTradeItemForm;
import trade.math.form.NewTradeUserForm;
import trade.math.domain.tradeItem.TradeItem;
import trade.math.model.TradeUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MtApplication.class)
@ActiveProfiles("test")
public class WantListEntryServiceTest {

    @Autowired
    private TradeItemService tradeItemService;

    @Autowired
    private TradeUserService tradeUserService;

    @Autowired
    private WantListService wantListService;

    private final String USERNAME_0 = "username_0";
    private final String USERNAME_1 = "username_1";

    @Before
    public void setUp() throws Exception {
        wantListService.deleteAll();
        tradeItemService.deleteAll();

        tradeUserService.deleteAll();
        tradeUserService.save(new NewTradeUserForm(USERNAME_0, "some@email.com", "password", "password"));
        tradeUserService.save(new NewTradeUserForm(USERNAME_1, "some1@email.com", "password", "password"));
    }

    @Test
    public void testFindEntries() throws Exception {
        int count = 10;
        TradeItem offer = tradeItemService.save(new NewTradeItemForm("item_0", "description_0", ""), getUser(USERNAME_0));
        List<TradeItem> p = makeItems(count, "p", USERNAME_1);
        wantListService.setWants(offer, p);

        List<WantListEntry> entries = wantListService.findEntries(offer);
        assertThat(entries, hasSize(count));
    }

    private TradeUser getUser(String username) {
        return tradeUserService.findByUsername(username);
    }

    @Test
    public void testWantListIsCreatedForEveryItem() throws Exception {
        TradeItem item = tradeItemService.save(new NewTradeItemForm("item_0", "description_0", ""), getUser(USERNAME_0));

        WantList wantList = wantListService.findByItem(item);
        assertThat(wantList, is(notNullValue()));
        assertThat(wantList.getEntries(), is(empty()));

        assertThat(wantList.getItem().getId(), is(equalTo(item.getId())));
    }

    @Test
    public void testPriorities() throws Exception {
        WantList wantList;

        TradeItem offer = tradeItemService.save(new NewTradeItemForm("offer_0", "offer_0", ""), getUser(USERNAME_0));
        TradeItem want0 = tradeItemService.save(new NewTradeItemForm("want_0", "want_0", ""), getUser(USERNAME_1));
        TradeItem want1 = tradeItemService.save(new NewTradeItemForm("want_1", "want_1", ""), getUser(USERNAME_1));

        assertThat(wantListService.findByItem(offer).getEntries(), is(empty()));
        assertThat(wantListService.findByItem(want0).getEntries(), is(empty()));

        wantListService.setWant(offer, want0);
        wantList = wantListService.findByItem(offer);
        assertThat(wantList.getEntries(), hasSize(1));
        assertThat(wantList.getEntries().get(0).getPriority(), is(equalTo(1)));

        wantList = wantListService.findByItem(offer);
        assertThat(wantList.getEntries(), hasSize(2));
        assertThat(wantList.getEntries().get(0).getPriority(), is(equalTo(2)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserCannotAddOwnItems() throws Exception {
        TradeItem offer = tradeItemService.save(new NewTradeItemForm("offer_0", "offer_0", ""), getUser(USERNAME_0));
        TradeItem want = tradeItemService.save(new NewTradeItemForm("want_0", "want_0", ""), getUser(USERNAME_0));

        wantListService.setWant(offer, want);

        assertThat(wantListService.findByItem(offer).getEntries(), is(empty()));
        assertThat(wantListService.findByItem(want).getEntries(), is(empty()));
    }

    @Test
    public void testDeleteAllEntries() throws Exception {
        TradeItem offer = tradeItemService.save(new NewTradeItemForm("offer_0", "offer_0", ""), getUser(USERNAME_0));
        List<TradeItem> items = makeItems(3, "p", USERNAME_1);
        wantListService.setWants(offer, items);
        assertThat(wantListService.findByItem(offer).getEntries(), hasSize(3));

        wantListService.setWants(offer, Collections.emptyList());
        assertThat(wantListService.findByItem(offer).getEntries(), is(empty()));
    }

    @Test
    public void testDeleteSingleEntry() throws Exception {
        TradeItem offer = tradeItemService.save(new NewTradeItemForm("offer_0", "offer_0", ""), getUser(USERNAME_0));
        TradeItem want = tradeItemService.save(new NewTradeItemForm("want_0", "want_0", ""), getUser(USERNAME_1));

        wantListService.setWant(offer, want);
        assertThat(wantListService.findByItem(offer).getEntries(), hasSize(1));
        assertThat(wantListService.findEntry(offer, want), is(notNullValue()));

        wantListService.deleteWant(offer, want);
        assertThat(wantListService.findByItem(offer).getEntries(), is(empty()));
    }

    @Test
    public void testDeleteWantListEntry() throws Exception {
        TradeItem offer = tradeItemService.save(new NewTradeItemForm("offer_0", "offer_0", ""), getUser(USERNAME_0));
        TradeItem want = tradeItemService.save(new NewTradeItemForm("want_0", "want_0", ""), getUser(USERNAME_1));

        wantListService.setWant(offer, want);
        assertThat(wantListService.findByItem(offer).getEntries(), is(not(empty())));
        wantListService.deleteWant(offer, want);
        assertThat(wantListService.findByItem(offer).getEntries(), is(empty()));
    }

    @Test
    public void updateWantListItemTest() {
//        TradeItem item = tradeItemService.save(new NewTradeItemForm("test", "test", null), "username");
//        TradeItem wantItem = tradeItemService.save(new NewTradeItemForm("want", "want", null), "username1");
//
//        WantListItem wantList = new WantListItem();
//
//        wantList.setPriority(1);
//        wantList.setItem(item);
//        wantList.setWant(wantItem);
//
//        wantList = wantListItemService.save(wantList);
//
//        item = tradeItemService.findById(item.getId());
//
//        assertEquals(1, item.getWantList().size());
//        assertEquals(1, item.getWantList().get(0).getPriority());
//
//        wantList.setPriority(4);
//
//        wantListItemService.update(wantList);
//
//        item = tradeItemService.findById(item.getId());
//        assertEquals(4, item.getWantList().get(0).getPriority());
    }

    @Test
    public void deleteWantListItemTest() {
//        TradeItem item = tradeItemService.save(new NewTradeItemForm("test", "test", null), "username");
//        TradeItem wantItem = tradeItemService.save(new NewTradeItemForm("want", "want", null), "username1");
//
//        WantListItem wantList = new WantListItem();
//
//        wantList.setPriority(6);
//        wantList.setItem(item);
//        wantList.setWant(wantItem);
//
//        wantList = wantListItemService.save(wantList);
//
//        item = tradeItemService.findById(item.getId());
//
//        assertEquals(1, item.getWantList().size());
//        assertEquals(6, item.getWantList().get(0).getPriority());
//
//        boolean result = wantListItemService.delete(wantList, tradeItemService);
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
//        assertEquals(4, item.getWantList().stream().filter(wantList -> wantList.getWant().getId() == pickedWantListId).findFirst().get().getPriority());
//
//        array[3] = array[0];
//        array[0] = pickedWantListId;
//
//        tradeItemService.updateWantList(list1.get(0).getId(), array);
//        item = tradeItemService.findById(list1.get(0).getId());
//        assertEquals(5, item.getWantList().size());
//        assertEquals(1, item.getWantList().stream().filter(wantList -> wantList.getWant().getId() == pickedWantListId).findFirst().get().getPriority());
    }


    private List<TradeItem> makeItems(int num, String prefix, String username) {
        List<TradeItem> list = new ArrayList<>();
        TradeUser user = getUser(username);
        for (int i = 0; i < num; i++) {
            list.add(tradeItemService.save(new NewTradeItemForm(prefix + String.valueOf(i), prefix + String.valueOf(i), null), user));
        }

        return list;
    }

}
