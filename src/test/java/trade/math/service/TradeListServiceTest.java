package trade.math.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import trade.math.MtApplication;
import trade.math.domain.tradeItem.TradeItemService;
import trade.math.domain.tradeList.IllegalTradeListStateChange;
import trade.math.domain.tradeList.TradeListService;
import trade.math.domain.tradeUser.TradeUserService;
import trade.math.form.NewTradeItemForm;
import trade.math.form.NewTradeUserForm;
import trade.math.domain.tradeItem.TradeItem;
import trade.math.domain.tradeList.TradeList;
import trade.math.domain.tradeList.TradeListState;

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
        tradeItemService.deleteAll();
        tradeUserService.deleteAll();
        tradeListService.deleteAll();

        tradeUserService.save(new NewTradeUserForm("username", "user@email", "password", "password"));
    }

    @Test
    public void testPersistNewList() throws Exception {
        assertThat(tradeListService.createNewList(), is(notNullValue()));
        assertThat(tradeListService.findAll(), hasSize(1));
        assertThat(tradeListService.findMostRecentList().get().getState(), is(equalTo(TradeListState.OPEN)));
    }

    @Test
    public void testFindMostRecentList() throws Exception {
        //TODO: ten test raz mi nie przeszedł.
        //TODO: a nawet więcej razy... czasem działa, czasem nie (za szybko?).
        TradeList list0 = tradeListService.createNewList();
        TradeList list1 = tradeListService.createNewList();
        TradeList mostRecentList = tradeListService.findMostRecentList().get();
        assertThat(mostRecentList.getId(), is(equalTo(list1.getId())));
    }

    @Test
    public void testChangeListState() throws Exception {
        tradeListService.createNewList();
        tradeListService.setState(TradeListState.CLOSED);
        assertThat(tradeListService.findMostRecentList().get().getState(), is(equalTo(TradeListState.CLOSED)));
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

    @Test
    public void testCanNotAddItemWhenLatestListIsClosed() throws Exception {
        tradeListService.createNewList();
        tradeListService.setState(TradeListState.CLOSED);

        tradeItemService.save(new NewTradeItemForm("title", "description", ""), "username");
        assertThat(tradeItemService.findAll(), hasSize(1));

        assertThat(tradeItemService.findByRecentTradeList(), is(empty()));
        assertThat(tradeItemService.findAll().get(0).getTradeList(), is(nullValue()));
    }

    @Test(expected = IllegalTradeListStateChange.class)
    public void testShouldThrowExceptionOnIllegalStateChange_OPEN_OPEN() throws Exception {
        tradeListService.createNewList();
        tradeListService.setState(TradeListState.OPEN);
    }

    @Test(expected = IllegalTradeListStateChange.class)
    public void testShouldThrowExceptionOnIllegalStateChange_CLOSED_CLOSED() throws Exception {
        tradeListService.createNewList();
        tradeListService.setState(TradeListState.CLOSED);
        tradeListService.setState(TradeListState.CLOSED);
    }

    //TODO: check that cannot close if no list present or current list closed
}