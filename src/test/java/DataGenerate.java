import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;
@UtilityClass
public class DataGenerate {
    @UtilityClass
    public static class Registration {
        public static UserInfo generateUserActive(String locale) {
            Faker faker = new Faker();
            return new UserInfo(faker.name().username(), faker.internet().password(), UserInfo.status_active);
        }
        public static UserInfo generateUserBlocked(String locale) {
            Faker faker = new Faker();
            return new UserInfo(faker.name().username(), faker.internet().password(), UserInfo.status_blocked);
        }
    }
}
