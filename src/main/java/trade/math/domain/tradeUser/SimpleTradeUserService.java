package trade.math.domain.tradeUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import trade.math.TradeUserRole;
import trade.math.form.NewTradeUserForm;
import trade.math.model.TradeUser;
import trade.math.domain.tradeUser.TradeUserRepository;
import trade.math.domain.tradeUser.TradeUserService;

import java.util.Optional;

/**
 * Created by karol on 18.02.16.
 */
@Service
public class SimpleTradeUserService implements TradeUserService {

    private TradeUserRepository tradeUserRepository;

    @Autowired
    public SimpleTradeUserService(TradeUserRepository tradeUserRepository) {
        this.tradeUserRepository = tradeUserRepository;
    }

    @Override
    public TradeUser save(NewTradeUserForm newTradeUserForm, TradeUserRole tradeUserRole) {
        TradeUser tradeUser = new TradeUser();
        tradeUser.setUsername(newTradeUserForm.getUsername());
        tradeUser.setEmail(newTradeUserForm.getEmail());
        tradeUser.setPassword(new BCryptPasswordEncoder().encode(newTradeUserForm.getPassword()));
        tradeUser.setRole(tradeUserRole);
        return tradeUserRepository.save(tradeUser);
    }

    @Override
    public TradeUser save(NewTradeUserForm newTradeUserForm) {
        return save(newTradeUserForm, TradeUserRole.ROLE_USER);
    }

    @Override
    public void deleteAll() {
        deleteAll(TradeUserRole.ROLE_USER);
    }

    @Override
    public void deleteAll(TradeUserRole tradeUserRole) {
        tradeUserRepository.delete(tradeUserRepository.findAllByRole(tradeUserRole));
    }

    @Override
    public Optional<TradeUser> findByUsername(String username) {
        TradeUser oneByUsername = tradeUserRepository.findOneByUsername(username);
        if (oneByUsername == null) return Optional.empty();
        return Optional.of(oneByUsername);
    }
}
