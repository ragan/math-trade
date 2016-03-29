package trade.math.domain.groupList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<GroupListDTO> findAll() {
        return groupListRepository.findAll().stream().map(GroupListDTO::new).collect(toList());
    }

    @Override
    public GroupListDTO save(GroupListDTO groupListDTO) {
        GroupList groupList = groupListRepository.save(groupListDTO.toGroupList());
        return new GroupListDTO(groupList);
    }

    @Override
    public List<GroupListDTO> save(List<GroupListDTO> groupListDTOs) {
        List<GroupList> lists = groupListRepository.save(groupListDTOs.stream()
                .map(GroupListDTO::toGroupList)
                .collect(toList()));
        return lists.stream().map(GroupListDTO::new).collect(toList());
    }

    @Override
    public void deleteAll() {
        groupListRepository.deleteAll();
    }

    @Override
    public Map<GroupListDTO, List<GroupListItem>> makeGroupLists(List<GroupListItem> byRecentTradeList) {
        Map<Object, List<GroupListItem>> group = byRecentTradeList.stream()
                .collect(groupingBy(GroupListItem::getProperty));
        return group.entrySet().stream()
                .collect(toMap(e -> save(new GroupListDTO("e")), Map.Entry::getValue));
    }
}
