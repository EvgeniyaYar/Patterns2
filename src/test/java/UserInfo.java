import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class UserInfo {
    private final String login;
    private final String password;
    private final String status;
    public static final String status_active = "active";
    public static final String status_blocked = "blocked";

}
