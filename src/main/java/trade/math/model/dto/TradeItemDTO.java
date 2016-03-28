package trade.math.model.dto;

import trade.math.model.TradeBoardGameProperties;
import trade.math.model.TradeItem;
import trade.math.model.TradeItemCategory;

import java.util.Optional;

import static trade.math.model.TradeItemCategory.*;

public class TradeItemDTO {

    private long id;

    private String title;

    private String description;

    private String imgUrl;

    private boolean deletable;

    private TradeItemCategory category;

    private Integer bggId;

    public TradeItemDTO() {
        //
    }

    public TradeItemDTO(TradeItem tradeItem, boolean deletable) {
        this(tradeItem, deletable, Optional.empty());
    }

    public TradeItemDTO(TradeItem tradeItem, boolean deletable, Optional<TradeBoardGameProperties> properties) {
        this.id = tradeItem.getId();
        this.title = tradeItem.getTitle();
        this.description = tradeItem.getDescription();
        this.imgUrl = tradeItem.getImgUrl();
        this.category = tradeItem.getCategory();
        this.deletable = deletable;
        this.bggId = properties.map(p -> p.getBggId()).orElse(null);
    }

    public TradeItemDTO(long id, String title, String description, String imgURL, boolean canDelete) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imgUrl = imgURL;
        this.deletable = canDelete;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return isDeletable();
    }

    public void setCanDelete(boolean canDelete) {
        setDeletable(canDelete);
    }

    public boolean isDeletable() {
        return this.deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public TradeItemCategory getCategory() {
        return category;
    }

    public void setCategory(TradeItemCategory category) {
        this.category = category;
    }

    public Integer getBggId() {
        return bggId;
    }

    public void setBggId(Integer bggId) {
        this.bggId = bggId;
    }
}
