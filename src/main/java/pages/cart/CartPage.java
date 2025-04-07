package pages.cart;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;
import utils.Constants;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class CartPage extends BasePage {
    @FindBy(xpath = "//table[@class='shop_table shop_table_responsive cart woocommerce-cart-form__contents']")
    private WebElement productTable;

    @FindBy(xpath = "//a[@class='remove']")
    private List<WebElement> removeButtons;

    @FindBy(xpath = "//div[@class='quantity']//input")
    private List<WebElement> quantityInputs;

    @FindBy(xpath = "//button[@name='update_cart']")
    private WebElement updateCartButton;

    @FindBy(xpath = "//tr[@class='order-total']//span[@class='woocommerce-Price-amount amount']")
    private WebElement totalCartAmount;

    @FindBy(xpath = "//a[@class='checkout-button button alt wc-forward']")
    private WebElement proceedToPaymentButton;

    @FindBy(id = "coupon_code")
    private WebElement couponCodeInput;


    @FindBy(name = "apply_coupon")
    private WebElement applyCouponButton;

    @FindBy(xpath = "//a[@class='woocommerce-remove-coupon']")
    private List<WebElement> removeCouponButtons;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public Integer getFirstProductQuantity() {
        new Actions(driver).pause(Duration.ofSeconds(1)).perform();
        return Integer.valueOf(Objects.requireNonNull(quantityInputs.getFirst().getDomProperty("value")));
    }

    public CartPage setFirstProductQuantity(Integer quantity) {
        quantityInputs.getFirst().clear();
        quantityInputs.getFirst().sendKeys(String.valueOf(quantity));
        return this;
    }

    public Float getTotalCartAmount() {
        new Actions(driver).pause(Duration.ofSeconds(1)).perform();
        return parseFloatPriceValue(totalCartAmount.getText());
    }

    public CartPage setCouponCode(String couponCode) {
        couponCodeInput.clear();
        couponCodeInput.sendKeys(couponCode);
        couponCodeInput.sendKeys(Keys.ENTER);
        return this;
    }

    public CartPage tryRemoveCoupons() {
        try {
            removeCouponButtons.forEach(WebElement::click);
        } catch (NoSuchElementException ignored) {
        }
        return this;
    }

    public CartPage clickUpdateCartButton() {
        updateCartButton.click();
        return this;
    }

    public CartPage clickApplyCouponButton() {
        new WebDriverWait(driver, Constants.DEFAULT_EXPLICIT_WAITING)
                .ignoring(StaleElementReferenceException.class)
                .until(d -> {
                    applyCouponButton.click();
                    return true;
                });
        return this;
    }

    public void clickProceedToPayment() {
        new WebDriverWait(driver, Constants.DEFAULT_EXPLICIT_WAITING)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(proceedToPaymentButton))
                .click();
        //      proceedToPaymentButton.click();
    }

    public Boolean loadingProductTable() {
        try {
            return new WebDriverWait(driver, Constants.DEFAULT_EXPLICIT_WAITING)
                    .ignoring(NoSuchElementException.class)
                    .until(d -> {
                        driver.navigate().refresh();
                        return productTable.isDisplayed();
                    });
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public CartPage clearCart() {
        new WebDriverWait(driver, Constants.DEFAULT_EXPLICIT_WAITING)
                .ignoring(StaleElementReferenceException.class)
                .until(d -> {
                    removeButtons.forEach(WebElement::click);
                    return true;
                });
        return this;
    }

}
