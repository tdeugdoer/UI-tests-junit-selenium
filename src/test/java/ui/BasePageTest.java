package ui;

import allure.AllureScreenshotExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

@ExtendWith(AllureScreenshotExtension.class)
public abstract class BasePageTest {
    protected final WebDriver driver = new EdgeDriver();

    @AfterEach
    public void quitDriver() {
        driver.quit();
    }

}
