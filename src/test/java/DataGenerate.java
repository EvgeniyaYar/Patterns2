import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

@UtilityClass
public class DataGenerate {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void setUpAll(UserInfo userInfo) {
        given()
                .spec(requestSpec)
                .body(userInfo) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @UtilityClass
    public static class Registration {
        public static UserInfo generateUser(String status) {
            Faker faker = new Faker();
            return new UserInfo(faker.name().username(), faker.internet().password(), status);
        }
    }
}
