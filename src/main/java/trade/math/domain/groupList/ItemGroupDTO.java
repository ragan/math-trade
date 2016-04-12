package trade.math.domain.groupList;

public class ItemGroupDTO {

    private Long id;

    private String title;

    public ItemGroupDTO() {
        //
    }

    public ItemGroupDTO(String title) {
        this.title = title;
    }

    public ItemGroupDTO(ItemGroup itemGroup) {
        this.id = itemGroup.getId();
        this.title = itemGroup.getTitle();
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
