package trade.math.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import trade.math.MtApplication;
import trade.math.form.NewTradeCategoryForm;

/**
 * Created by daniel on 08.03.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MtApplication.class)
@ActiveProfiles("test")
public class TradeCategoryServiceTest {

    @Autowired
    private TradeCategoryService tradeCategoryService;

    @Before
    public void setup(){
        tradeCategoryService.deleteAll();
    }

    @Test
    public void saveTest(){
        assertEquals(0, tradeCategoryService.findAll().size());

        NewTradeCategoryForm newTradeCategoryForm = new NewTradeCategoryForm("Test");

        tradeCategoryService.save(newTradeCategoryForm);
        
        assertEquals(1, tradeCategoryService.findAll().size());
    }

}
