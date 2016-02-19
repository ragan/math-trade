package trade.math.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

        List<TradeItem> testPage0 = tradeItemService.findWithPagination(1, 10);

        assertEquals(0, testPage0.get(0).getBggId());
        assertEquals(5, testPage0.get(5).getBggId());

        assertEquals("desc9", testPage0.get(9).getDescription());

        List<TradeItem> testPage3 = tradeItemService.findWithPagination(4, 10);

        assertEquals(32, testPage3.get(2).getBggId());

        assertEquals("desc37", testPage3.get(7).getDescription());
    }

    @Test
    public void testGetPageCount(){
        prepareTradeList(1);
        int pageCount = tradeItemService.getPageCount(10);
        assertEquals(1, pageCount);

        prepareTradeList(0);
        pageCount = tradeItemService.getPageCount(10);
        assertEquals(1, pageCount);

        prepareTradeList(10);
        pageCount = tradeItemService.getPageCount(10);
        assertEquals(1, pageCount);

        prepareTradeList(77);
        pageCount = tradeItemService.getPageCount(7);
        assertEquals(11, pageCount);

        prepareTradeList(100);
        pageCount = tradeItemService.getPageCount(5);
        assertEquals(20, pageCount);

        prepareTradeList(20);
        pageCount = tradeItemService.getPageCount(7);
        assertEquals(3, pageCount);
    }

    @Test
    public void testGetPaginationList(){
        prepareTradeList(100);
        List<Integer> list = tradeItemService.getPaginationList(1, 10, 5);

        System.out.println(list.toString());

        assertEquals(7, list.size());
        assertEquals(1, list.get(0).intValue());
        assertEquals(2, list.get(1).intValue());
        assertEquals(5, list.get(4).intValue());
        assertEquals(-1, list.get(5).intValue());
        assertEquals(10, list.get(6).intValue());

        list = tradeItemService.getPaginationList(5, 10, 5);

        System.out.println(list.toString());

        assertEquals(9, list.size());
        assertEquals(1, list.get(0).intValue());
        assertEquals(-1, list.get(1).intValue());
        assertEquals(3, list.get(2).intValue());
        assertEquals(5, list.get(4).intValue());
        assertEquals(7, list.get(6).intValue());
        assertEquals(-1, list.get(7).intValue());
        assertEquals(10, list.get(8).intValue());

        list = tradeItemService.getPaginationList(9, 10, 5);

        System.out.println(list.toString());

        assertEquals(7, list.size());
        assertEquals(10, list.get(6).intValue());
        assertEquals(9, list.get(5).intValue());
        assertEquals(1, list.get(0).intValue());
        assertEquals(-1, list.get(1).intValue());

        list = tradeItemService.getPaginationList(5, 10, 4);

        System.out.println(list.toString());

        assertEquals(8, list.size());
        assertEquals(4, list.get(2).intValue());
        assertEquals(5, list.get(3).intValue());
        assertEquals(7, list.get(5).intValue());
        assertEquals(-1, list.get(6).intValue());
        assertEquals(10, list.get(7).intValue());

        prepareTradeList(1);
        list = tradeItemService.getPaginationList(1, 10, 5);

        System.out.println(list.toString());

        assertEquals(1, list.size());
        assertEquals(1, list.get(0).intValue());

        list = tradeItemService.getPaginationList(3, 10, 5);

        System.out.println(list.toString());

        assertEquals(1, list.size());
        assertEquals(1, list.get(0).intValue());

        list = tradeItemService.getPaginationList(-3, 10, 5);

        System.out.println(list.toString());

        assertEquals(1, list.size());
        assertEquals(1, list.get(0).intValue());


        prepareTradeList(50);
        list = tradeItemService.getPaginationList(3, 10, 5);

        System.out.println(list.toString());

        assertEquals(5, list.size());
        assertEquals(1, list.get(0).intValue());
        assertEquals(5, list.get(4).intValue());

    }


    //HELPERS
    private void prepareTradeList(int count) {
        tradeItemService.clearTradeItems();

        for (int i = 0; i < count; i++) {
            NewTradeItemForm form = new NewTradeItemForm();
            form.setDescription("desc" + i);
            form.setBggId(i);

            tradeItemService.save(form);
        }
    }


}