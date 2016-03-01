package trade.math.bgsearch.bgg;

import trade.math.bgsearch.BoardGameSearchResult;
import trade.math.bgsearch.BoardGameSearchResultItem;
import trade.math.model.TradeBoardGame;

import java.util.Collections;
import java.util.List;

public class BggGameSearchResult implements BoardGameSearchResult {

    private List<BoardGameSearchResultItem> items;

    public BggGameSearchResult() {
        this(Collections.emptyList());
    }

    public BggGameSearchResult(List<BoardGameSearchResultItem> items) {
        this.items = items;
    }

    @Override
    public List<BoardGameSearchResultItem> getItems() {
        return items;
    }

    public void setItems(List<BoardGameSearchResultItem> items) {
        this.items = items;
    }
}
