package trade.math.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import trade.math.model.TradeItemCategory;

import javax.validation.constraints.NotNull;

@TitleExistsConstraint
public class NewTradeItemForm {

    @NotEmpty
    @Length(min = 1, max = 128)
    private String title;

    @NotEmpty
    @Length(min = 1, max = 128)
    private String description;

    @NotNull
    private String imageUrl;

    private TradeItemCategory category;

    private int bggId;

    public NewTradeItemForm() {
        //
    }

    public NewTradeItemForm(String title, String description, String imageUrl) {
        this(title, description, imageUrl, TradeItemCategory.NONE, 0);
    }

    public NewTradeItemForm(String title, String description, String imageUrl, TradeItemCategory category, int bggId) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.bggId = bggId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public TradeItemCategory getCategory() {
        return category;
    }

    public void setCategory(TradeItemCategory category) {
        this.category = category;
    }

    public int getBggId() {
        return bggId;
    }

    public void setBggId(int bggId) {
        this.bggId = bggId;
    }
}
