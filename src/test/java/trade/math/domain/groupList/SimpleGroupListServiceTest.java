package trade.math.domain.groupList;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        when(groupListRepository.save(Mockito.<List>any())).then(invocation -> {
            groupListList.addAll((Collection<? extends GroupList>) invocation.getArguments()[0]);
            return invocation.getArguments()[0];
        });
        groupListService = new SimpleGroupListService(groupListRepository);
    }

    @Test
    public void testSaveGroupList() throws Exception {
        GroupListDTO dto = new GroupListDTO("test title");
        assertThat(groupListService.save(dto).getTitle(), is(equalToIgnoringCase("test title")));
    }

    @Test
    public void testSaveGroupLists() throws Exception {
        List<GroupListDTO> dtos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dtos.add(new GroupListDTO("title " + i));
        }
        groupListService.save(dtos);
        assertThat(this.groupListList, hasSize(10));
    }

    @Test
    public void testGroupItems() throws Exception {
        List<GroupListItem> items = new ArrayList<>();
        items.add(new GroupableItem<>("a"));
        items.add(new GroupableItem<>("a"));
        items.add(new GroupableItem<>("b"));
        items.add(new GroupableItem<>("b"));
        items.add(new GroupableItem<>("c"));

        Map<GroupListDTO, List<GroupListItem>> result = groupListService.makeGroupLists(items);
        assertThat(result.size(), is(3));
        assertThat(groupListList, hasSize(3));
    }

    private class GroupableItem<T> implements GroupListItem<T> {

        private T property;

        public GroupableItem(T property) {
            this.property = property;
        }

        @Override
        public T getProperty() {
            return this.property;
        }
    }
}