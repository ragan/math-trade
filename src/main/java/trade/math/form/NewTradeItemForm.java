package trade.math.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by karol on 17.02.16.
 */
public class NewTradeItemForm {

    @NotNull
    private Integer bggId;

    @NotEmpty
    @Length(min = 1, max = 128)
    private String description;

    public NewTradeItemForm() {
        //
    }

    public NewTradeItemForm(Integer bggId, String description) {
        this.bggId = bggId;
        this.description = description;
    }

    public Integer getBggId() {
        return bggId;
    }

    public void setBggId(Integer bggId) {
        this.bggId = bggId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
