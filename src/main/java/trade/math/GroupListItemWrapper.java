package trade.math;

import trade.math.domain.groupList.GroupListItem;
import trade.math.domain.tradeItem.TradeItemDTO;

public class GroupListItemWrapper<T> implements GroupListItem<T> {

    private final TradeItemDTO tradeItemDTO;
    private final PropertyProvider<T> provider;

    public GroupListItemWrapper(TradeItemDTO tradeItemDTO, PropertyProvider<T> provider) {
        this.tradeItemDTO = tradeItemDTO;
        this.provider = provider;
    }

    @Override
    public T getProperty() {
        return provider.getProperty(this.tradeItemDTO);
    }
}
