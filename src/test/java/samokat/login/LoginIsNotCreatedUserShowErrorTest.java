package samokat.login;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.CourierAccount;
import samokat.steps.Steps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class LoginIsNotCreatedUserShowErrorTest {
    private static final String ACCOUNT_ERROR = "Учетная запись не найдена";

    private final Steps steps = new Steps();
    private CourierAccount account;
    private List<CourierAccount> testData;

    @Before
    public void setUp() {
        testData = new ArrayList<>();

        String randomName = steps.generateRandomString(8);
        String randomPassword = steps.generateRandomString(8);
        String randomFirstName = steps.generateRandomString(8);

        account = new CourierAccount(
                randomName,
                randomPassword,
                randomFirstName);

        testData.add(account);
    }

    @Test
    @DisplayName("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void loginIsNotCreatedUserShowError() {
        ValidatableResponse response = steps.login(account);
        assertEquals("Авторизация под несуществующим пользователем должна вернуть ошибку",
                response.extract().body().jsonPath().getString("message"), ACCOUNT_ERROR);
    }

    @After
    public void cleanUp() {
        steps.delete(testData);
    }
}



