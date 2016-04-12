package trade.math.domain.wantList;

import trade.math.domain.tradeItem.TradeItem;

public class WantListEntryDTO {

    private TradeItem offer;

    private TradeItem want;

    private Long id;

    private String title;

    private String shortTitle;

    public WantListEntryDTO(WantListEntry entry) {
        this.offer = entry.getWantList().getItem();
        this.want = entry.getItem();

        id = want.getId();
        title = want.getTitle();
        shortTitle = title;
        if (shortTitle.length() > 15) {
            shortTitle = shortTitle.substring(0, 15) + "...";
        }
    }

    public TradeItem getWant() {
        return want;
    }

    public void setWant(TradeItem want) {
        this.want = want;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
