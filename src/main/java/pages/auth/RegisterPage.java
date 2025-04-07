package pages.auth;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class RegisterPage extends BasePage {
    @FindBy(id = "reg_username")
    private WebElement usernameField;

    @FindBy(id = "reg_email")
    private WebElement emailField;

    @FindBy(id = "reg_password")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@name='register']")
    private WebElement registerButton;

    @FindBy(xpath = "//div[@class='content-page']/div")
    private WebElement successRegisterMessage;

    @FindBy(xpath = "//ul[@class='woocommerce-error']/li")
    private WebElement errorMessage;

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage fillOutRegisterForm(String username, String email, String password) {
        usernameField.sendKeys(username);
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        return this;
    }

    public RegisterPage clickRegisterButton() {
        registerButton.click();
        return this;
    }

    public String getSuccessRegisterMessage() {
        return successRegisterMessage.getText();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

}
