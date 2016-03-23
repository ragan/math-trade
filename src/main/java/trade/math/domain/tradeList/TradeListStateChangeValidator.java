package trade.math.domain.tradeList;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by karol on 23.03.16.
 */
public class TradeListStateChangeValidator {

    private static final Map<TradeListState, TradeListState> allowedStateChanges = new HashMap<>();

    static {
        allowedStateChanges.put(TradeListState.CLOSED, TradeListState.OPEN);
        allowedStateChanges.put(TradeListState.OPEN, TradeListState.CLOSED);
    }

    public static boolean assertCanChangeState(TradeListState currentState, TradeListState targetState) {
        if (allowedStateChanges.get(currentState) != targetState)
            throw new IllegalTradeListStateChange(String.format("Cannot change list state from %s to %s.",
                    currentState, targetState));
        return true;
    }

}
