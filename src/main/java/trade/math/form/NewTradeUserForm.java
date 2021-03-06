package trade.math.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by karol on 18.02.16.
 */
@PasswordMatchConfirmation
public class NewTradeUserForm {

    @NotEmpty
    @Size(min = 3, max = 32)
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,32}$")
    private String username;

    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 128)
    private String password;

    @NotNull
    @NotEmpty
    private String passwordConfirmation;

    public NewTradeUserForm() {
    }

    public NewTradeUserForm(String username, String email, String password, String passwordConfirmation) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
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

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
