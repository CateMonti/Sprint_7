package samokat.courier;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
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

public class CreateNewCourierTest {
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
    @DisplayName("Создание курьера - курьера можно создать")
    public void createNewCourierReturnSC_CREATED() {
        ValidatableResponse response = steps.create(account);
        assertThat("Ждём 201",
                response.extract().statusCode(),
                equalTo(HttpStatus.SC_CREATED));
    }

    @After
    public void cleanUp() {
        steps.delete(testData);
    }
}
