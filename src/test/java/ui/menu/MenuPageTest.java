package ui.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.cart.CartPage;
import pages.menu.MenuPage;
import ui.BasePageTest;
import utils.FailMessages;
import utils.TestConstants;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MenuPageTest extends BasePageTest {
    private final MenuPage menuPage = new MenuPage(driver);
    private final CartPage cartPage = new CartPage(driver);

    @BeforeEach
    public void setUp() {
        menuPage.open(TestConstants.Urls.BASE_URL + TestConstants.Urls.MENU_URL);
    }

    /**
     * Применение сортировки пицц
     */
    @Test
    public void sorting() {
        menuPage.sortMenuByAscPrice();

        List<Float> menuCardsPrices = menuPage.getMenuCardsPrices();
        List<Float> sortedCardsPrices = menuCardsPrices.stream()
                .sorted()
                .toList();

        assertEquals(sortedCardsPrices, menuCardsPrices, FailMessages.MENU_NOT_SORTED_BY_PRICE_ASC);
    }

    /**
     * Фильтрация пицц по цене
     */
    @Test
    public void priceFiltering() {
        Integer expectedMinPrice = 300;
        Integer expectedMaxPrice = 480;

        List<Float> menuCardsPrices = menuPage
                .changeMinPrice(expectedMinPrice)
                .changeMaxPrice(expectedMaxPrice)
                .clickPriceFilteringButton()
                .getMenuCardsPrices();

        assertAll(
                () -> assertTrue(menuCardsPrices.stream()
                        .allMatch(price -> price >= expectedMinPrice), FailMessages.MENU_NOT_FILTERED_BY_MIN_PRICE),
                () -> assertTrue(menuCardsPrices.stream()
                        .allMatch(price -> price <= expectedMaxPrice), FailMessages.MENU_NOT_FILTERED_BY_MAX_PRICE)
        );

    }

    /**
     * Добавление пиццы в корзину
     */
    @Test
    public void additionToCart() {
        menuPage.addToCartFirstProduct()
                .clickLinkToCart();
        Boolean cartExist = cartPage.loadingProductTable();

        assertTrue(cartExist, FailMessages.ELEMENT_NOT_EXIST);
    }

}
