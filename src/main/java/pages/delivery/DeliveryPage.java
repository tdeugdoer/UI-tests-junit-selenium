package pages.delivery;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

import java.util.List;

public class DeliveryPage extends BasePage {
    @FindBy(xpath = "//iframe[contains(@src, 'delivery.html')]")
    private WebElement termsOfDeliveryIframe;

    @FindBy(xpath = "//ul")
    private List<WebElement> termsOfDeliveryUl;


    public DeliveryPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getTermsOfDelivery() {
        driver.switchTo().frame(termsOfDeliveryIframe);
        List<String> termsOfDelivery = termsOfDeliveryUl.stream()
                .map(WebElement::getText)
                .toList();
        driver.switchTo().defaultContent();
        return termsOfDelivery;
    }

}
