package pages;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Constants;

import java.util.List;

public abstract class BasePage {
    protected final WebDriver driver;

    @FindBy(xpath = "//a[@class='logout']")
    private WebElement logoutButton;

    @FindBy(xpath = "//li[contains(@class, 'menu-item menu-item-type-taxonomy menu-item-object-product_cat menu-item-has-children')]/a")
    private WebElement menuPageButton;

    @FindBy(xpath = "//ul[@class='sub-menu']//a")
    private List<WebElement> menuCategoryButtons;

    @FindBy(xpath = "//ul[@id='menu-primary-menu']//a[text()='Мой аккаунт']")
    private WebElement accountPageButton;

    @FindBy(xpath = "//ul[@id='menu-primary-menu']//a[text()='Акции']")
    private WebElement promoPageButton;

    @FindBy(xpath = "//a[@class='cart-contents wcmenucart-contents']")
    private WebElement linkToCart;

    @FindBy(id = "ak-top")
    private WebElement topScrollArrow;

    @FindBy(xpath = "//div[@class='banner-text wow fadeInLeft']//a[text()='Instagram']")
    private WebElement instagramButton;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Constants.DEFAULT_EXPLICIT_WAITING);
        PageFactory.initElements(driver, this);
    }

    protected Integer parseIntPriceValue(String priceValue) {
        return Integer.parseInt(priceValue.replace("₽", ""));
    }

    protected Float parseFloatPriceValue(String priceValue) {
        return Float.parseFloat(priceValue
                .replace(",", ".")
                .replace("₽", ""));
    }

    public void open(String url) {
        driver.get(url);
    }

    public void clickLogoutButton() {
        logoutButton.click();
    }

    public void clickMenuPageButton() {
        menuPageButton.click();
    }

    public void clickAccountPageButton() {
        accountPageButton.click();
    }

    public void clickPromoPageButton() {
        promoPageButton.click();
    }

    public void clickMenuCategoryButton(String category) {
        new Actions(driver)
                .moveToElement(menuPageButton)
                .pause(500)
                .perform();
        menuCategoryButtons.stream()
                .filter(button -> button.getText().equalsIgnoreCase(category))
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public void clickLinkToCart() {
        new WebDriverWait(driver, Constants.DEFAULT_EXPLICIT_WAITING)
                .ignoring(StaleElementReferenceException.class)
                .until(d -> {
                    linkToCart.click();
                    return true;
                });
    }

    public Boolean isTopScrollArrowVisible() {
        return new WebDriverWait(driver, Constants.DEFAULT_EXPLICIT_WAITING)
                .until(driver -> {
                    scrollPage(100);
                    return topScrollArrow.isDisplayed();
                });
    }

    public void scrollPage(Integer shift) {
        new Actions(driver)
                .scrollByAmount(0, shift)
                .perform();
    }

    public void clickInstagramButton() {
        instagramButton.click();
    }

}
