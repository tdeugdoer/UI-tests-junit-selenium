package ui.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.auth.RegisterPage;
import ui.BasePageTest;
import utils.FailMessages;
import utils.StringGenerator;
import utils.TestConstants;
import utils.data.RegisterData;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterPageTest extends BasePageTest {
    private final RegisterPage registerPage = new RegisterPage(driver);
    private String username;
    private String email;
    private String password;

    @BeforeEach
    public void setUp() {
        username = StringGenerator.getUniqueString();
        email = StringGenerator.getUniqueString() + TestConstants.EMAIL_SUFFIX;
        password = StringGenerator.getUniqueString();

        registerPage.open(TestConstants.Urls.BASE_URL + TestConstants.Urls.REGISTER_URL);
    }

    @Test
    @DisplayName("Пользователь успешно регистрируется при вводе валидных значений")
    public void successRegister() {
        String message = registerPage
                .fillOutRegisterForm(username, email, password)
                .clickRegisterButton()
                .getSuccessRegisterMessage();

        assertEquals("Регистрация завершена", message, FailMessages.STRING_NOT_MATCH_EXPECTED);
    }

    @Test
    @DisplayName("Пользователь пытается создать уже существующий аккаунт и получает ошибку")
    public void repeatRegister() {
        registerPage
                .fillOutRegisterForm(username, email, password)
                .clickRegisterButton()
                .clickLogoutButton();

        registerPage.open(TestConstants.Urls.BASE_URL + TestConstants.Urls.REGISTER_URL);
        String message = registerPage
                .fillOutRegisterForm(username, email, password)
                .clickRegisterButton()
                .getErrorMessage();

        assertEquals("Error: Учетная запись с такой почтой уже зарегистировавана. Пожалуйста авторизуйтесь.", message, FailMessages.STRING_NOT_MATCH_EXPECTED);
    }

    @Test
    @DisplayName("Пользователь пытается зарегистрироваться, не вводя пароль и получает ошибку")
    public void missingPasswordRegister() {
        String password = RegisterData.EMPTY_PASSWORD;

        String message = registerPage
                .fillOutRegisterForm(username, email, password)
                .clickRegisterButton()
                .getErrorMessage();

        assertEquals("Error: Введите пароль для регистрации.", message, FailMessages.STRING_NOT_MATCH_EXPECTED);
    }

}
