package trade.math;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import trade.math.form.NewTradeUserForm;
import trade.math.domain.tradeUser.TradeUserService;

/**
 * Created by karol on 23.02.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MtApplication.class)
@ActiveProfiles("test")
public class MtUserDetailsServiceTest extends TestCase {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TradeUserService tradeUserService;

    @Test
    public void testFindByUsername() throws Exception {
        tradeUserService.save(new NewTradeUserForm("testuser", "mail@test.com", "testpassword", "testpassword"));

        assertNotNull(userDetailsService.loadUserByUsername("testuser"));
    }
}