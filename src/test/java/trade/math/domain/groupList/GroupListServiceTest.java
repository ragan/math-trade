package trade.math.domain.groupList;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import trade.math.MtApplication;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MtApplication.class)
@ActiveProfiles("test")
public class GroupListServiceTest {

    @Autowired
    private GroupListService groupListService;

    @Before
    public void setUp() throws Exception {
        groupListService.deleteAll();
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
}