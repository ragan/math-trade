package trade.math;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import trade.math.model.TradeListState;
import trade.math.domain.tradeList.TradeListService;
import trade.math.service.TradeUserService;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by karol on 23.02.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port=9000")
@ActiveProfiles(profiles = {"test", TestConfiguration.TITLE_REPO_MOCK})
public class MtApplicationSeleniumTest {

    private WebDriver driver = new FirefoxDriver();

    String url = "http://localhost:9000/";

    @Autowired
    private TradeUserService tradeUserService;

    @Autowired
    private TradeListService tradeListService;

    @Before
    public void setUp() throws Exception {
        tradeUserService.deleteAll();
    }

    @Test
    public void testSignUpAndLoginAndLogout() throws Exception {
        driver.get(url);
        driver.findElement(By.linkText("Sign up")).click();

        driver.findElement(By.id("username")).sendKeys("username");
        driver.findElement(By.id("email")).sendKeys("test@email.com");
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("password-confirm")).sendKeys("password");

        driver.findElement(By.id("signup-submit")).click();

        assertTrue(driver.getCurrentUrl().equals(url));

        signIn("username", "password");

        driver.findElement(By.id("signout-submit")).click();
    }

    private void signIn(String username, String password) {
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.id("signin-submit")).click();
    }

    @Test
    public void testCreateNewList() throws Exception {
        driver.get(url);
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.id("signin-submit")).click();
        driver.findElement(By.linkText("Admin")).click();
        driver.findElement(By.id("create-new-list-command")).click();

        assertThat(tradeListService.findMostRecentList(), is(notNullValue()));
        assertThat(tradeListService.findMostRecentList().getState(), is(TradeListState.OPEN));
    }
}