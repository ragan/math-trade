package trade.math.domain.wantList;

import trade.math.domain.tradeItem.TradeItem;

public class WantListEntryDTO {

    private TradeItem offer;

    private String shortTitle;

    public WantListEntryDTO(WantListEntry entry) {
        this.offer = entry.getWantList().getItem();

        shortTitle = offer.getTitle();
        if (shortTitle.length() > 15) {
            shortTitle = offer.getTitle().substring(0, 15) + "...";
        }
    }

    public TradeItem getOffer() {
        return offer;
    }

    public void setOffer(TradeItem offer) {
        this.offer = offer;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

}
