import java.util.Objects;

public class User {

    private String fullName;
    private String phone;
    private String username;

    public User(String fullName, String phone, String username) {
        this.fullName = fullName;
        this.phone = phone;
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
