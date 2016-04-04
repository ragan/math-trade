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
import trade.math.form.NewTradeItemForm;
import trade.math.form.NewTradeUserForm;
import trade.math.model.TradeItemCategory;
import trade.math.service.TradeUserService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MtApplication.class)
@ActiveProfiles("test")
public class GroupListServiceTest {

    @Autowired
    private GroupListService groupListService;

    @Autowired
    private TradeItemService tradeItemService;

    @Autowired
    private TradeUserService tradeUserService;

    @Before
    public void setUp() throws Exception {
        groupListService.deleteAll();

        tradeUserService.deleteAll();
        tradeUserService.save(new NewTradeUserForm("username", "user@email.com", "password", "password"));
    }

    @Test
    public void testSaveSingleGroupList() throws Exception {
        String TITLE = "title";

        GroupListDTO dto = groupListService.save(new GroupListDTO(TITLE));
        assertThat(dto, is(notNullValue()));
        assertThat(dto.getId(), is(notNullValue()));
        assertThat(dto.getTitle(), equalTo(TITLE));
    }

    @Test
    public void testSaveMultipleGroupLists() throws Exception {
        String TITLE = "title";
        List<GroupListDTO> dtos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dtos.add(new GroupListDTO(TITLE + i));
        }
        groupListService.save(dtos);
        assertThat(groupListService.findAll(), hasSize(10));
    }

    //TODO: test unique titles
}