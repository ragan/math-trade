package trade.math;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {MtApplication.class, SecurityConfiguration.class})
@WebAppConfiguration
@ActiveProfiles("test")
public class MtApplicationTests {

    @Autowired
    WebApplicationContext context;

    @Autowired
    private FilterChainProxy filterChainProxy;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .dispatchOptions(true)
                .addFilter(filterChainProxy)
                .build();
    }

    @Test
    public void testIndexPage() throws Exception {
        this.mockMvc.perform(get("/")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginPage() throws Exception {
        this.mockMvc.perform(get("/login")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    public void testSignUpPage() throws Exception {
        this.mockMvc.perform(get("/login")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddItemPage() throws Exception {
        this.mockMvc.perform(get("/addItem")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isFound());
    }

}
