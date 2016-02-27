package trade.math.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class TradeBoardGame {

    @Id
    @GeneratedValue
    private Long id;

    private int bggId;

    @OneToMany
    private List<TradeBoardGameTitle> titles;

    private String imageUrl;

    private String thumbnailUrl;

    private int minPlayers;

    private int maxPlayers;

    private int yearPublished;

    private String designer;

    public TradeBoardGame() {
    }

    public TradeBoardGame(int bggId, List<TradeBoardGameTitle> titles, String imageUrl, String thumbnailUrl,
                          int minPlayers, int maxPlayers, int yearPublished, String designer) {
        this.bggId = bggId;
        this.titles = titles;
        this.imageUrl = imageUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.yearPublished = yearPublished;
        this.designer = designer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBggId() {
        return bggId;
    }

    public void setBggId(int bggId) {
        this.bggId = bggId;
    }

    public List<TradeBoardGameTitle> getNames() {
        return titles;
    }

    public void setNames(List<TradeBoardGameTitle> names) {
        this.titles = names;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }
}
