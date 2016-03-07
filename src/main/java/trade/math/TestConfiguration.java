package trade.math;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import trade.math.model.TradeBoardGame;
import trade.math.model.TradeBoardGameTitle;
import trade.math.repository.TradeBoardGameTitleRepository;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@Configuration
public class TestConfiguration {

    public static final String TITLE_REPO_MOCK = "title-repo-mock";

    @Profile(TestConfiguration.TITLE_REPO_MOCK)
    public TradeBoardGameTitleRepository tradeBoardGameTitleRepository() {
        TradeBoardGame boardGame = new TradeBoardGame();
        boardGame.setId(1L);
        boardGame.setBggId(123);
        TradeBoardGameTitle title = new TradeBoardGameTitle();
        boardGame.setNames(Collections.singletonList(title));
        title.setTitle("Test title");
        title.setTradeBoardGame(boardGame);

        TradeBoardGameTitleRepository mock = mock(TradeBoardGameTitleRepository.class);
        when(mock.findOneByTitleAllIgnoreCase(anyString()))
                .thenReturn(Optional.of(title));
        when(mock.findByTitleAllIgnoreCaseOrderByTitleAsc(anyString()))
                .thenReturn(Collections.singletonList(title));
        when(mock.findByTitleAllIgnoreCaseContainingOrderByTitleAsc(anyString()))
                .thenReturn(Collections.singletonList(title));
        return mock;
    }


}
