import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    public void userInfo(UserInfo info, String login, String password) {
        $("[data-test-id='login'] input").setValue(login);
        $("[data-test-id='password'] input").setValue(password);
        $("[data-test-id='action-login']").click();

    }

    public void wrongLogin(UserInfo info, String login) {
        userInfo(info, login, info.getPassword());
        $("[data-test-id='error-notification']").should(Condition.appear)
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));

    }

    public void wrongPassword(UserInfo info, String password) {
        userInfo(info, info.getLogin(), password);
        $("[data-test-id='error-notification']").should(Condition.appear)
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));

    }

    public void registered(UserInfo info) {
        userInfo(info, info.getLogin(), info.getPassword());
        $("h2").shouldBe(Condition.visible).shouldHave(Condition.text("Личный кабинет"));
    }

    public void notRegistered(UserInfo info) {
        userInfo(info, info.getLogin(), info.getPassword());
        $("[data-test-id='error-notification']").should(Condition.appear)
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    public void blocked(UserInfo info) {
        userInfo(info, info.getLogin(), info.getPassword());
        $("[data-test-id='error-notification']").should(Condition.appear)
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"));
    }
}
