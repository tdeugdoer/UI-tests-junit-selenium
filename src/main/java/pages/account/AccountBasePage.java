package pages.account;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class AccountBasePage extends BasePage {
    @FindBy(xpath = "//li[contains(@class, 'woocommerce-MyAccount-navigation-link woocommerce-MyAccount-navigation-link--edit-account')]")
    private WebElement editAccountLink;

    @FindBy(xpath = "//li[contains(@class, 'woocommerce-MyAccount-navigation-link woocommerce-MyAccount-navigation-link--orders')]/a")
    private WebElement ordersAccountLink;

    public AccountBasePage(WebDriver driver) {
        super(driver);
    }

    public void clickEditAccountLink() {
        editAccountLink.click();
    }

    public void clickOrdersAccountLink() {
        ordersAccountLink.click();
    }

}
