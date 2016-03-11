package trade.math.service;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import trade.math.MtApplication;
import trade.math.TradeUserRole;
import trade.math.form.NewTradeUserForm;
import trade.math.model.TradeUser;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by karol on 18.02.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MtApplication.class)
@ActiveProfiles("test")
public class TradeUserServiceTest {

    @Autowired
    private TradeUserService tradeUserService;

    @Before
    public void setUp() throws Exception {
        tradeUserService.deleteAll();
    }

    @Test
    public void testIfAdminIsPresent() throws Exception {
        assertThat(tradeUserService.findByUsername("admin").isPresent(), is(true));
    }

    @Test
    public void testWhenUsernameIsNotFound() throws Exception {
        assertThat(tradeUserService.findByUsername("illegal username").isPresent(), is(false));
    }

    @Test
    public void testPersistUserWithAdminRole() throws Exception {
        assertThat(tradeUserService.save(makeNewUserForm("admin_username", "admin@email.com", "password"),
                TradeUserRole.ROLE_ADMIN), is(notNullValue()));
        Optional<TradeUser> username = tradeUserService.findByUsername("admin_username");
        assertThat(username.isPresent(), is(true));
        assertThat(username.get().getRole(), is(TradeUserRole.ROLE_ADMIN));
    }

    @Test
    public void testSaveNewUser() throws Exception {
        NewTradeUserForm newTradeUserForm = makeDefaultNewUserForm();
        assertNotNull(tradeUserService.save(newTradeUserForm)); //jest w bazie admin z tym mailem

        assertNotNull(tradeUserService.findByUsername(newTradeUserForm.getUsername()));
        assertTrue(tradeUserService.findByUsername(newTradeUserForm.getUsername()).get().getUsername()
                .equals(newTradeUserForm.getUsername()));
    }

    @Test(expected = Exception.class)
    public void testEmailCanNotBeEmpty() throws Exception {
        NewTradeUserForm newTradeUserForm = makeDefaultNewUserForm();
        newTradeUserForm.setEmail("");
        tradeUserService.save(newTradeUserForm);
    }

    @Test
    public void testPasswordCanNotBeEmpty() throws Exception {
        NewTradeUserForm newTradeUserForm = makeDefaultNewUserForm();
        newTradeUserForm.setPassword("");
        tradeUserService.save(newTradeUserForm);

        assertFalse(tradeUserService.findByUsername(newTradeUserForm.getUsername()).get().getPassword().equals(""));
    }

    @Test(expected = Exception.class)
    public void testUsernameCanNotBeEmpty() throws Exception {
        NewTradeUserForm newTradeUserForm = makeDefaultNewUserForm();
        newTradeUserForm.setUsername("");
        tradeUserService.save(newTradeUserForm);
    }

    @Test(expected = Exception.class)
    public void testUsernameMustBeUnique() throws Exception {
        tradeUserService.save(makeDefaultNewUserForm());
        tradeUserService.save(makeDefaultNewUserForm());
    }

    @Test(expected = Exception.class)
    public void testEmailMustBeUnique() throws Exception {
        NewTradeUserForm newTradeUserForm0 = makeDefaultNewUserForm();
        newTradeUserForm0.setUsername("username0");
        NewTradeUserForm newTradeUserForm1 = makeDefaultNewUserForm();
        newTradeUserForm1.setUsername("username1");
        //email is the same so error expected
        tradeUserService.save(newTradeUserForm0);
        tradeUserService.save(newTradeUserForm1);
    }

    private NewTradeUserForm makeDefaultNewUserForm() {
        return makeNewUserForm("username", "some@email.com", "password");
    }

    private NewTradeUserForm makeNewUserForm(String username, String email, String password) {
        NewTradeUserForm newTradeUserForm = new NewTradeUserForm();
        newTradeUserForm.setUsername(username);
        newTradeUserForm.setEmail(email);
        newTradeUserForm.setPassword(password);
        return newTradeUserForm;
    }
}