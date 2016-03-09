package trade.math.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import trade.math.MtApplication;
import trade.math.form.NewTradeCategoryForm;
import trade.math.form.NewTradeItemForm;
import trade.math.form.NewTradeUserForm;
import trade.math.model.TradeCategory;
import trade.math.model.TradeItem;
import trade.math.model.TradeUser;

/**
 * Created by daniel on 08.03.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MtApplication.class)
@ActiveProfiles("test")
public class TradeCategoryServiceTest {

    @Autowired
    private TradeCategoryService tradeCategoryService;

    @Autowired
    private TradeItemService tradeItemService;

    @Autowired
    private TradeUserService tradeUserService;

    private TradeUser tmpUser;

    @Before
    public void setup(){
        tradeCategoryService.deleteAll();
        tradeItemService.deleteAll(true);
        tradeUserService.deleteAll();

        tmpUser = tradeUserService.save(new NewTradeUserForm("Test", "test@test.pl", "000000", "000000"));
    }

    @Test
    public void saveTest(){
        assertEquals(0, tradeCategoryService.findAll().size());

        NewTradeCategoryForm newTradeCategoryForm = new NewTradeCategoryForm("Test");

        tradeCategoryService.save(newTradeCategoryForm);
        
        assertEquals(1, tradeCategoryService.findAll().size());
    }

    @Test
    public void categoryItemBindTest(){
        tradeItemService.save(new NewTradeItemForm("Carcassonne", "Dobra", null, null), tmpUser.getUsername());

        TradeCategory tmpCat = tradeCategoryService.save(new NewTradeCategoryForm("Board game"));

        assertEquals(1, tradeItemService.findAll().size());

        TradeItem tmpItem = tradeItemService.findAll().get(0);

        tmpItem.setCategory(tmpCat);

        tradeItemService.update(tmpItem);

        assertEquals(1, tradeItemService.findAll().size());

        tmpItem = tradeItemService.findAll().get(0);

        assertNotNull(tmpItem.getCategory());

        tradeItemService.save(new NewTradeItemForm("Chuj", "Ideał", null, tmpCat), tmpUser.getUsername());

        assertEquals(2, tradeItemService.findAll().size());

        for (TradeItem tradeItem : tradeItemService.findAll()) {
            System.out.println(tradeItem.getTitle() + " - " + tradeItem.getCategory());
            assertNotNull(tradeItem.getCategory());
        }
    }

}
