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
    }

    @Test
    public void testSaveSingleGroupList() throws Exception {
        assertThat(groupListService.save(new GroupListDTO("title")), is(notNullValue()));
    }
}