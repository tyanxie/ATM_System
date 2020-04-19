package top.atm.bean;

/**
 * 管理员 JavaBean
 *
 * @author taifu
 */

@SuppressWarnings ("unused")
public class Admin {
    private Integer id;
    private Integer bankId;
    private String name;
    private String username;
    private String password;

    public Admin() {}

    public Admin(Integer id, Integer bankId, String name, String username, String password) {
        this.id = id;
        this.bankId = bankId;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
            "id=" + id +
            ", bankId=" + bankId +
            ", name='" + name + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            '}';
    }
}
