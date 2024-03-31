package accounts;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class UserProfile implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "login", unique = true, updatable = false)
    private String login;
    @Column(name = "pass", unique = true, updatable = false)
    private String pass;

    @Column(name = "email", unique = true, updatable = false)
    private String email;

    @SuppressWarnings("UnusedDeclaration")
    public UserProfile() {
    }
    @SuppressWarnings("UnusedDeclaration")
    public UserProfile(String login, String pass, String email) {
        this.login = login;
        this.pass = pass;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }

}
