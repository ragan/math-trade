package trade.math.domain.wantList;

public class WantListDTO {

    private Long wantTradeItemId;
    private String wantTradeItemTitle;
    private int priority;

    public WantListDTO(Long wantTradeItemId, String wantTradeItemTitle, int priority) {
        this.wantTradeItemId = wantTradeItemId;
        this.wantTradeItemTitle = wantTradeItemTitle;
        this.priority = priority;
    }

    public Long getWantTradeItemId() {
        return wantTradeItemId;
    }

    public void setWantTradeItemId(Long wantTradeItemId) {
        this.wantTradeItemId = wantTradeItemId;
    }

    public String getWantTradeItemTitle() {
        return wantTradeItemTitle;
    }

    public void setWantTradeItemTitle(String wantTradeItemTitle) {
        this.wantTradeItemTitle = wantTradeItemTitle;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
