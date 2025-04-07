package ui.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.main.MainPage;
import ui.BasePageTest;
import utils.Constants;
import utils.ExceptionMessages;
import utils.FailMessages;
import utils.TestConstants;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class MainPageTest extends BasePageTest {
    private final MainPage mainPage = new MainPage(driver);

    @BeforeEach
    public void setUp() {
        mainPage.open(TestConstants.Urls.BASE_URL);
    }

    /**
     * Переключение пиццы в слайдере нажатием на клавиатуре стрелки влево и вправо;
     */
    @Test
    public void slidePizzaSliderByKeys() {
        List<WebElement> initialVisiblePizzaList = mainPage.getVisiblePizzaInSlider();

        mainPage.slidePizzaSlider(4, Keys.LEFT);
        List<WebElement> afterLeftSlideVisiblePizzaList = mainPage.getVisiblePizzaInSlider();

        mainPage.slidePizzaSlider(3, Keys.RIGHT);
        List<WebElement> afterRightSlideVisiblePizzaList = mainPage.getVisiblePizzaInSlider();

        assertAll(
                () -> assertNotEquals(afterLeftSlideVisiblePizzaList, initialVisiblePizzaList, FailMessages.SLIDE_NOT_CHANGE_ELEMENTS),
                () -> assertNotEquals(afterRightSlideVisiblePizzaList, initialVisiblePizzaList, FailMessages.SLIDE_NOT_CHANGE_ELEMENTS)
        );
    }

    /**
     * Переключение пиццы в слайдере нажатием стрелки влево и вправо;
     */
    @Test
    public void slidePizzaSliderByButtons() {
        List<WebElement> initialVisiblePizzaList = mainPage.getVisiblePizzaInSlider();

        mainPage.slideLeftPizzaSlider(4);
        List<WebElement> afterLeftSlideVisiblePizzaList = mainPage.getVisiblePizzaInSlider();

        mainPage.slideRightPizzaSlider(3);
        List<WebElement> afterRightSlideVisiblePizzaList = mainPage.getVisiblePizzaInSlider();

        assertAll(
                () -> assertNotEquals(afterLeftSlideVisiblePizzaList, initialVisiblePizzaList, FailMessages.SLIDE_NOT_CHANGE_ELEMENTS),
                () -> assertNotEquals(afterRightSlideVisiblePizzaList, initialVisiblePizzaList, FailMessages.SLIDE_NOT_CHANGE_ELEMENTS)
        );
    }

    /**
     * Проверка отображения ссылки "В корзину" при наведении на картинку напитка.
     */
    @Test
    public void addToCartLinkAppearsOnHover() {
        Long beforeCountVisibleDrinkToCartButtons = mainPage.getCountDrinkWithVisibleCartButtons();
        mainPage.moveToFirstDrinkImage();
        Long afterCountVisibleDrinkToCartButtons = mainPage.getCountDrinkWithVisibleCartButtons();

        assertAll(
                () -> assertEquals(0, beforeCountVisibleDrinkToCartButtons, FailMessages.ELEMENT_COUNT_NOT_MATCH_EXPECTED),
                () -> assertEquals(1, afterCountVisibleDrinkToCartButtons, FailMessages.ELEMENT_COUNT_NOT_MATCH_EXPECTED)
        );
    }

    /**
     * Проверка перехода на страницу десерта при клике по его картинке.
     */
    @Test
    public void clickOnDessertImageRedirectsToDessertPage() {
        String firstDesertName = mainPage.getFirstDesertTitle();
        mainPage.clickFirstDesertPageLink();
        String currentPageTitle = driver.getTitle();

        assertTrue(Objects.requireNonNull(currentPageTitle, FailMessages.STRING_SHOULD_NOT_BE_NULL).contains(firstDesertName), FailMessages.STRING_NOT_HAS_EXPECTED);
    }

    /**
     * Проверка отображения стрелки "Наверх" при скроллинге вниз страницы.
     */
    @Test
    public void scrollToTopArrowAppearsWhenScrolledDown() {
        Boolean isScrollArrowVisible = mainPage.isTopScrollArrowVisible();
        assertTrue(isScrollArrowVisible, FailMessages.ELEMENT_NOT_VISIBLE);
    }

    /**
     * Проверка открытия ссылок на соцсети в новой вкладке.
     */
    @Test
    public void socialMediaLinksOpenInNewTab() {
        Integer beforeWindowCount = getWindowCount();
        String originalWindow = driver.getWindowHandle();

        mainPage.clickInstagramButton();

        new WebDriverWait(driver, Constants.DEFAULT_EXPLICIT_WAITING)
                .until(d -> driver.getWindowHandles().size() > 1);
        Integer afterWindowCount = getWindowCount();
        switchToNewTab(originalWindow);

        assertAll(
                () -> assertEquals(1, beforeWindowCount, FailMessages.WINDOW_COUNT_NOT_MATCH_EXPECTED),
                () -> assertEquals(2, afterWindowCount, FailMessages.WINDOW_COUNT_NOT_MATCH_EXPECTED),
                () -> assertTrue(Objects.requireNonNull(driver.getCurrentUrl(), FailMessages.STRING_SHOULD_NOT_BE_NULL)
                        .contains("www.instagram.com"), FailMessages.NEW_WINDOW_NOT_MATCH_EXPECTED)
        );
    }

    private Integer getWindowCount() {
        return driver.getWindowHandles().size();
    }

    private void switchToNewTab(String originalWindow) {
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
            }
        }
        throw new IllegalStateException(ExceptionMessages.NEW_WINDOW_NOT_FOUND);
    }

}
