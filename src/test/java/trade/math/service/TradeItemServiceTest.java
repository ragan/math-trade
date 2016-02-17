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
    }
}