package trade.math.controller;

import com.sun.security.auth.UserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import trade.math.MtApplication;
import trade.math.SecurityConfiguration;
import trade.math.domain.tradeItem.TradeItem;
import trade.math.domain.tradeItem.TradeItemService;
import trade.math.domain.tradeUser.TradeUserService;
import trade.math.domain.wantList.WantList;
import trade.math.domain.wantList.WantListService;
import trade.math.form.NewTradeItemForm;
import trade.math.form.NewTradeUserForm;
import trade.math.model.TradeUser;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MtApplication.class, SecurityConfiguration.class})
@WebAppConfiguration
@ActiveProfiles("test")
public class WantListControllerTest {

    @Autowired
    WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private TradeUserService tradeUserService;

    @Autowired
    private TradeItemService tradeItemService;

    @Autowired
    private WantListService wantListService;

    private TradeUser user0;
    private TradeItem item0;
    private TradeUser user1;
    private TradeItem item1;
    private TradeItem item2;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        wantListService.deleteAll();
        tradeItemService.deleteAll();
        tradeUserService.deleteAll();

        user0 = tradeUserService.save(new NewTradeUserForm("user0", "email@user0", "password", "password"));
        item0 = tradeItemService.save(new NewTradeItemForm("title0", "desc0", ""), user0);
        user1 = tradeUserService.save(new NewTradeUserForm("user1", "email@user1", "password", "password"));
        item1 = tradeItemService.save(new NewTradeItemForm("title1", "desc1", ""), user1);
        item2 = tradeItemService.save(new NewTradeItemForm("title2", "desc2", ""), user1);
    }

    @Test
    public void test_UserShouldAuthenticateFirst() throws Exception {
        mockMvc.perform(get("/wantList"))
                .andExpect(status().is3xxRedirection());
        mockMvc.perform(get("/wantList/entries"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(value = "user0")
    public void test_PrioritiesShouldChangeOrderBasedOnPutArrayValues() throws Exception {
        String item0Id = String.valueOf(item0.getId());
        String item1Id = String.valueOf(item1.getId());
        String item2Id = String.valueOf(item2.getId());

        mockMvc.perform(put("/wantList/entries")
                .param("itemId", item0Id)
                .param("wantIds[]", item1Id, item2Id))

                .andExpect(status().isOk());
        WantList wantList = wantListService.findByItem(tradeItemService.findById(item0.getId()));
        assertThat(wantList.getEntries(), hasSize(2));
        assertThat(wantListService.findEntry(item0, item1).getPriority(), is(1));
        assertThat(wantListService.findEntry(item0, item2).getPriority(), is(2));

        mockMvc.perform(put("/wantList/entries")
                .param("itemId", item0Id)
                .param("wantIds[]", item2Id, item1Id))

                .andExpect(status().isOk());
        wantList = wantListService.findByItem(tradeItemService.findById(item0.getId()));
        assertThat(wantList.getEntries(), hasSize(2));
        assertThat(wantListService.findEntry(item0, item1).getPriority(), is(2));
        assertThat(wantListService.findEntry(item0, item2).getPriority(), is(1));
    }

    @Test
    @WithMockUser
    public void test_ItemsShouldBeClearAfterSendingEmptyArray() throws Exception {
        String item0Id = String.valueOf(item0.getId());

        wantListService.setWants(item0, Arrays.asList(item1, item2));
        WantList wantList = wantListService.findByItem(tradeItemService.findById(item0.getId()));
        assertThat(wantList.getEntries(), hasSize(2));

        mockMvc.perform(put("/wantList/entries")
                .param("itemId", item0Id))

                .andExpect(status().isOk());
        wantList = wantListService.findByItem(tradeItemService.findById(item0.getId()));
        assertThat(wantList.getEntries(), is(empty()));
    }
}