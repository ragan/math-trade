package trade.math.service;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import trade.math.MtApplication;
import trade.math.form.NewTradeItemForm;
import trade.math.form.NewTradeUserForm;
import trade.math.model.TradeItem;
import trade.math.model.WantListItem;

import java.util.List;

/**
 * Created by daniel on 24.03.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MtApplication.class)
@ActiveProfiles("test")
public class WantListItemServiceTest {

    @Autowired
    private TradeItemService tradeItemService;

    @Autowired
    private TradeUserService tradeUserService;

    @Before
    public void setUp() throws Exception {
        tradeItemService.deleteAll(true);

        tradeUserService.deleteAll();
        tradeUserService.save(new NewTradeUserForm("username", "some@email.com", "password", "password"));
        tradeUserService.save(new NewTradeUserForm("username1", "some1@email.com", "password", "password"));
    }

    @Test
    public void saveWantListItemTest(){
        TradeItem tradeItem = tradeItemService.save(new NewTradeItemForm("test", "test", null), "username");

        assertNotNull(tradeItem);

        List<WantListItem> wantListItems = tradeItem.getWantList();

        assertEquals(0, wantListItems.size());

        wantListItems.add(WantListItem())
    }


}
