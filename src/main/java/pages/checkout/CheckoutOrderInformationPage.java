package pages.checkout;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutOrderInformationPage extends CheckoutBasePage {
    @FindBy(xpath = "//li[@class='woocommerce-order-overview__payment-method method']/strong")
    private WebElement paymentMethod;

    public CheckoutOrderInformationPage(WebDriver driver) {
        super(driver);
    }

    public String getPaymentMethod() {
        return paymentMethod.getText();
    }

}
