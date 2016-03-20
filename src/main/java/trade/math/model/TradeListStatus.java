package trade.math.model;

public class TradeListStatus {

    //TODO: TEST IT!

    private TradeList tradeList;

    public TradeListStatus(TradeList tradeList) {
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
}