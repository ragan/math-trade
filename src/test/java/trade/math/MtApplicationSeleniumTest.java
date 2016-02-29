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
import trade.math.service.TradeUserService;

import static org.junit.Assert.assertTrue;

/**
 * Created by karol on 23.02.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port=9000")
@ActiveProfiles("test")
public class MtApplicationSeleniumTest {

    private WebDriver driver = new FirefoxDriver();

    String url = "http://localhost:9000/";

    @Autowired
    private TradeUserService tradeUserService;

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

        driver.findElement(By.name("username")).sendKeys("username");
        driver.findElement(By.name("password")).sendKeys("password");
        driver.findElement(By.id("signin-submit")).click();

        driver.findElement(By.id("signout-submit")).click();
    }
}