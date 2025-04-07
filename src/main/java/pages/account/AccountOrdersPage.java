package pages.account;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AccountOrdersPage extends AccountBasePage {
    @FindBy(xpath = "//td[@class='woocommerce-orders-table__cell woocommerce-orders-table__cell-order-number']/a")
    private List<WebElement> numberOrders;

    public AccountOrdersPage(WebDriver driver) {
        super(driver);
    }

    public void clickFirstOrder() {
        numberOrders.getFirst().click();
    }

}
