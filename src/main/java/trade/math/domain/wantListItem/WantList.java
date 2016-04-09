package trade.math.domain.wantListItem;

import trade.math.domain.tradeItem.TradeItem;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "WANT_LIST")
public class WantList {

    @Id
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TRADE_ITEM_ID")
    private TradeItem offer;

    @OneToMany(mappedBy = "wantList")
    private List<WantListEntry> wantListEntries;

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

    public List<WantListEntry> getWantListEntries() {
        return wantListEntries;
    }

    public void setWantListEntries(List<WantListEntry> wantListEntries) {
        this.wantListEntries = wantListEntries;
    }
}
