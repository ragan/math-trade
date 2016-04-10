package trade.math.domain.groupList;

import javax.persistence.*;

@Entity
@Table(name = "ITEM_GROUP")
public class ItemGroup {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "TITLE")
    private String title;

    public ItemGroup() {
        //
    }

    public ItemGroup(String title) {
        this.title = title;
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
}
