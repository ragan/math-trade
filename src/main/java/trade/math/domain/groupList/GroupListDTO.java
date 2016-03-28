package trade.math.domain.groupList;

public class GroupListDTO {

    private Long id;

    private String title;

    public GroupListDTO() {
        //
    }

    public GroupListDTO(GroupList groupList) {
        this.id = groupList.getId();
        this.title = groupList.getTitle();
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
