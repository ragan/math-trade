package trade.math.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import trade.math.model.TradeCategory;

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

    private TradeCategory category;

    public NewTradeItemForm() {
        //
    }

    public NewTradeItemForm(String title, String description, String imageUrl, TradeCategory category) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
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

    public TradeCategory getCategory() {
        return category;
    }

    public void setCategory(TradeCategory category) {
        this.category = category;
    }
}
