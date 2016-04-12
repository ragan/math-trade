package trade.math.controller;

import com.sun.security.auth.UserPrincipal;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import trade.math.MtApplication;
import trade.math.domain.tradeItem.TradeItem;
import trade.math.domain.tradeItem.TradeItemService;
import trade.math.domain.tradeUser.TradeUserService;
import trade.math.domain.wantList.WantList;
import trade.math.domain.wantList.WantListService;
import trade.math.form.NewTradeItemForm;
import trade.math.form.NewTradeUserForm;
import trade.math.model.TradeUser;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {MtApplication.class})
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

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .dispatchOptions(true)
                .build();
    }

    @Test
    public void testPriorities() throws Exception {
        TradeUser user0 = tradeUserService.save(new NewTradeUserForm("user0", "email@user0", "password", "password"));
        TradeItem item0 = tradeItemService.save(new NewTradeItemForm("title0", "desc0", ""), user0);
        TradeUser user1 = tradeUserService.save(new NewTradeUserForm("user1", "email@user1", "password", "password"));
        TradeItem item1 = tradeItemService.save(new NewTradeItemForm("title1", "desc1", ""), user1);
        TradeItem item2 = tradeItemService.save(new NewTradeItemForm("title2", "desc2", ""), user1);

        String item0Id = String.valueOf(item0.getId());
        String item1Id = String.valueOf(item1.getId());
        String item2Id = String.valueOf(item2.getId());

        mockMvc.perform(put("/wantList/entries")
                .principal(new UserPrincipal("user0"))
                .param("itemId", item0Id)
                .param("wantIds[]", item1Id, item2Id))

                .andExpect(status().isOk());
        WantList wantList = wantListService.findByItem(tradeItemService.findById(item0.getId()));
        assertThat(wantList.getEntries(), hasSize(2));
        assertThat(wantListService.findEntry(item0, item1).getPriority(), is(1));
        assertThat(wantListService.findEntry(item0, item2).getPriority(), is(2));

        mockMvc.perform(put("/wantList/entries")
                .principal(new UserPrincipal("user0"))
                .param("itemId", item0Id)
                .param("wantIds[]", item2Id, item1Id))

                .andExpect(status().isOk());

        wantList = wantListService.findByItem(tradeItemService.findById(item0.getId()));
        assertThat(wantList.getEntries(), hasSize(2));
        assertThat(wantListService.findEntry(item0, item1).getPriority(), is(2));
        assertThat(wantListService.findEntry(item0, item2).getPriority(), is(1));
    }
}