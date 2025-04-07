package ui.account;

import formData.CheckoutFormData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.account.*;
import pages.auth.LoginPage;
import pages.cart.CartPage;
import pages.checkout.CheckoutDataFillingPage;
import pages.main.MainPage;
import pages.menu.MenuPage;
import ui.BasePageTest;
import utils.FailMessages;
import utils.StringGenerator;
import utils.TestConstants;
import utils.data.CheckoutData;
import utils.data.LoginData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountPageTest extends BasePageTest {
    private final LoginPage loginPage = new LoginPage(driver);
    private final AccountBasePage accountBasePage = new AccountBasePage(driver);
    private final AccountEditPage accountEditPage = new AccountEditPage(driver);
    private final AccountInformationPage accountInformationPage = new AccountInformationPage(driver);
    private final AccountOrdersPage accountOrdersPage = new AccountOrdersPage(driver);
    private final AccountViewOrderPage accountViewOrderPage = new AccountViewOrderPage(driver);
    private final MainPage mainPage = new MainPage(driver);
    private final CartPage cartPage = new CartPage(driver);
    private final MenuPage menuPage = new MenuPage(driver);
    private final CheckoutDataFillingPage checkoutDataFillingPage = new CheckoutDataFillingPage(driver);


    @BeforeEach
    public void setUp() {
        loginPage.open(TestConstants.Urls.BASE_URL + TestConstants.Urls.MY_ACCOUNT_URL);
        loginPage.fillOutLoginForm(LoginData.EXISTING_EMAIL, LoginData.EXISTING_PASSWORD)
                .clickLoginButton();
    }

    /**
     * Загрузка файла
     */
    @Test
    public void changeAccountImage() {
        accountBasePage.clickEditAccountLink();
        accountEditPage.inputFile("D:\\Учёба своё\\Проекты\\UI-tests-junit\\src\\test\\resources\\images\\Profile.jpg")
                .clickSaveAccountDetailsButton();
        String message = accountInformationPage.getMessage();

        assertEquals("Account details changed successfully.", message, FailMessages.STRING_NOT_MATCH_EXPECTED);
    }

    /**
     * Создание заказа и проверка его появления в заказах на странице пользователя
     */
    @Test
    public void checkAddingOrder() {
        CheckoutFormData checkoutFormData = getCheckoutFormData();

        mainPage.clickMenuPageButton();

        menuPage.addToCartFirstProduct()
                .clickLinkToCart();

        cartPage.loadingProductTable();
        cartPage.clickProceedToPayment();

        checkoutDataFillingPage.fillOutOrderDetails(checkoutFormData)
                .clickTermsCheckbox()
                .clickPlaceOrderButton()
                .clickAccountPageButton();

        accountBasePage.clickOrdersAccountLink();
        accountOrdersPage.clickFirstOrder();
        String customerDetails = accountViewOrderPage.getCustomerDetails();

        assertTrue(customerDetails.contains(checkoutFormData.getFirstName()), FailMessages.STRING_NOT_HAS_EXPECTED);
    }

    private CheckoutFormData getCheckoutFormData() {
        return CheckoutFormData.builder()
                .firstName(StringGenerator.getUniqueString())
                .lastName(CheckoutData.LAST_NAME)
                .country(CheckoutData.COUNTRY)
                .address(CheckoutData.ADDRESS)
                .city(CheckoutData.CITY)
                .state(CheckoutData.STATE)
                .postcode(CheckoutData.POSTCODE)
                .phone(CheckoutData.PHONE)
                .build();
    }

}
