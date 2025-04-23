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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginSuccessReturnIdTest {

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
    @DisplayName("Успешный запрос возвращает id")
    public void loginSuccessReturnId() {
        steps.create(account);
        ValidatableResponse response = steps.login(account);
        assertThat("Успешный запрос возвращает \"id\": int", response.extract().body().jsonPath().
                getInt("id"), notNullValue());
    }

    @After
    public void cleanUp() {
        steps.delete(testData);
    }
}



