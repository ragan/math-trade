package trade.math.domain.groupList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleGroupListService implements GroupListService {

    private final GroupListRepository groupListRepository;

    @Autowired
    public SimpleGroupListService(GroupListRepository groupListRepository) {
        this.groupListRepository = groupListRepository;
    }

    @Override
    public GroupListDTO save(GroupListDTO groupListDTO) {
        GroupList groupList = groupListRepository.save(groupListDTO.toGroupList());
        return new GroupListDTO(groupList);
    }
}
