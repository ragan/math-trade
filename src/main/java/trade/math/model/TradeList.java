package trade.math.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRADE_LIST")
public class TradeList {

    @Id
    @GeneratedValue
    @Column(name = "LIST_ID")
    private Long id;

    @Column(name = "CREATION_TIME")
    private Date creationTime;

    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    private TradeListState state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public TradeListState getState() {
        return state;
    }

    public void setState(TradeListState state) {
        this.state = state;
    }
}
