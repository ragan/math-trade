package trade.math.service;

import trade.math.form.NewTradeUserForm;
import trade.math.model.TradeUser;

/**
 * Created by karol on 18.02.16.
 */
public interface TradeUserService {

    TradeUser save(NewTradeUserForm newTradeUserForm);

    void deleteAllTradeUsers();

    TradeUser findByUsername(String username);
}
