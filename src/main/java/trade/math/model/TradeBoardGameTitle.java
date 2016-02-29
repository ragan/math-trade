package trade.math.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TradeBoardGameTitle {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private TradeBoardGame tradeBoardGame;

    private String title;

    public TradeBoardGameTitle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TradeBoardGame getTradeBoardGame() {
        return tradeBoardGame;
    }

    public void setTradeBoardGame(TradeBoardGame tradeBoardGame) {
        this.tradeBoardGame = tradeBoardGame;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
