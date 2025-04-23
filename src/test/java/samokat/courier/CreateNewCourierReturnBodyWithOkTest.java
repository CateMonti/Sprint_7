package samokat.courier;

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

public class CreateNewCourierReturnBodyWithOkTest {
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
    @DisplayName("Создание курьера - успешный запрос возвращает ok: true")
    public void createNewCourierReturnBodyWithOk() {
        ValidatableResponse response = steps.create(account);
        boolean expected = true;
        boolean actual = response.extract().body().jsonPath().getBoolean("ok");
        assertEquals("Успешный запрос возвращает ok: true", expected, actual);
    }

    @After
    public void cleanUp() {
        steps.delete(testData);
    }
}


