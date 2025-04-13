package pages.bonus;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;
import utils.Constants;

import java.time.Duration;

public class BonusPage extends BasePage {
    @FindBy(id = "bonus_username")
    private WebElement usernameInput;

    @FindBy(id = "bonus_phone")
    private WebElement phoneInput;

    @FindBy(xpath = "//button[@type='submit' and @name='bonus']")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@id='bonus_main']/h3")
    private WebElement resultMessage;

    public BonusPage(WebDriver driver) {
        super(driver);
    }

    public BonusPage fillOutBonusForm(String username, String phone) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
        phoneInput.clear();
        phoneInput.sendKeys(phone);
        return this;
    }

    public BonusPage clickSubmit() {
        submitButton.click();
        return this;
    }

    public String getAlertMessage() {
        Alert alert = new WebDriverWait(driver, Constants.DEFAULT_EXPLICIT_WAITING)
                .until(ExpectedConditions.alertIsPresent());
        String alertMessage = alert.getText();
        alert.accept();
        return alertMessage;
    }

    public String getResultMessage() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(resultMessage))
                .getText();
    }

}
