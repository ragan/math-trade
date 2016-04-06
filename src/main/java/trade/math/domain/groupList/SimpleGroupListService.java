package trade.math.domain.groupList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trade.math.domain.tradeItem.TradeItem;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
public class SimpleGroupListService implements GroupListService {

    private final GroupListRepository groupListRepository;

    @Autowired
    public SimpleGroupListService(GroupListRepository groupListRepository) {
        this.groupListRepository = groupListRepository;
    }

    @Override
    public List<GroupList> findAll() {
        return groupListRepository.findAll();
    }

    @Override
    public GroupList save(GroupList groupList) {
        return groupListRepository.save(groupList);
    }

    @Override
    public List<GroupList> save(List<GroupList> groupLists) {
        return groupListRepository.save(groupLists);
    }

    @Override
    public void deleteAll() {
        groupListRepository.deleteAll();
    }

    @Override
    public Map<GroupList, List<GroupListItem>> makeGroupLists(List<GroupListItem> items) {
        Map<Object, List<GroupListItem>> group = items.stream()
                .collect(groupingBy(GroupListItem::getProperty));
        return group.entrySet().stream()
                .collect(toMap(e -> save(new GroupList("e")), Map.Entry::getValue));
    }
}
