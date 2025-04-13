package ui.header;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.main.MainPage;
import pages.menu.MenuPage;
import ui.BasePageTest;
import utils.FailMessages;
import utils.TestConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeaderTest extends BasePageTest {
    private final MainPage mainPage = new MainPage(driver);
    private final MenuPage menuPage = new MenuPage(driver);

    @BeforeEach
    public void setUp() {
        mainPage.open(TestConstants.Urls.BASE_URL);
    }

    /**
     * Навигационное меню: переход по всем разделам меню:
     * 1) "Меню" → «Пицца»
     * 2) «Меню» → «Десерты»
     * 3) «Меню» → «Напитки»
     */
    @ParameterizedTest
    @ValueSource(strings = {"Пицца", "Десерты", "Напитки"})
    public void menuCategories(String category) {
        mainPage.clickMenuCategoryButton(category);
        String title = menuPage.getTitle();

        assertEquals(category.toLowerCase(), title.toLowerCase(), FailMessages.STRING_NOT_MATCH_EXPECTED);
    }

}
