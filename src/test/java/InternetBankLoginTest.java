import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.open;

public class InternetBankLoginTest {
    private static UserInfo userInfoActive;
    private static UserInfo userInfoBlocked;
    LoginPage loginPage = new LoginPage();


    @BeforeAll
    static void setUpAll() {
        userInfoActive = DataGenerate.Registration.generateUser("active");
        userInfoBlocked = DataGenerate.Registration.generateUser("blocked");
        DataGenerate.setUpAll(userInfoActive);
        DataGenerate.setUpAll(userInfoBlocked);
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldLogIn() {
        loginPage.registered(userInfoActive);
    }
    @Test
    public void shouldNotLogInIfUserIsNotRegistered() {
        loginPage.notRegistered(DataGenerate.Registration.generateUser("active"));
    }

    @Test
    public void shouldNotLogInIfUserBlocked() {
        loginPage.blocked(userInfoBlocked);
    }

    @Test
    public void shouldNotLogInIfWrongLogin() {
        String login = DataGenerate.Registration.generateUser("active").getLogin();
        loginPage.wrongLogin(userInfoActive, login);
    }

    @Test
    public void shouldNotLogInIfWrongPassword() {
        String password = DataGenerate.Registration.generateUser("active").getPassword();
        loginPage.wrongPassword(userInfoActive, password);
    }
}

