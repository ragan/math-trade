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
import trade.math.form.NewTradeUserForm;
import trade.math.domain.tradeItem.TradeItemService;
import trade.math.domain.tradeList.TradeListService;
import trade.math.service.TradeUserService;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
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
public class MtListOperationsSeleniumTest {

    private WebDriver driver = new FirefoxDriver();

    String url = "http://localhost:9000/";

    @Autowired
    private TradeUserService tradeUserService;

    @Autowired
    private TradeListService tradeListService;

    @Autowired
    private TradeItemService tradeItemService;

    @Before
    public void setUp() throws Exception {
        tradeUserService.deleteAll();

        tradeUserService.save(new NewTradeUserForm("user", "email@user.com", "password", "password"));
    }

    @Test
    public void testCreateItemNoCategory() throws Exception {
        driver.get(url);
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("user");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("password");
        driver.findElement(By.id("signin-submit")).click();
        driver.findElement(By.id("add-item-href")).click();
        driver.findElement(By.id("game-form-title-text")).clear();
        driver.findElement(By.id("game-form-title-text")).sendKeys("Some test item");
        driver.findElement(By.id("description")).clear();
        driver.findElement(By.id("description")).sendKeys("With description");
        driver.findElement(By.cssSelector("input.btn.btn-default")).click();

        assertThat(tradeItemService.findAll(), hasSize(1));
    }
}