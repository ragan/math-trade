package trade.math.model.dto;

import trade.math.model.TradeBoardGame;
import trade.math.model.TradeBoardGameTitle;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class TradeBoardGameDTO {

    private int bggId;

    private List<String> names;

    private String imageUrl;

    private String thumbnailUrl;

    private int minPlayers;

    private int maxPlayers;

    private int yearPublished;

    private String designer;

    public TradeBoardGameDTO() {
    }

    public TradeBoardGameDTO(int bggId, List<String> names, String imageUrl, String thumbnailUrl,
                             int minPlayers, int maxPlayers, int yearPublished, String designer) {
        this.bggId = bggId;
        this.names = names;
        this.imageUrl = imageUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.yearPublished = yearPublished;
        this.designer = designer;
    }

    public TradeBoardGameDTO(TradeBoardGame tradeBoardGame) {
        this.bggId = tradeBoardGame.getBggId();
        this.names = tradeBoardGame.getNames().stream().map(TradeBoardGameTitle::getTitle).collect(toList());
        this.imageUrl = tradeBoardGame.getImageUrl();
        this.thumbnailUrl = tradeBoardGame.getThumbnailUrl();
        this.minPlayers = tradeBoardGame.getMinPlayers();
        this.maxPlayers = tradeBoardGame.getMaxPlayers();
        this.yearPublished = tradeBoardGame.getYearPublished();
        this.designer = tradeBoardGame.getDesigner();
    }

    public TradeBoardGame makeTradeBoardGame() {
        TradeBoardGame tradeBoardGame = new TradeBoardGame();
        tradeBoardGame.setBggId(getBggId());
        tradeBoardGame.setNames(getNames().stream().map(t -> {
            TradeBoardGameTitle tradeBoardGameTitle = new TradeBoardGameTitle();
            tradeBoardGameTitle.setTitle(t);
            tradeBoardGameTitle.setTradeBoardGame(tradeBoardGame);
            return tradeBoardGameTitle;
        }).collect(toList()));
        tradeBoardGame.setImageUrl(getImageUrl());
        tradeBoardGame.setThumbnailUrl(getThumbnailUrl());
        tradeBoardGame.setMinPlayers(getMinPlayers());
        tradeBoardGame.setMaxPlayers(getMaxPlayers());
        tradeBoardGame.setYearPublished(getYearPublished());
        tradeBoardGame.setDesigner(getDesigner());

        return tradeBoardGame;
    }

    public int getBggId() {
        return bggId;
    }

    public void setBggId(int bggId) {
        this.bggId = bggId;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
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
