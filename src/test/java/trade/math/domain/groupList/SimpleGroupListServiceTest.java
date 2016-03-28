package trade.math.domain.groupList;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SimpleGroupListServiceTest {

    private List<GroupList> groupListList;

    private GroupListService groupListService;

    @Mock
    private GroupListRepository groupListRepository;

    @Before
    public void setUp() throws Exception {
        groupListList = new ArrayList<>();

        MockitoAnnotations.initMocks(this);
        when(groupListRepository.save(Mockito.any(GroupList.class))).then(invocation -> {
            groupListList.add((GroupList) invocation.getArguments()[0]);
            return invocation.getArguments()[0];
        });

        groupListService = new SimpleGroupListService(groupListRepository);
    }

    @Test
    public void testSaveGroupList() throws Exception {
        GroupListDTO dto = new GroupListDTO("test title");
        assertThat(groupListService.save(dto).getTitle(), is(equalToIgnoringCase("test title")));
    }
}