package ui;

import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public abstract class BasePageTest {
    public final WebDriver driver = new EdgeDriver();

    @AfterEach
    public void quitDriver() {
        driver.quit();
    }

}
