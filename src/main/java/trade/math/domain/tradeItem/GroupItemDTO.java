package trade.math.domain.tradeItem;

import java.util.List;
import java.util.stream.Collectors;

public class GroupItemDTO {

    private String title;

    private List<String> titles;

    public GroupItemDTO(TradeItem groupItem) {
        setTitle(groupItem.getTitle());
        setTitles(groupItem.getGroupItems().stream()
                .map(gi -> gi.getOwner().getUsername() + " : " + gi.getTitle()).collect(Collectors.toList()));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }
}
