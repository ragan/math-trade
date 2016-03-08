package trade.math.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by daniel on 08.03.16.
 */
public class NewTradeCategoryForm {

    @NotEmpty
    @Length(min = 3, max = 64)
    private String title;

    public NewTradeCategoryForm() {
    }

    public NewTradeCategoryForm(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
