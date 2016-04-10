package trade.math.domain.tradeUser;

import trade.math.TradeUserRole;
import trade.math.form.NewTradeUserForm;
import trade.math.model.TradeUser;

import java.util.Optional;

/**
 * Created by karol on 18.02.16.
 */
public interface TradeUserService {

    TradeUser save(NewTradeUserForm newTradeUserForm, TradeUserRole tradeUserRole);

    TradeUser save(NewTradeUserForm newTradeUserForm);

    /**
     * Removes all users from the system. Admin account is not removed.
     */
    void deleteAll();

    /**
     * Delete all users with specified role.
     *
     * @param tradeUserRole role
     */
    void deleteAll(TradeUserRole tradeUserRole);

    Optional<TradeUser> findByUsername(String username);
}
