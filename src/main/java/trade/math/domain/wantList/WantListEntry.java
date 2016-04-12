package trade.math.domain.wantList;

import trade.math.domain.tradeItem.TradeItem;

import javax.persistence.*;

@Entity
@Table(name = "WANT_LIST_ENTRY")
public class WantListEntry {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "WANT_LIST_ID")
    private WantList wantList;

    @ManyToOne
    @JoinColumn(name = "TRADE_ITEM_ID")
    private TradeItem item;

    private int priority;

    public WantListEntry() {
        //
    }

    public WantListEntry(WantList wantList, TradeItem item, int priority) {
        this.wantList = wantList;
        this.item = item;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WantList getWantList() {
        return wantList;
    }

    public void setWantList(WantList wantList) {
        this.wantList = wantList;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public TradeItem getItem() {
        return item;
    }

    public void setItem(TradeItem item) {
        this.item = item;
    }
}
