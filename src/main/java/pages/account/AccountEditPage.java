package pages.account;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountEditPage extends AccountBasePage {
    @FindBy(id = "uploadFile")
    private WebElement uploadFileInput;

    @FindBy(xpath = "//button[@name='save_account_details']")
    private WebElement saveAccountDetailsButton;

    public AccountEditPage(WebDriver driver) {
        super(driver);
    }

    public AccountEditPage inputFile(String filePath) {
        uploadFileInput.sendKeys(filePath);
        return this;
    }

    public void clickSaveAccountDetailsButton() {
        saveAccountDetailsButton.click();
    }

}
