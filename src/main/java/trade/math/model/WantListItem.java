package trade.math.model;

import com.sun.javafx.beans.IDProperty;
import trade.math.model.id.WantListItemId;

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
    //@PrimaryKeyJoinColumn(name="OFFER_TRADE_ITEM_ID", referencedColumnName = "ID")
    @JoinColumn(name = "OFFER_TRADE_ITEM_ID", insertable = false, updatable = false, referencedColumnName = "ID")
    private TradeItem offerTradeItem;

    @ManyToOne
    @JoinColumn(name = "WANT_TRADE_ITEM_ID", insertable = false, updatable = false, referencedColumnName = "ID")
    //@PrimaryKeyJoinColumn(name="WANT_TRADE_ITEM_ID", referencedColumnName = "ID")
    private TradeItem wantTradeItem;


}
