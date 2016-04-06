package trade.math.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import trade.math.MtApplication;
import trade.math.domain.groupList.GroupListService;
import trade.math.domain.tradeItem.TradeItem;
import trade.math.domain.tradeItem.TradeItemService;
import trade.math.form.NewTradeItemForm;
import trade.math.form.NewTradeUserForm;
import trade.math.model.TradeItemCategory;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by karol on 17.02.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MtApplication.class)
@ActiveProfiles("test")
public class TradeItemServiceTest {

    private final String USERNAME = "username";
    private final String USERNAME_1 = "username1";

    @Autowired
    private TradeItemService tradeItemService;

    @Autowired
    private TradeUserService tradeUserService;

    @Autowired
    private GroupListService groupListService;

    @Before
    public void setUp() throws Exception {
        tradeItemService.deleteAll(true);

        tradeUserService.deleteAll();
        tradeUserService.save(new NewTradeUserForm(USERNAME, "some@email.com", "password", "password"));
        tradeUserService.save(new NewTradeUserForm(USERNAME_1, "some1@email.com", "password", "password"));
    }

    @Test
    public void testSaveAndDeleteNewBoardGame() throws Exception {
        NewTradeItemForm form = new NewTradeItemForm("Title", "Description", "", TradeItemCategory.BOARD_GAME, 123);

        Long id = tradeItemService.save(form, USERNAME).getId();
        assertThat(tradeItemService.findAll(), hasSize(1));

        assertThat(tradeItemService.findById(id).getTitle(), is(equalToIgnoringCase("Title")));
        assertThat(tradeItemService.findById(id).getDescription(), is(equalToIgnoringCase("Description")));
        assertThat(tradeItemService.findById(id).getImgUrl(), is(equalToIgnoringCase("")));
        assertThat(tradeItemService.findById(id).getCategory(), is(TradeItemCategory.BOARD_GAME));
        assertThat(tradeItemService.findById(id).getBggId(), is(123));
    }

    @Test
    public void testGetPaginationList() {
        prepareTradeList(100, USERNAME);
        List<Integer> list = tradeItemService.findAll(new PageRequest(0, 10)).getPagination();// .getPaginationList(1, 10, 5);

        assertEquals(7, list.size());
        assertEquals(1, list.get(0).intValue());
        assertEquals(2, list.get(1).intValue());
        assertEquals(5, list.get(4).intValue());
        assertEquals(-1, list.get(5).intValue());
        assertEquals(10, list.get(6).intValue());

        list = tradeItemService.findAll(new PageRequest(4, 10)).getPagination();// .getPaginationList(5, 10, 5);

        assertEquals(9, list.size());
        assertEquals(1, list.get(0).intValue());
        assertEquals(-1, list.get(1).intValue());
        assertEquals(3, list.get(2).intValue());
        assertEquals(5, list.get(4).intValue());
        assertEquals(7, list.get(6).intValue());
        assertEquals(-1, list.get(7).intValue());
        assertEquals(10, list.get(8).intValue());

        list = tradeItemService.findAll(new PageRequest(8, 10)).getPagination();// .getPaginationList(9, 10, 5);

        assertEquals(7, list.size());
        assertEquals(10, list.get(6).intValue());
        assertEquals(9, list.get(5).intValue());
        assertEquals(1, list.get(0).intValue());
        assertEquals(-1, list.get(1).intValue());

        tradeItemService.deleteAll();
        prepareTradeList(1, USERNAME);
        list = tradeItemService.findAll(new PageRequest(0, 10)).getPagination();// .getPaginationList(1, 10, 5);

        assertEquals(1, list.size());
        assertEquals(1, list.get(0).intValue());

        list = tradeItemService.findAll(new PageRequest(2, 10)).getPagination();// .getPaginationList(3, 10, 5);

        assertEquals(1, list.size());
        assertEquals(1, list.get(0).intValue());

        tradeItemService.deleteAll();
        prepareTradeList(50, USERNAME);
        list = tradeItemService.findAll(new PageRequest(2, 10)).getPagination();// .getPaginationList(3, 10, 5);

        assertEquals(5, list.size());
        assertEquals(1, list.get(0).intValue());
        assertEquals(5, list.get(4).intValue());
    }

    @Test
    public void testDeleteById() {
        prepareTradeList(10, USERNAME);

        List<TradeItem> allItems = tradeItemService.findAll();

        Long deletedItemId = allItems.get(4).getId();

        assertTrue(tradeItemService.deleteById(deletedItemId, true, ""));

        allItems = tradeItemService.findAll();

        assertEquals(9, allItems.size());

        for (TradeItem item : allItems) assertFalse(item.getId() == deletedItemId);

        assertFalse(tradeItemService.deleteById(deletedItemId, true, ""));
    }

    @Test
    public void testDeleteCheckOwner() {
        prepareTradeList(10, USERNAME);

        assertFalse(tradeItemService.deleteById(tradeItemService.findAll().get(0).getId(), false, "anotherUser"));
        assertTrue(tradeItemService.deleteById(tradeItemService.findAll().get(0).getId(), false, USERNAME));
        assertTrue(tradeItemService.deleteById(tradeItemService.findAll().get(0).getId(), true, "anotherUser"));
    }

    @Test
    public void testFindByRecentTradeListAndNameAndNotOwner() {
        prepareTradeList(10, USERNAME);
        prepareTradeList(2, USERNAME_1);

        List list = tradeItemService.findByRecentTradeListAndNameAndNotOwner("", USERNAME);

        assertNotNull(list);
        assertEquals(2, list.size());

        list = tradeItemService.findByRecentTradeListAndNameAndNotOwner("", "test");
        assertNull(list);
    }

    @Test
    public void testFindByRecentTradeListAndOwner() {
        prepareTradeList(10, USERNAME);
        prepareTradeList(8, USERNAME_1);

        List<TradeItem> list = tradeItemService.findByRecentTradeListAndOwner(USERNAME_1);

        assertNotNull(list);
        assertEquals(8, list.size());

        list = tradeItemService.findByRecentTradeListAndOwner("test");

        assertNotNull(list);
        assertEquals(0, list.size());
    }

    @Test
    public void testCreateGroupLists() throws Exception {
        assertThat(true, is(true));
    }

    private void prepareTradeList(int count, String userName) {
        for (int i = 0; i < count; i++) {
            NewTradeItemForm form = new NewTradeItemForm("title" + i, "desc" + i, "");
            tradeItemService.save(form, userName);
        }
    }
}