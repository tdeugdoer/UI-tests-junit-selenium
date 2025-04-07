package pages.promo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class PromoPage extends BasePage {
    @FindBy(xpath = "//div[@class='content-page']/ul/li[1]/strong")
    private WebElement firstCoupon;

    public PromoPage(WebDriver driver) {
        super(driver);
    }

    public String getFirstCoupon() {
        return firstCoupon.getText();
    }

}
