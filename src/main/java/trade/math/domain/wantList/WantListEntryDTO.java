package trade.math.domain.wantList;

import trade.math.domain.tradeItem.TradeItem;

public class WantListEntryDTO {

    private final TradeItem offer;

    public WantListEntryDTO(WantListEntry entry) {
        this.offer = entry.getWantList().getItem();
    }

}
