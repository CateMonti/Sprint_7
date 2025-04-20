package samokat.courier;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CourierAccount;
import samokat.steps.Steps;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateNewCourierWithoutRequiredFieldTest {
    private final Faker faker = new Faker(new Locale("en"));
    private final Steps steps = new Steps();
    private CourierAccount account;
    private List<CourierAccount> testData;

    @Before
    public void setUp() {
        testData = new ArrayList<>();
//        account = new CourierAccount(
//                faker.funnyName().name(),
//                faker.internet().password(),
//                faker.name().firstName());
        account = new CourierAccount("gtgрtрgg", "kktррgtgr", "aррtwwd");
        testData.add(account);
    }

    @Test
    @DisplayName("Создание курьера - если одного из полей нет, запрос возвращает ошибку")
    public void createFieldlessReturnsError() {
        account = new CourierAccount();
        account.setLogin(faker.funnyName().name());
        account.setFirstName(faker.name().firstName());
        testData.add(account);
        assertThat("Пароль обязательное поле, ждем 400 код",
                steps.create(account).extract().statusCode(),
                equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    @DisplayName("Создание курьера - если одного из полей нет, запрос возвращает ошибку")
    public void createFieldlessReturnsError2() {
        account = new CourierAccount();
        account.setPassword(faker.internet().password());
        account.setFirstName(faker.name().firstName());
        testData.add(account);
        assertThat("Логин обязательное поле, ждем 400 код",
                steps.create(account).extract().statusCode(),
                equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    @DisplayName("Создание курьера - если одного из полей нет, запрос возвращает ошибку")
    @Description("У портала баг. Принимает создание пользователя без firstName")
    public void createFieldlessReturnsError3() {
        account = new CourierAccount(
                faker.funnyName().name(),
                faker.internet().password(),
                "");
        assertThat("Имя обязательное поле, ждем 400 код", steps.create(account).extract().statusCode(),
                equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @After
    public void cleanUp() {
        steps.delete(testData);
    }
}

