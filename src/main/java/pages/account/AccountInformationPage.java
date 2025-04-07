package pages.account;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountInformationPage extends AccountBasePage {
    @FindBy(xpath = "//div[@class='woocommerce-message']")
    private WebElement message;

    public AccountInformationPage(WebDriver driver) {
        super(driver);
    }

    public String getMessage() {
        return message.getText();
    }

}
