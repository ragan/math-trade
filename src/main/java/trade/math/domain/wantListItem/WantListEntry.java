package trade.math.domain.wantListItem;

import javax.persistence.*;

@Entity
@Table(name = "WANT_LIST_ENTRY")
public class WantListEntry {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "WANT_LIST_ID")
    private WantList wantList;

    private int priority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WantList getWantList() {
        return wantList;
    }

    public void setWantList(WantList wantList) {
        this.wantList = wantList;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
