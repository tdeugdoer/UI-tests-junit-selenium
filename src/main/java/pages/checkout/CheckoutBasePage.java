package pages.checkout;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.time.Duration;

public class CheckoutBasePage extends BasePage {
    @FindBy(xpath = "//h2[@class='post-title']")
    private WebElement postTitle;

    public CheckoutBasePage(WebDriver driver) {
        super(driver);
    }

    public String getPostTitle() {
        return postTitle.getText();
    }

    public String tryGetExpectedPostTitle(String expectedPostTitle, Duration timeout) {
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.textToBePresentInElement(postTitle, expectedPostTitle));
        return getPostTitle();
    }

}
