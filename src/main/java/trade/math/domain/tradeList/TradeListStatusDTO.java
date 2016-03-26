package trade.math.domain.tradeList;

import java.util.Optional;

public class TradeListStatusDTO {

    private Optional<TradeList> tradeList;

    public TradeListStatusDTO(TradeList tradeList) {
        this(Optional.of(tradeList));
    }

    public TradeListStatusDTO(Optional<TradeList> tradeList) {
        this.tradeList = tradeList;
    }

    public Boolean canOpen() {
        return tradeList.map(l -> l.getState() == TradeListState.CLOSED).orElse(false);
    }

    public Boolean canClose() {
        return tradeList.map(tl -> tl.getState() == TradeListState.OPEN).orElse(false);
    }

    public boolean isOpen() {
        return !isClosed();
    }

    public Boolean isClosed() {
        return canOpen();
    }

    public boolean isDone() {
        return tradeList.map(tl -> tl.getState() == TradeListState.DONE).orElse(false);
    }

    public boolean canGroup() {
        return (isOpen() || isClosed()) && !isDone();
    }

    public boolean canTrade() {
        return isClosed();
    }
}