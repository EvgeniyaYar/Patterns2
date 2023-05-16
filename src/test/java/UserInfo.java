import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class UserInfo {
    private final String login;
    private final String password;
    private final String status;

}
