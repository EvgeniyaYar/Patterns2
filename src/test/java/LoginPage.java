import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    public void login(UserInfo info) {
        $("[data-test-id='login'] input").setValue(info.getLogin());
        $("[data-test-id='password'] input").setValue(info.getPassword());
        $("[data-test-id='action-login']").click();
    }
    public void wrongLogin(UserInfo info, String login) {
        $("[data-test-id='login'] input").setValue(login);
        $("[data-test-id='password'] input").setValue(info.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").should(Condition.appear)
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));

    }
    public void wrongPassword(UserInfo info, String password) {
        $("[data-test-id='login'] input").setValue(info.getLogin());
        $("[data-test-id='password'] input").setValue(password);
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").should(Condition.appear)
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));

    }


}
