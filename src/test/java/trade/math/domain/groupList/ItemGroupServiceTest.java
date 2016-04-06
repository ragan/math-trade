package trade.math.domain.groupList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import trade.math.MtApplication;
import trade.math.domain.tradeItem.TradeItemService;
import trade.math.form.NewTradeUserForm;
import trade.math.service.TradeUserService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MtApplication.class)
@ActiveProfiles("test")
public class ItemGroupServiceTest {

    @Autowired
    private ItemGroupService itemGroupService;

    @Autowired
    private TradeItemService tradeItemService;

    @Autowired
    private TradeUserService tradeUserService;

    @Before
    public void setUp() throws Exception {
        itemGroupService.deleteAll();

        tradeUserService.deleteAll();
        tradeUserService.save(new NewTradeUserForm("username", "user@email.com", "password", "password"));
    }

    @Test
    public void testSaveSingleGroupList() throws Exception {
        String TITLE = "title";

        ItemGroup group = itemGroupService.save(new ItemGroup(TITLE));
        assertThat(group, is(notNullValue()));
        assertThat(group.getId(), is(notNullValue()));
        assertThat(group.getTitle(), equalTo(TITLE));
    }

    @Test
    public void testSaveMultipleGroupLists() throws Exception {
        String TITLE = "title";
        List<ItemGroup> dtos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dtos.add(new ItemGroup(TITLE + i));
        }
        itemGroupService.save(dtos);
        assertThat(itemGroupService.findAll(), hasSize(10));
    }

    //TODO: test unique titles
}