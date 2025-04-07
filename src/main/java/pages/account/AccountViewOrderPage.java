package pages.account;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountViewOrderPage extends AccountBasePage {
    @FindBy(xpath = "//section[@class='woocommerce-customer-details']/address")
    private WebElement customerDetails;

    public AccountViewOrderPage(WebDriver driver) {
        super(driver);
    }

    public String getCustomerDetails() {
        return customerDetails.getText();
    }

}
