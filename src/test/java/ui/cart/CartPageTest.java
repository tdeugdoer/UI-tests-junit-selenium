package ui.cart;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.auth.LoginPage;
import pages.cart.CartPage;
import pages.checkout.CheckoutBasePage;
import pages.main.MainPage;
import pages.menu.MenuPage;
import pages.promo.PromoPage;
import ui.BasePageTest;
import utils.FailMessages;
import utils.TestConstants;
import utils.data.LoginData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartPageTest extends BasePageTest {
    private final LoginPage loginPage = new LoginPage(driver);
    private final MainPage mainPage = new MainPage(driver);
    private final CartPage cartPage = new CartPage(driver);
    private final MenuPage menuPage = new MenuPage(driver);
    private final CheckoutBasePage checkoutBasePage = new CheckoutBasePage(driver);
    private final PromoPage promoPage = new PromoPage(driver);

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
        cartPage.tryRemoveCoupons();
    }

    @AfterEach
    public void tearDown() {
        cartPage.open(TestConstants.Urls.BASE_URL + TestConstants.Urls.CART_URL);
        cartPage.clearCart();
    }

    @Test
    @DisplayName("Увеличение и уменьшение количества товара")
    public void changeProductQuantity() {
        Integer beforeQuantity = cartPage.getFirstProductQuantity();
        cartPage.setFirstProductQuantity(beforeQuantity + 1);
        Integer afterQuantity = cartPage.getFirstProductQuantity();

        assertEquals(beforeQuantity + 1, afterQuantity, FailMessages.NUMBER_NOT_MATCH_EXPECTED);
    }

    @Test
    @DisplayName("Обновление корзины после изменения содержимого")
    public void updateAfterChanges() {
        Integer beforeTotalCartAmount = cartPage.getFirstProductQuantity();
        cartPage.setFirstProductQuantity(cartPage.getFirstProductQuantity() + 1)
                .clickUpdateCartButton()
                .getTotalCartAmount();
        Integer afterTotalCartAmount = cartPage.getFirstProductQuantity();

        assertTrue(afterTotalCartAmount > beforeTotalCartAmount, FailMessages.PRICE_SHOULD_INCREASE);
    }

    @Test
    @DisplayName("Переход к оплате (авторизованный пользователь)")
    public void proceedToCheckoutForLoggedInUser() {
        cartPage.clickProceedToPayment();
        String message = checkoutBasePage.getPostTitle();

        assertEquals("ОФОРМЛЕНИЕ ЗАКАЗА", message, FailMessages.STRING_NOT_MATCH_EXPECTED);
    }

    @Test
    @DisplayName("Применение промокода из раздела «Акции»")
    public void applyPromoCodeFromSalesSection() {
        cartPage.clickPromoPageButton();
        String coupon = promoPage.getFirstCoupon();
        promoPage.clickLinkToCart();
        Float beforeTotalCartAmount = cartPage.getTotalCartAmount();
        cartPage
                .setCouponCode(coupon)
                .clickApplyCouponButton();
        Float afterTotalCartAmount = cartPage.getTotalCartAmount();

        assertTrue(afterTotalCartAmount < beforeTotalCartAmount, FailMessages.PRICE_SHOULD_DECREASE);
    }

}
