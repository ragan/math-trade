package trade.math.domain.wantList;

import trade.math.domain.tradeItem.TradeItem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "WANT_LIST")
public class WantList {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TRADE_ITEM_ID")
    private TradeItem item;

    @OneToMany(mappedBy = "wantList", fetch = FetchType.EAGER)
    private List<WantListEntry> entries = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TradeItem getItem() {
        return item;
    }

    public void setItem(TradeItem item) {
        this.item = item;
    }

    public List<WantListEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<WantListEntry> entries) {
        this.entries = entries;
    }

}
