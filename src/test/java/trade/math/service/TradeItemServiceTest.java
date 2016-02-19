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
    public void testPaginationList() throws Exception {
        prepareTradeList();

        List<TradeItem> testPage0 = tradeItemService.findWithPagination(0, 10);

        assertEquals(0, testPage0.get(0).getBggId());
        assertEquals(5, testPage0.get(5).getBggId());

        assertEquals("desc9", testPage0.get(9).getDescription());

        List<TradeItem> testPage3 = tradeItemService.findWithPagination(3, 10);

        assertEquals(32, testPage3.get(2).getBggId());

        assertEquals("desc37", testPage3.get(7).getDescription());
    }

    private void prepareTradeList() {
        tradeItemService.clearTradeItems();

        for (int i = 0; i < 100; i++) {
            NewTradeItemForm form = new NewTradeItemForm();
            form.setDescription("desc" + i);
            form.setBggId(i);

            tradeItemService.save(form);
        }
    }


}