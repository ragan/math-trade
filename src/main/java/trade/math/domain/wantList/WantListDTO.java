package trade.math.domain.wantList;

import trade.math.domain.tradeItem.TradeItem;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class WantListDTO {

    private Long id;

    private TradeItem tradeItem;

    private String offerTitle;

    private List<WantListEntryDTO> entries;

    public WantListDTO(WantList wantList) {
        setId(wantList.getId());
        setOfferTitle(wantList.getItem().getTitle());
        setEntries(wantList.getEntries().stream().map(WantListEntryDTO::new).collect(toList()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TradeItem getTradeItem() {
        return tradeItem;
    }

    public void setTradeItem(TradeItem tradeItem) {
        this.tradeItem = tradeItem;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public List<WantListEntryDTO> getEntries() {
        return entries;
    }

    public void setEntries(List<WantListEntryDTO> entries) {
        this.entries = entries;
    }
}
