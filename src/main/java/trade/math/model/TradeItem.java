package trade.math.model;

import org.hibernate.validator.constraints.NotEmpty;
import trade.math.domain.groupList.GroupList;
import trade.math.domain.tradeList.TradeList;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class TradeItem {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    private String title;

    @Column(nullable = false)
    private String description;

    private String imgUrl;

    @Column(nullable = false)
    private boolean forTrade;

    @ManyToOne
    @JoinColumn(nullable = false)
    private TradeUser owner;

    @ManyToOne
    @JoinColumn(name = "TRADE_LIST_ID", nullable = true)
    private TradeList tradeList;

    @NotNull
    @Column(name = "CATEGORY")
    @Enumerated(EnumType.STRING)
    private TradeItemCategory category;

    @ManyToOne
    @JoinColumn(name = "GROUP_LIST_ID")
    private GroupList groupList;

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

    public boolean isForTrade() {
        return forTrade;
    }

    public void setForTrade(boolean forTrade) {
        this.forTrade = forTrade;
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

    public GroupList getGroupList() {
        return groupList;
    }

    public void setGroupList(GroupList groupList) {
        this.groupList = groupList;
    }
}
