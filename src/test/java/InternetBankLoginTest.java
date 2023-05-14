import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

public class InternetBankLoginTest {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static UserInfo userInfoActive;
    private static UserInfo userInfoBlocked;

    @BeforeAll
    static void setUpAll() {
        userInfoActive = DataGenerate.Registration.generateUserActive("ru");
        userInfoBlocked = DataGenerate.Registration.generateUserBlocked("ru");

        given()
                .spec(requestSpec)
                .body(userInfoActive) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
        given()
                .spec(requestSpec)
                .body(userInfoBlocked) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK

    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldLogIn() {
        Configuration.holdBrowserOpen = true;
        LoginPage loginPage = new LoginPage();
        loginPage.login(userInfoActive);
        $("h2").shouldBe(Condition.visible).shouldHave(Condition.text("Личный кабинет"));

    }

    @Test
    public void shouldNotLogInIfUserIsNotRegistered() {
        Configuration.holdBrowserOpen = true;
        LoginPage loginPage = new LoginPage();
        UserInfo info = DataGenerate.Registration.generateUserActive("ru");
        loginPage.login(info);
        $("[data-test-id='error-notification']").should(Condition.appear)
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }
    @Test
    public void shouldNotLogInIfUserBlocked() {
        Configuration.holdBrowserOpen = true;
        LoginPage loginPage = new LoginPage();
        loginPage.login(userInfoBlocked);
        $("[data-test-id='error-notification']").should(Condition.appear)
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"));
    }
    @Test
    public void shouldNotLogInIfWrongLogin() {
        Faker faker = new Faker();
        LoginPage loginPage = new LoginPage();

        String login = faker.name().username();
        loginPage.wrongLogin(userInfoActive, login );
    }
    @Test
    public void shouldNotLogInIfWrongPassword() {
        Faker faker = new Faker();
        LoginPage loginPage = new LoginPage();

        String password = faker.internet().password();
        loginPage.wrongPassword(userInfoActive, password);
    }

    }
