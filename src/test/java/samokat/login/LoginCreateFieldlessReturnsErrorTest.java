package samokat.login;

import com.github.javafaker.Faker;
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

public class LoginCreateFieldlessReturnsErrorTest {

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
    @DisplayName("Если одного из полей нет, запрос возвращает ошибку.")
    public void createFieldlessReturnsError() {
        steps.create(account);
        CourierAccount wrongAccount = new CourierAccount();
        wrongAccount.setPassword(account.getPassword());
        testData.add(wrongAccount);
        assertThat("Пароль обязательное поле, ждем 400 код", steps.login(wrongAccount).extract().statusCode(),
                equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @After
    public void cleanUp() {
        steps.delete(testData);
    }
}

