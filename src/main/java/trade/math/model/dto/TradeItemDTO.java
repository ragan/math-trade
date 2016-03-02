package trade.math.model.dto;

/**
 * Created by karol on 01.03.16.
 */
public class TradeItemDTO {

    private long id;
    private String title;
    private String description;
    private String imgUrl;
    private boolean canDelete;

    public TradeItemDTO() {
    }

    public TradeItemDTO(long id, String title, String description, String imgURL, boolean canDelete) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imgUrl = imgURL;
        this.canDelete = canDelete;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgURL) {
        this.imgUrl = imgURL;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
