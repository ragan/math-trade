package trade.math.domain.wantListItem;

import trade.math.domain.tradeItem.TradeItem;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "WANT_LIST")
public class WantList {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TRADE_ITEM_ID")
    private TradeItem offer;

    @OneToMany(mappedBy = "wantList")
    private List<WantListItem> wantListItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TradeItem getOffer() {
        return offer;
    }

    public void setOffer(TradeItem offer) {
        this.offer = offer;
    }

    public List<WantListItem> getWantListItems() {
        return wantListItems;
    }

    public void setWantListItems(List<WantListItem> wantListItems) {
        this.wantListItems = wantListItems;
    }
}
