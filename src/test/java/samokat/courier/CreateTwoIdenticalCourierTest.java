package samokat.courier;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.CourierAccount;
import org.junit.After;
import samokat.steps.Steps;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotEquals;

public class CreateTwoIdenticalCourierTest {
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
    @DisplayName("Создание курьера - нельзя создать двух одинаковых курьеров")
    public void createIdenticalAccountsForbidden() {
        ValidatableResponse createFirst = steps.create(account);
        int firstStatusCode = createFirst.extract().statusCode();
        assertThat("Ожидаем код 201", firstStatusCode, equalTo(HttpStatus.SC_CREATED));

        ValidatableResponse createSecond = steps.create(account);
        int secondStatusCode = createSecond.extract().statusCode();
        assertNotEquals("Статус код должен быть 409", secondStatusCode, equalTo(HttpStatus.SC_CONFLICT));
    }

    @After
    public void cleanUp() {
        steps.delete(testData);
    }
}


