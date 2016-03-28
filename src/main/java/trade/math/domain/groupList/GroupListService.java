package trade.math.domain.groupList;

import java.util.List;
import java.util.Map;

public interface GroupListService {

    List<GroupListDTO> findAll();

    GroupListDTO save(GroupListDTO groupListDTO);

    List<GroupListDTO> save(List<GroupListDTO> groupListDTOs);

    void deleteAll();

    <T> Map<GroupListDTO, List<GroupListItem<T>>> makeGroupLists(List<GroupListItem<T>> byRecentTradeList);
}
