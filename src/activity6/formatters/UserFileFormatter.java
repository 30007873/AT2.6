package activity6.formatters;
// User file formatter
public class UserFileFormatter {

    public String username;
    public String email;
    public String studentId;

    public UserFileFormatter(String username, String email, String studentId) {
        this.username = username;
        this.email = email;
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "UserFileFormatter{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", studentId=" + studentId +
                '}';
    }
}
