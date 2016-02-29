package trade.math.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import trade.math.MtApplication;
import trade.math.model.dto.TradeBoardGameDTO;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by karol on 26.02.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MtApplication.class)
@ActiveProfiles("test")
public class TradeBoardGameServiceTest {

    @Autowired
    private TradeBoardGameService tradeBoardGameService;

    @Before
    public void setUp() throws Exception {
        tradeBoardGameService.deleteAll();
        assertTrue(tradeBoardGameService.findAll().isEmpty());
    }

    @Test
    public void testPersistTradeBoardGame() throws Exception {
        tradeBoardGameService.save(makeBoardGame("test title"));
        assertTrue(tradeBoardGameService.findAll().size() == 1);
    }

    @Test
    public void testPersistListOfTradeBoardGames() throws Exception {
        tradeBoardGameService.save(Arrays.asList(makeBoardGame("some title 1"), makeBoardGame("some title 1")));
        assertTrue(tradeBoardGameService.findAll().size() == 2);
    }

    @Test
    public void testCanNotSearchEmptyOrTooShortString() throws Exception {
        tradeBoardGameService.save(makeBoardGame("test title"));
        assertTrue(tradeBoardGameService.searchByName("").isEmpty());
        assertTrue(tradeBoardGameService.searchByName("t").isEmpty());
        assertTrue(tradeBoardGameService.searchByName("te").isEmpty());
    }

    @Test
    public void testSearchByTitle() throws Exception {
        tradeBoardGameService.save(makeBoardGame("some title"));

        assertTrue(tradeBoardGameService.searchByName("some").size() == 1);
    }

    @Test
    public void testSearchByRepeatingTitle() throws Exception {
        tradeBoardGameService.save(makeBoardGame("some title", "some other title"));
        tradeBoardGameService.save(makeBoardGame("test title"));

        assertTrue(tradeBoardGameService.searchByName("some").size() == 2);
    }

    @Test
    public void testSearchTitlesOnly() throws Exception {
        tradeBoardGameService.save(makeBoardGame("some title", "some other title"));

        assertTrue(tradeBoardGameService.searchTitlesLike("some").size() == 2);
    }

    private TradeBoardGameDTO makeBoardGame(String title) {
        return makeBoardGame(Arrays.asList(title));
    }

    private TradeBoardGameDTO makeBoardGame(String... titles) {
        return makeBoardGame(Arrays.asList(titles));
    }

    private TradeBoardGameDTO makeBoardGame(List<String> titles) {
        return new TradeBoardGameDTO(123, titles, "imageUrl", "thumbnailUrl", 1, 2, 1992, "test designer");
    }
}