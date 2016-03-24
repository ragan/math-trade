package trade.math.domain.tradeList;

/**
 * Created by karol on 23.03.16.
 */
public class IllegalTradeListStateChange extends RuntimeException {
    public IllegalTradeListStateChange(String s) {
        super(s);
    }
}
