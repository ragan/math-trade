package trade.math.domain.tradeItem.wantListItem;

import trade.math.domain.tradeItem.TradeItem;

import javax.persistence.*;

/**
 * Created by daniel on 23.03.16.
 */
@Entity
@Table(name="WANT_LIST_ITEM")
@IdClass(WantListItemId.class)
public class WantListItem {
    @Id
    @Column(name = "OFFER_TRADE_ITEM_ID")
    private Long offerTradeItemId;
    @Id
    @Column(name = "WANT_TRADE_ITEM_ID")
    private Long wantTradeItemId;
    @Column(name = "PRIORITY")
    private int priority;

    @ManyToOne
    @JoinColumn(name = "OFFER_TRADE_ITEM_ID", insertable = false, updatable = false, referencedColumnName = "ID")
    //@PrimaryKeyJoinColumn(name="OFFER_TRADE_ITEM_ID", referencedColumnName = "ID")
    private TradeItem offerTradeItem;

    @ManyToOne
    @JoinColumn(name = "WANT_TRADE_ITEM_ID", insertable = false, updatable = false, referencedColumnName = "ID")
    //@PrimaryKeyJoinColumn(name="WANT_TRADE_ITEM_ID", referencedColumnName = "ID")
    private TradeItem wantTradeItem;


    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public TradeItem getOfferTradeItem() {
        return offerTradeItem;
    }

    public void setOfferTradeItem(TradeItem offerTradeItem) {
        this.offerTradeItemId = offerTradeItem.getId();
        this.offerTradeItem = offerTradeItem;
    }

    public TradeItem getWantTradeItem() {
        return wantTradeItem;
    }

    public void setWantTradeItem(TradeItem wantTradeItem) {
        this.wantTradeItemId = wantTradeItem.getId();
        this.wantTradeItem = wantTradeItem;
    }
}
