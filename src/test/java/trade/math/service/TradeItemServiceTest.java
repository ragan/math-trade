package trade.math.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import trade.math.MtApplication;
import trade.math.form.NewTradeItemForm;
import trade.math.model.TradeItem;

import java.util.List;

/**
 * Created by karol on 17.02.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MtApplication.class)
@ActiveProfiles("test")
public class TradeItemServiceTest extends TestCase {

    @Autowired
    private TradeItemService tradeItemService;

    @Test
    public void testSaveNewTradeItem() throws Exception {
        NewTradeItemForm newTradeItemForm = new NewTradeItemForm();
        newTradeItemForm.setDescription("description");
        newTradeItemForm.setBggId(123);

        TradeItem item = tradeItemService.save(newTradeItemForm);
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
    public void testFindWithPagination() throws Exception {
        prepareTradeList(100);

        List<TradeItem> testPage0 = tradeItemService.findAll(new PageRequest(0, 10)).getItems();

        assertEquals(0, testPage0.get(0).getBggId());
        assertEquals(5, testPage0.get(5).getBggId());

        assertEquals("desc9", testPage0.get(9).getDescription());

        List<TradeItem> testPage3 = tradeItemService.findAll(new PageRequest(3, 10)).getItems();

        assertEquals(32, testPage3.get(2).getBggId());

        assertEquals("desc37", testPage3.get(7).getDescription());
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
    public void testDeleteById(){
        prepareTradeList(10);

        List<TradeItem> allItems = tradeItemService.findAll();

        for (TradeItem item : allItems) System.out.println(item.getId());

        Long deletedItemId = allItems.get(4).getId();

        tradeItemService.deleteById(deletedItemId); // pierwszy raz usuwam

        allItems = tradeItemService.findAll();

        for (TradeItem item : allItems) System.out.println(item.getId());

        assertEquals(9, allItems.size());

        for (TradeItem item : allItems) assertFalse(item.getId() == deletedItemId);
    }


    //HELPERS
    private void prepareTradeList(int count) {
        tradeItemService.deleteAll();

        for (int i = 0; i < count; i++) {
            NewTradeItemForm form = new NewTradeItemForm();
            form.setDescription("desc" + i);
            form.setBggId(i);

            tradeItemService.save(form);
        }
    }


}