public class User {
    protected String name;
    protected String email;
    protected String password;
    protected String department;

    public User() {}

    public User(String name, String email, String password, String department) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.department = department;
    }
}
