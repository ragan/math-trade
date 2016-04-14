package trade.math.domain.groupItem;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import trade.math.MtApplication;
import trade.math.domain.tradeItem.TradeItem;
import trade.math.domain.tradeItem.TradeItemService;
import trade.math.domain.tradeList.TradeListService;
import trade.math.domain.tradeUser.TradeUserService;
import trade.math.form.NewTradeItemForm;
import trade.math.form.NewTradeUserForm;
import trade.math.model.TradeUser;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static trade.math.model.TradeItemCategory.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MtApplication.class)
@ActiveProfiles("test")
public class GroupItemServiceTest {

    private final String USER1 = "user1";

    private final String USER0 = "user0";

    @Autowired
    private TradeItemService tradeItemService;

    @Autowired
    private TradeUserService tradeUserService;

    @Autowired
    private TradeListService tradeListService;

    private TradeUser user0;
    private TradeUser user1;

    @Before
    public void setUp() throws Exception {
        tradeItemService.deleteAll();
        tradeUserService.deleteAll();

        user0 = tradeUserService.save(new NewTradeUserForm(USER0, "user0@email", "pass", "pass"));
        user1 = tradeUserService.save(new NewTradeUserForm(USER1, "user1@email", "pass", "pass"));
    }

    @Test
    public void testSaveGroup() throws Exception {
        tradeListService.createNewList();
        tradeItemService.save(new NewTradeItemForm("title0", "desc0", "", BOARD_GAME, 123), user0);
        tradeItemService.save(new NewTradeItemForm("title1", "desc1", "", BOARD_GAME, 123), user1);
        tradeItemService.save(new NewTradeItemForm("title2", "desc2", "", BOARD_GAME, 234), user1);

    }
}