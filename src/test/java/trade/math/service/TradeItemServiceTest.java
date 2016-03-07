package trade.math.service;

import junit.framework.TestCase;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import trade.math.MtApplication;
import trade.math.form.NewTradeItemForm;
import trade.math.form.NewTradeUserForm;
import trade.math.model.TradeItem;
import trade.math.model.TradeUser;
import trade.math.model.dto.TradeItemDTO;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
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

    @Autowired
    private TradeItemService tradeItemService;

    @Autowired
    private TradeUserService tradeUserService;

    private TradeUser tmpUser;

    @Before
    public void setUp() throws Exception {
        tradeItemService.deleteAll(true);

        tradeUserService.deleteAll();
        tmpUser = tradeUserService.save(new NewTradeUserForm("username", "some@email.com", "password", "password"));
    }

    @Test
    public void testIfImageUrlIsTheSame() throws Exception {
        NewTradeItemForm newTradeItemForm = new NewTradeItemForm();
        newTradeItemForm.setTitle("title");
        newTradeItemForm.setDescription("description");
        newTradeItemForm.setImageUrl("imageUrl");

        TradeItem item = tradeItemService.save(newTradeItemForm, "username");
        assertThat(item.getImgUrl(), is(equalTo("imageUrl")));
    }

    @Test
    public void testSaveNewTradeItem() throws Exception {
        NewTradeItemForm newTradeItemForm = new NewTradeItemForm();
        newTradeItemForm.setTitle("title");
        newTradeItemForm.setDescription("description");

        TradeItem item = tradeItemService.save(newTradeItemForm, "username");
        assertNotNull(item.getId());

        Long itemId = item.getId();

        List<TradeItem> forms = tradeItemService.findAll();
        assertTrue(forms.size() == 1);

        TradeItem foundItem = tradeItemService.findById(itemId);
        assertNotNull(foundItem);
        assertTrue(foundItem.getDescription().equals("description"));

        System.out.println(foundItem.getTitle());
    }

    @Test
    public void testGetPaginationList() {
        prepareTradeList(100);
        List<Integer> list = tradeItemService.findAll(new PageRequest(0, 10)).getPagination();// .getPaginationList(1, 10, 5);

        System.out.println(list.toString());

        assertEquals(7, list.size());
        assertEquals(1, list.get(0).intValue());
        assertEquals(2, list.get(1).intValue());
        assertEquals(5, list.get(4).intValue());
        assertEquals(-1, list.get(5).intValue());
        assertEquals(10, list.get(6).intValue());

        list = tradeItemService.findAll(new PageRequest(4, 10)).getPagination();// .getPaginationList(5, 10, 5);

        System.out.println(list.toString());

        assertEquals(9, list.size());
        assertEquals(1, list.get(0).intValue());
        assertEquals(-1, list.get(1).intValue());
        assertEquals(3, list.get(2).intValue());
        assertEquals(5, list.get(4).intValue());
        assertEquals(7, list.get(6).intValue());
        assertEquals(-1, list.get(7).intValue());
        assertEquals(10, list.get(8).intValue());

        list = tradeItemService.findAll(new PageRequest(8, 10)).getPagination();// .getPaginationList(9, 10, 5);

        System.out.println(list.toString());

        assertEquals(7, list.size());
        assertEquals(10, list.get(6).intValue());
        assertEquals(9, list.get(5).intValue());
        assertEquals(1, list.get(0).intValue());
        assertEquals(-1, list.get(1).intValue());
//
//        list = tradeItemService.getPaginationList(5, 10, 4);
//
//        System.out.println(list.toString());
//
//        assertEquals(8, list.size());
//        assertEquals(4, list.get(2).intValue());
//        assertEquals(5, list.get(3).intValue());
//        assertEquals(7, list.get(5).intValue());
//        assertEquals(-1, list.get(6).intValue());
//        assertEquals(10, list.get(7).intValue());

        prepareTradeList(1);
        list = tradeItemService.findAll(new PageRequest(0, 10)).getPagination();// .getPaginationList(1, 10, 5);

        System.out.println(list.toString());

        assertEquals(1, list.size());
        assertEquals(1, list.get(0).intValue());

        list = tradeItemService.findAll(new PageRequest(2, 10)).getPagination();// .getPaginationList(3, 10, 5);

        System.out.println(list.toString());

        assertEquals(1, list.size());
        assertEquals(1, list.get(0).intValue());

//        list = tradeItemService.findAll(new PageRequest(-4, 10)).getPagination();// .getPaginationList(-3, 10, 5);
//
//        System.out.println(list.toString());
//
//        assertEquals(1, list.size());
//        assertEquals(1, list.get(0).intValue());


        prepareTradeList(50);
        list = tradeItemService.findAll(new PageRequest(2, 10)).getPagination();// .getPaginationList(3, 10, 5);

        System.out.println(list.toString());

        assertEquals(5, list.size());
        assertEquals(1, list.get(0).intValue());
        assertEquals(5, list.get(4).intValue());

    }

    @Test
    public void testDeleteById() {
        prepareTradeList(10);

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
        prepareTradeList(10);

        assertFalse(tradeItemService.deleteById(tradeItemService.findAll().get(0).getId(), false, "anotherUser"));
        assertTrue(tradeItemService.deleteById(tradeItemService.findAll().get(0).getId(), false, "username"));
        assertTrue(tradeItemService.deleteById(tradeItemService.findAll().get(0).getId(), true, "anotherUser"));
    }


    //HELPERS
    private void prepareTradeList(int count) {
        tradeItemService.deleteAll(true);

        for (int i = 0; i < count; i++) {
            NewTradeItemForm form = new NewTradeItemForm();
            form.setTitle("title" + i);
            form.setDescription("desc" + i);
//            form.setBggId(i);

            tradeItemService.save(form, "username");
        }
    }

}