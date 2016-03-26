package trade.math.domain.tradeList;

public class TradeListStatusDTO {

    //TODO: TEST IT!

    private TradeList tradeList;

    public TradeListStatusDTO(TradeList tradeList) {
        this.tradeList = tradeList;
    }

    public Boolean exists() {
        return tradeList != null;
    }

    public Boolean canOpen() {
        if (tradeList == null) return false;
        return tradeList.getState() == TradeListState.CLOSED;
    }

    public Boolean canClose() {
        if (tradeList == null) return false;
        return tradeList.getState() == TradeListState.OPEN;
    }

    public Boolean isClosed() {
        return canOpen();
    }
}