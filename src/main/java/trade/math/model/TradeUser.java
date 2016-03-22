package trade.math.model;

import org.hibernate.validator.constraints.NotEmpty;
import trade.math.TradeUserRole;

import javax.persistence.*;
import java.util.List;

/**
 * Created by karol on 18.02.16.
 */
@Entity
public class TradeUser {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NotEmpty
    private String username;

    @Column(nullable = false, unique = true)
    @NotEmpty
    private String email;

    @Column(nullable = false)
    @NotEmpty
    private String password;

    @Enumerated(EnumType.STRING)
    private TradeUserRole role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<TradeItem> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TradeUserRole getRole() {
        return role;
    }

    public void setRole(TradeUserRole role) {
        this.role = role;
    }

    public List<TradeItem> getItems() {
        return items;
    }

    public void setItems(List<TradeItem> items) {
        this.items = items;
    }
}
