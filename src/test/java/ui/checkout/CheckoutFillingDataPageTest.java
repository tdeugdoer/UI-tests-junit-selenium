package ui.checkout;

import formData.CheckoutFormData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.auth.LoginPage;
import pages.cart.CartPage;
import pages.checkout.CheckoutDataFillingPage;
import pages.checkout.CheckoutOrderInformationPage;
import pages.main.MainPage;
import pages.menu.MenuPage;
import ui.BasePageTest;
import utils.DateUtils;
import utils.FailMessages;
import utils.TestConstants;
import utils.data.CheckoutData;
import utils.data.LoginData;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutFillingDataPageTest extends BasePageTest {
    private final LoginPage loginPage = new LoginPage(driver);
    private final MainPage mainPage = new MainPage(driver);
    private final MenuPage menuPage = new MenuPage(driver);
    private final CartPage cartPage = new CartPage(driver);
    private final CheckoutDataFillingPage checkoutDataFillingPage = new CheckoutDataFillingPage(driver);
    private final CheckoutOrderInformationPage checkoutOrderInformationPage = new CheckoutOrderInformationPage(driver);

    private final CheckoutFormData checkoutFormData = getCheckoutFormData();

    @BeforeEach
    public void setUp() {
        loginPage.open(TestConstants.Urls.BASE_URL + TestConstants.Urls.MY_ACCOUNT_URL);
        loginPage.fillOutLoginForm(LoginData.EXISTING_EMAIL, LoginData.EXISTING_PASSWORD)
                .clickLoginButton();

        mainPage.clickLinkToCart();
        cartPage.clearCart()
                .clickMenuPageButton();

        menuPage.addToCartFirstProduct()
                .clickLinkToCart();

        cartPage.loadingProductTable();
        cartPage.clickProceedToPayment();
    }

    @Test
    @DisplayName("Установка даты заказа")
    public void orderTime() {
        String tomorrowString = DateUtils.getTomorrowDateString();

        checkoutDataFillingPage
                .fillOutOrderDetails(checkoutFormData)
                .enterOrderDate(tomorrowString)
                .clickTermsCheckbox()
                .clickPlaceOrderButton();

        String title = checkoutOrderInformationPage
                .tryGetExpectedPostTitle("ЗАКАЗ ПОЛУЧЕН", TestConstants.DEFAULT_EXPLICIT_DURATION);

        assertEquals("ЗАКАЗ ПОЛУЧЕН", title, FailMessages.STRING_NOT_MATCH_EXPECTED);
    }

    @Test
    @DisplayName("Успешное оформление заказа с оплатой наличными")
    public void paymentInCash() {
        checkoutDataFillingPage
                .fillOutOrderDetails(checkoutFormData)
                .selectPaymentInCashOnDeliveryRadioButton()
                .clickTermsCheckbox()
                .clickPlaceOrderButton();

        String title = checkoutOrderInformationPage
                .tryGetExpectedPostTitle("ЗАКАЗ ПОЛУЧЕН", TestConstants.DEFAULT_EXPLICIT_DURATION);
        String paymentMethod = checkoutOrderInformationPage.getPaymentMethod();

        assertAll(
                () -> assertEquals("ЗАКАЗ ПОЛУЧЕН", title, FailMessages.STRING_NOT_MATCH_EXPECTED),
                () -> assertEquals("Оплата при доставке", paymentMethod, FailMessages.STRING_NOT_MATCH_EXPECTED)
        );
    }

    private CheckoutFormData getCheckoutFormData() {
        return CheckoutFormData.builder()
                .firstName(CheckoutData.FIRST_NAME)
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
