package trade.math.domain.tradeItem.wantListItem;

/**
 * Created by daniel on 24.03.16.
 */
public interface WantListItemService {

    WantListItem save(WantListItem wantListItem);
    boolean update(WantListItem wantListItem);
    boolean delete(WantListItem wantListItem);
}
