package pages.main;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;
import utils.Constants;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class MainPage extends BasePage {
    @FindBy(xpath = "//h2[@class='prod-title' and contains(text(), 'Пицца')]/ancestor::aside/ul[@class='new-prod-slide remove-overload slick-initialized slick-slider']")
    private WebElement pizzaSliderUl;

    @FindBy(xpath = "//h2[@class='prod-title' and contains(text(), 'Пицца')]/ancestor::aside/ul[@class='new-prod-slide remove-overload slick-initialized slick-slider']//div[@class='slick-track']/li")
    private List<WebElement> pizzaSliderLi;

    @FindBy(xpath = "//h2[@class='prod-title' and contains(text(), 'Пицца')]/ancestor::aside/ul[@class='new-prod-slide remove-overload slick-initialized slick-slider']//a[@class='slick-prev']")
    private WebElement prevPizzaSlick;

    @FindBy(xpath = "//h2[@class='prod-title' and contains(text(), 'Пицца')]/ancestor::aside/ul[@class='new-prod-slide remove-overload slick-initialized slick-slider']//a[@class='slick-next']")
    private WebElement nextPizzaSlick;

    @FindBy(xpath = "//h2[@class='prod-title' and contains(text(), 'Напитки')]/ancestor::aside//img[@class='attachment-shop_catalog size-shop_catalog wp-post-image']")
    private List<WebElement> drinkImages;

    @FindBy(xpath = "//h2[@class='prod-title' and contains(text(), 'Десерты')]/ancestor::aside//img[@class='attachment-shop_catalog size-shop_catalog wp-post-image']/parent::a")
    private List<WebElement> dessertPageLinks;


    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void slideLeftPizzaSlider(Integer times) {
        IntStream.range(0, times).forEach(i -> clickElement(prevPizzaSlick, Duration.ofSeconds(1)));
    }

    public void slideRightPizzaSlider(Integer times) {
        IntStream.range(0, times).forEach(i -> clickElement(nextPizzaSlick, Duration.ofSeconds(1)));
    }

    public void slidePizzaSlider(Integer times, Keys keys) {
        pizzaSliderUl.click();
        IntStream.range(0, times).forEach(i -> clickKey(keys));
    }

    public List<WebElement> getVisiblePizzaInSlider() {
        return pizzaSliderLi.stream()
                .filter(el -> Objects.equals(el.getDomAttribute("aria-hidden"), Boolean.FALSE.toString()))
                .toList();
    }

    public Long getCountDrinkWithVisibleCartButtons() {
        return drinkImages.stream()
                .filter(WebElement::isDisplayed)
                .count();
    }

    public MainPage moveToFirstDrinkImage() {
        new Actions(driver)
                .moveToElement(drinkImages.getFirst())
                .perform();
        return this;
    }

    public String getFirstDesertTitle() {
        return dessertPageLinks.getFirst().getDomAttribute("title");
    }

    public void clickFirstDesertPageLink() {
        dessertPageLinks.getFirst().click();
    }

    private void clickElement(WebElement element, Duration pause) {
        new Actions(driver)
                .click(element)
                .pause(pause)
                .perform();
    }

    private void clickKey(Keys keys) {
        new Actions(driver)
                .sendKeys(keys)
                .pause(Constants.DEFAULT_EXPLICIT_WAITING)
                .perform();
    }

}
