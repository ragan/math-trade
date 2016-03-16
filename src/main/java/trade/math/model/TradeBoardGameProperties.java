package trade.math.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by daniel on 11.03.16.
 */
@Entity
@Table(name = "TRADE_BOARD_GAME_PROPERTIES")
public class TradeBoardGameProperties {

    @Id
    @GeneratedValue
    @Column(name = "PROPERTIES_ID")
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "TRADE_ITEM_ID", nullable = false)
    private TradeItem tradeItem;

    @Column(name = "BGG_ID")
    private Integer bggId;

    public TradeBoardGameProperties() {
    }

    public TradeBoardGameProperties(TradeItem tradeItem, Integer bggId) {
        this.tradeItem = tradeItem;
        this.bggId = bggId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TradeItem getTradeItem() {
        return tradeItem;
    }

    public void setTradeItem(TradeItem tradeItem) {
        this.tradeItem = tradeItem;
    }

    public Integer getBggId() {
        return bggId;
    }

    public void setBggId(Integer bggId) {
        this.bggId = bggId;
    }
}
