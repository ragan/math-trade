package trade.math;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import trade.math.controller.MainController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MtApplication.class)
@ActiveProfiles("test")
public class MtApplicationTests {

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MainController()).build();
    }

    @Test
    public void testEverySiteEndPoint() throws Exception {
//        this.mockMvc.perform(get("/")
//                .accept(MediaType.TEXT_HTML))
//                .andExpect(status().isOk());

    }

    @Test
    public void testSite() throws Exception {
//        this.mockMvc.perform(get("/signUp")
//                .accept(MediaType.TEXT_HTML))
//                .andExpect(status().isOk());
    }
}
