package trade.math.domain.groupList;

import java.util.List;
import java.util.Map;

public interface ItemGroupService {

    List<ItemGroup> findAll();

    ItemGroup save(ItemGroup groupList);

    List<ItemGroup> save(List<ItemGroup> groupLists);

    void deleteAll();

    Map<ItemGroup, List<GroupListItem>> makeGroupLists(List<GroupListItem> items);
}
