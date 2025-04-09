package ui.cart;

import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
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

import static io.qameta.allure.Allure.step;
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
        step("Аутентификация", () -> {
            loginPage.open(TestConstants.Urls.BASE_URL + TestConstants.Urls.MY_ACCOUNT_URL);
            loginPage.fillOutLoginForm(LoginData.EXISTING_EMAIL, LoginData.EXISTING_PASSWORD)
                    .clickLoginButton();
        });
        step("Переходим в корзину", mainPage::clickLinkToCart);
        step("Очищаем корзину и переходим на страницу меню", () ->
                cartPage.clearCart().clickMenuPageButton()
        );
        step("Добавление продукта в корзину и переход в неё", () ->
                menuPage.addToCartFirstProduct().clickLinkToCart()
        );
        step("Удаления имеющихся купонов", () -> {
            cartPage.loadingProductTable();
            cartPage.tryRemoveCoupons();
        });
    }

    @AfterEach
    public void tearDown() {
        step("Очистка корзины", () -> {
            cartPage.open(TestConstants.Urls.BASE_URL + TestConstants.Urls.CART_URL);
            cartPage.clearCart();
        });
    }

    @Test
    @DisplayName("Увеличение и уменьшение количества товара")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Yahor Tserashkevich")
    @Link(name = "Website", url = "https://pizzeria.skillbox.cc/cart/")
    public void changeProductQuantity() {
        step("", () -> {
            Integer beforeQuantity = cartPage.getFirstProductQuantity();
            cartPage.setFirstProductQuantity(beforeQuantity + 1);
            Integer afterQuantity = cartPage.getFirstProductQuantity();

            assertEquals(beforeQuantity + 1, afterQuantity, FailMessages.NUMBER_NOT_MATCH_EXPECTED);
        });
    }

    @Test
    @DisplayName("Обновление корзины после изменения содержимого")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Yahor Tserashkevich")
    @Link(name = "Website", url = "https://pizzeria.skillbox.cc/cart/")
    public void updateAfterChanges() {
        step("Обновления количество продуктов в корзине", () -> {
            Integer beforeTotalCartAmount = cartPage.getFirstProductQuantity();
            cartPage.setFirstProductQuantity(cartPage.getFirstProductQuantity() + 1)
                    .clickUpdateCartButton()
                    .getTotalCartAmount();
            Integer afterTotalCartAmount = cartPage.getFirstProductQuantity();

            assertTrue(afterTotalCartAmount > beforeTotalCartAmount, FailMessages.PRICE_SHOULD_INCREASE);
        });
    }

    @Test
    @DisplayName("Переход к оплате (авторизованный пользователь)")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Yahor Tserashkevich")
    @Link(name = "Website", url = "https://pizzeria.skillbox.cc/cart/")
    public void proceedToCheckoutForLoggedInUser() {
        step("Переход к оплате заказа", () -> {
            cartPage.clickProceedToPayment();
            String message = checkoutBasePage.getPostTitle();

            assertEquals("ОФОРМЛЕНИЕ ЗАКАЗА", message, FailMessages.STRING_NOT_MATCH_EXPECTED);
        });
    }

    @Test
    @DisplayName("Применение промокода из раздела «Акции»")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yahor Tserashkevich")
    @Link(name = "Website", url = "https://pizzeria.skillbox.cc/cart/")
    public void applyPromoCodeFromSalesSection() {
        step("Получение и применение промокода", () -> {
            cartPage.clickPromoPageButton();

            String coupon = promoPage.getFirstCoupon();
            promoPage.clickLinkToCart();

            Float beforeTotalCartAmount = cartPage.getTotalCartAmount();
            cartPage
                    .setCouponCode(coupon)
                    .clickApplyCouponButton();
            Float afterTotalCartAmount = cartPage.getTotalCartAmount();

            assertTrue(afterTotalCartAmount < beforeTotalCartAmount, FailMessages.PRICE_SHOULD_DECREASE);
        });
    }

}
