package allure;

import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Field;


public class AllureScreenshotExtension implements AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isPresent()) {
            Object testInstance = context.getRequiredTestInstance();
            WebDriver driver = findDriverInHierarchy(testInstance);

            if (driver != null) {
                attachScreenshot(driver);
                attachPageSource(driver);
            }
        }
    }

    private WebDriver findDriverInHierarchy(Object testInstance) {
        Class<?> currentClass = testInstance.getClass();

        while (currentClass != Object.class) {
            try {
                Field driverField = currentClass.getDeclaredField("driver");
                driverField.setAccessible(true);
                return (WebDriver) driverField.get(testInstance);
            } catch (NoSuchFieldException e) {
                currentClass = currentClass.getSuperclass();
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Ошибка доступа к полю driver", e);
            }
        }

        return null;
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    private byte[] attachScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "HTML страницы", type = "text/html", fileExtension = "html")
    public String attachPageSource(WebDriver driver) {
        return driver.getPageSource();
    }

}
