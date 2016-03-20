package trade.math.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import trade.math.MtApplication;
import trade.math.form.NewTradeItemForm;
import trade.math.form.NewTradeUserForm;
import trade.math.model.TradeItem;
import trade.math.model.TradeList;
import trade.math.model.TradeListState;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtApplication.class)
@ActiveProfiles("test")
public class TradeListServiceTest {

    @Autowired
    private TradeListService tradeListService;

    @Autowired
    private TradeItemService tradeItemService;

    @Autowired
    private TradeUserService tradeUserService;

    @Before
    public void setUp() throws Exception {
        tradeListService.deleteAll();
        tradeItemService.deleteAll(true);
        tradeUserService.deleteAll();

        tradeUserService.save(new NewTradeUserForm("username", "user@email", "password", "password"));
    }

    @Test
    public void testPersistNewList() throws Exception {
        assertThat(tradeListService.createNewList(), is(notNullValue()));
        assertThat(tradeListService.findAll(), hasSize(1));
        assertThat(tradeListService.findMostRecentList().getState(), is(equalTo(TradeListState.OPEN)));
    }

    @Test
    public void testFindMostRecentList() throws Exception {
        TradeList list0 = tradeListService.createNewList();
        TradeList list1 = tradeListService.createNewList();
        TradeList mostRecentList = tradeListService.findMostRecentList();
        assertThat(mostRecentList.getId(), is(equalTo(list1.getId())));
    }

    @Test
    public void testChangeListState() throws Exception {
        tradeListService.createNewList();
        tradeListService.setState(TradeListState.CLOSED);
        assertThat(tradeListService.findMostRecentList().getState(), is(equalTo(TradeListState.CLOSED)));
    }

    @Test
    public void testAllItemsAreAssignedToCurrentOpenList() throws Exception {
        tradeListService.createNewList();
        NewTradeItemForm itemForm = new NewTradeItemForm("title", "description", "");
        TradeItem item = tradeItemService.save(itemForm, "username");

        assertThat(item.getTradeList(), is(notNullValue()));
        assertThat(item.getTradeList(), is(instanceOf(TradeList.class)));

        assertThat(tradeItemService.findByRecentTradeList(), hasSize(1));

        tradeListService.createNewList();
        tradeItemService.save(itemForm, "username");
        tradeItemService.save(itemForm, "username");

        assertThat(tradeItemService.findByRecentTradeList(), hasSize(2));
    }

    @Test(expected = IllegalStateException.class)
    public void testCanNotAddItemWhenLatestListIsClosed() throws Exception {
        tradeListService.createNewList();
        tradeListService.setState(TradeListState.CLOSED);

        tradeItemService.save(new NewTradeItemForm("title", "description", ""), "username");
    }

    //TODO: check that cannot close if no list present or current list closed
}