package trade.math.domain.groupList;

import java.util.List;

public interface GroupListService {

    List<GroupListDTO> findAll();

    GroupListDTO save(GroupListDTO groupListDTO);

    List<GroupListDTO> save(List<GroupListDTO> groupListDTOs);

    void deleteAll();
}
