package trade.math.model.dto;

import org.junit.Test;
import trade.math.model.TradeBoardGame;
import trade.math.model.TradeBoardGameTitle;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TradeBoardGameDTOTest {

    @Test
    public void testFromModelToDTO() throws Exception {
        TradeBoardGameTitle tradeBoardGameTitle = new TradeBoardGameTitle();
        tradeBoardGameTitle.setTitle("test title");

        TradeBoardGameDTO dto = new TradeBoardGameDTO(
                new TradeBoardGame(123, Arrays.asList(tradeBoardGameTitle), "imageUrl", "thumbnailUrl", 1, 2, 1992,
                        "test designer")
        );

        assertTrue(dto.getBggId() == 123);
        assertTrue(dto.getNames().size() == 1);
        assertTrue(dto.getNames().get(0).equals("test title"));
        assertTrue(dto.getImageUrl().equals("imageUrl"));
        assertTrue(dto.getThumbnailUrl().equals("thumbnailUrl"));
        assertTrue(dto.getMinPlayers() == 1);
        assertTrue(dto.getMaxPlayers() == 2);
        assertTrue(dto.getYearPublished() == 1992);
        assertTrue(dto.getDesigner().equals("test designer"));
    }
}