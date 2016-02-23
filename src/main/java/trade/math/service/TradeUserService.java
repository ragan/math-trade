package trade.math.service;

import trade.math.form.NewTradeUserForm;
import trade.math.model.TradeUser;

import java.util.Optional;

/**
 * Created by karol on 18.02.16.
 */
public interface TradeUserService {

    TradeUser save(NewTradeUserForm newTradeUserForm);

    void deleteAll();

    Optional<TradeUser> findByUsername(String username);
}
