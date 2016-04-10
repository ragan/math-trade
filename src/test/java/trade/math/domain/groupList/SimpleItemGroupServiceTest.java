package trade.math.domain.groupList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class SimpleItemGroupServiceTest {

    private List<ItemGroup> groupListList;

    private ItemGroupService itemGroupService;

    @Mock
    private ItemGroupRepository itemGroupRepository;

    @Before
    public void setUp() throws Exception {
        groupListList = new ArrayList<>();

        MockitoAnnotations.initMocks(this);
        when(itemGroupRepository.save(Mockito.any(ItemGroup.class))).then(invocation -> {
            groupListList.add((ItemGroup) invocation.getArguments()[0]);
            return invocation.getArguments()[0];
        });
        when(itemGroupRepository.save(Mockito.<List>any())).then(invocation -> {
            groupListList.addAll((Collection<? extends ItemGroup>) invocation.getArguments()[0]);
            return invocation.getArguments()[0];
        });
        itemGroupService = new SimpleItemGroupService(itemGroupRepository);
    }

    @Test
    public void testSaveGroupList() throws Exception {
        ItemGroup group = new ItemGroup("test title");
        assertThat(itemGroupService.save(group).getTitle(), is(equalToIgnoringCase("test title")));
    }

    @Test
    public void testSaveGroupLists() throws Exception {
        List<ItemGroup> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add(new ItemGroup("title " + i));
        }
        itemGroupService.save(items);
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

        Map<ItemGroup, List<GroupListItem>> result = itemGroupService.makeGroupLists(items);
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