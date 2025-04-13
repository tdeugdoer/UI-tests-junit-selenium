package pages.auth;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

public class LoginPage extends BasePage {
    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@name='login']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage fillOutLoginForm(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        return this;
    }

    public void clickLoginButton() {
        loginButton.click();
    }

}
