package trade.math.domain.groupList;

import java.util.List;
import java.util.Map;

public interface GroupListService {

    List<GroupList> findAll();

    GroupList save(GroupList groupList);

    List<GroupList> save(List<GroupList> groupLists);

    void deleteAll();

    Map<GroupList, List<GroupListItem>> makeGroupLists(List<GroupListItem> items);
}
