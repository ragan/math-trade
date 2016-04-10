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

@Component
public class MtUserDetailsService implements UserDetailsService {

    private final TradeUserService tradeUserService;

    @Autowired
    public MtUserDetailsService(TradeUserService tradeUserService) {
        this.tradeUserService = tradeUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            TradeUser tradeUser = tradeUserService.findByUsername(username);
            return new User(tradeUser.getUsername(), tradeUser.getPassword(),
                    AuthorityUtils.createAuthorityList(tradeUser.getRole().name()));
        } catch (UsernameNotFoundException e) {
            return null;
        }
    }
}
