package trade.math.form;

/**
 * Created by karol on 17.02.16.
 */
public class NewTradeItemForm {

    private int bggId;

    private String description;

    public NewTradeItemForm() {
        //
    }

    public NewTradeItemForm(int bggId, String description) {
        this.bggId = bggId;
        this.description = description;
    }

    public int getBggId() {
        return bggId;
    }

    public void setBggId(int bggId) {
        this.bggId = bggId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
