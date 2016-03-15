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
import trade.math.form.NewTradeItemForm;
import trade.math.form.NewTradeUserForm;
import trade.math.model.TradeBoardGameProperties;
import trade.math.model.TradeItem;
import trade.math.model.TradeItemCategory;
import trade.math.model.TradeUser;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by karol on 17.02.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MtApplication.class)
@ActiveProfiles("test")
public class TradeBoardGamePropertiesServiceTest {

    @Autowired
    private TradeItemService tradeItemService;

    @Autowired
    private TradeUserService tradeUserService;

    @Autowired
    private TradeBoardGamePropertiesService tradeBoardGamePropertiesService;

    private TradeUser tmpUser;
    private TradeItem tmpItem;

    @Before
    public void setUp() throws Exception {
        tradeItemService.deleteAll(true);

        tradeUserService.deleteAll();
        tmpUser = tradeUserService.save(new NewTradeUserForm("username", "some@email.com", "password", "password"));
        tmpItem = tradeItemService.save(new NewTradeItemForm("Chuj", "test", null), tmpUser.getUsername());
    }

    @Test
    public void testCreateBGP() {

        TradeBoardGameProperties tradeBoardGameProperties = new TradeBoardGameProperties(tmpItem, 10);

        tradeBoardGamePropertiesService.save(tradeBoardGameProperties);

        assertNotNull(tradeBoardGamePropertiesService.findByTradeItem(tmpItem));

        assertNull(tradeBoardGamePropertiesService.findByTradeItem(null));


        tradeBoardGamePropertiesService.deleteByTradeItem(tmpItem);
    }

    @Test
    public void testDuplicateBGP() {

        TradeBoardGameProperties tradeBoardGameProperties = new TradeBoardGameProperties(tmpItem, 10);

        assertNotNull(tradeBoardGamePropertiesService.save(tradeBoardGameProperties));


        tradeBoardGameProperties = new TradeBoardGameProperties(tmpItem, 12);

        assertNull(tradeBoardGamePropertiesService.save(tradeBoardGameProperties));

        tradeBoardGameProperties = tradeBoardGamePropertiesService.findByTradeItem(tmpItem);

        assertNotNull(tradeBoardGameProperties);

        assertEquals(10, tradeBoardGameProperties.getBggId().intValue());

        assertTrue(tmpItem.getId() == tradeBoardGameProperties.getTradeItem().getId());

        tradeBoardGamePropertiesService.deleteByTradeItem(tmpItem);
    }

    @Test
    public void testDeleteItem() {

        TradeBoardGameProperties tradeBoardGameProperties = new TradeBoardGameProperties(tmpItem, 10);

        tradeBoardGameProperties = tradeBoardGamePropertiesService.save(tradeBoardGameProperties);

        assertNotNull(tradeBoardGamePropertiesService.findByTradeItem(tmpItem));

        long itemId = tmpItem.getId();
        tradeItemService.deleteById(itemId, true, "");

        assertNull(tradeBoardGamePropertiesService.findByTradeItem(tmpItem));
        assertNull(tradeItemService.findById(itemId));
    }

    @Test
    public void testCreateItemWithProperty() {
        TradeItem itemWithProperty = tradeItemService.save(new NewTradeItemForm("Chuj", "test", null, TradeItemCategory.BOARD_GAME, 100), tmpUser.getUsername());

        assertNotNull(itemWithProperty);

        assertNotNull(tradeBoardGamePropertiesService.findByTradeItem(itemWithProperty));

        assertEquals(100, tradeBoardGamePropertiesService.findByTradeItem(itemWithProperty).getBggId().intValue());
    }

}