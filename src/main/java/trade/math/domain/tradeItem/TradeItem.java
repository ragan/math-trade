package trade.math.domain.tradeItem;

import org.hibernate.validator.constraints.NotEmpty;
import trade.math.domain.tradeList.TradeList;
import trade.math.model.TradeItemCategory;
import trade.math.model.TradeUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "TRADE_ITEM")
public class TradeItem {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    private String title;

    private String description;

    private String imgUrl;

    @ManyToOne
    private TradeUser owner;

    @ManyToOne
    @JoinColumn(name = "TRADE_LIST_ID", nullable = true)
    private TradeList tradeList;

    @NotNull
    @Column(name = "CATEGORY")
    @Enumerated(EnumType.STRING)
    private TradeItemCategory category;

    @Column(name = "BGG_ID")
    private int bggId;

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    private List<TradeItem> groupItems;

    @ManyToOne
    private TradeItem group;

    public TradeItem() {
        //
    }

    public TradeItem(String title, List<TradeItem> groupItems) {
        setTitle(title);
        setCategory(TradeItemCategory.GROUP_ITEM);
        setGroupItems(groupItems);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public TradeUser getOwner() {
        return owner;
    }

    public void setOwner(TradeUser owner) {
        this.owner = owner;
    }

    public TradeList getTradeList() {
        return tradeList;
    }

    public void setTradeList(TradeList tradeList) {
        this.tradeList = tradeList;
    }

    public TradeItemCategory getCategory() {
        return category;
    }

    public void setCategory(TradeItemCategory category) {
        this.category = category;
    }

    public int getBggId() {
        return bggId;
    }

    public void setBggId(int bggId) {
        this.bggId = bggId;
    }

    public List<TradeItem> getGroupItems() {
        return groupItems;
    }

    public void setGroupItems(List<TradeItem> groupItems) {
        this.groupItems = groupItems;
    }

    public TradeItem getGroup() {
        return group;
    }

    public void setGroup(TradeItem group) {
        this.group = group;
    }
}
