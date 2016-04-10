package trade.math;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import trade.math.model.TradeUser;
import trade.math.domain.tradeUser.TradeUserService;

/**
 * Created by karol on 23.02.16.
 */
@Component
public class MtUserDetailsService implements UserDetailsService {

    private final TradeUserService tradeUserService;

    @Autowired
    public MtUserDetailsService(TradeUserService tradeUserService) {
        this.tradeUserService = tradeUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TradeUser tradeUser = tradeUserService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with %s not found", username)));
        return new User(tradeUser.getUsername(), tradeUser.getPassword(),
                AuthorityUtils.createAuthorityList(tradeUser.getRole().name()));
    }
}
