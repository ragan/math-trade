package trade.math.domain.groupList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Service
public class SimpleItemGroupService implements ItemGroupService {

    private final ItemGroupRepository itemGroupRepository;

    @Autowired
    public SimpleItemGroupService(ItemGroupRepository itemGroupRepository) {
        this.itemGroupRepository = itemGroupRepository;
    }

    @Override
    public List<ItemGroup> findAll() {
        return itemGroupRepository.findAll();
    }

    @Override
    public ItemGroup save(ItemGroup groupList) {
        return itemGroupRepository.save(groupList);
    }

    @Override
    public List<ItemGroup> save(List<ItemGroup> groupLists) {
        return itemGroupRepository.save(groupLists);
    }

    @Override
    public void deleteAll() {
        itemGroupRepository.deleteAll();
    }

    @Override
    public Map<ItemGroup, List<GroupListItem>> makeGroupLists(List<GroupListItem> items) {
        Map<Object, List<GroupListItem>> group = items.stream()
                .collect(groupingBy(GroupListItem::getProperty));
        return group.entrySet().stream()
                .collect(toMap(e -> save(new ItemGroup("e")), Map.Entry::getValue));
    }
}
