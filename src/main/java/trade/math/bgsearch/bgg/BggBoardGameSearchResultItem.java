package trade.math.bgsearch.bgg;

import trade.math.bgsearch.BoardGameSearchResultItem;

public class BggBoardGameSearchResultItem implements BoardGameSearchResultItem {

    private int id;

    private String title;

    private int bggId;

    private String thumbnailUrl;

    public BggBoardGameSearchResultItem() {
    }

    public BggBoardGameSearchResultItem(String title, int bggId, String thumbnailUrl) {
        this(bggId, title, bggId, thumbnailUrl);
    }

    public BggBoardGameSearchResultItem(int id, String title, int bggId, String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.bggId = bggId;
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBggId() {
        return bggId;
    }

    public void setBggId(int bggId) {
        this.bggId = bggId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
