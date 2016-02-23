package trade.math.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import trade.math.TradeUserRole;
import trade.math.form.NewTradeUserForm;
import trade.math.model.TradeUser;
import trade.math.repository.TradeUserRepository;

import javax.validation.Valid;
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
    public TradeUser save(NewTradeUserForm newTradeUserForm) {
        TradeUser tradeUser = new TradeUser();
        tradeUser.setUsername(newTradeUserForm.getUsername());
        tradeUser.setEmail(newTradeUserForm.getEmail());
        tradeUser.setPassword(new BCryptPasswordEncoder().encode(newTradeUserForm.getPassword()));
        tradeUser.setRole(TradeUserRole.ROLE_USER);
        return tradeUserRepository.save(tradeUser);
    }

    @Override
    public void deleteAll() {
        tradeUserRepository.deleteAll();
    }

    @Override
    public Optional<TradeUser> findByUsername(String username) {
        TradeUser oneByUsername = tradeUserRepository.findOneByUsername(username);
        return Optional.of(oneByUsername);
    }
}
