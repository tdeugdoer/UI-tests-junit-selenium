package ui.delivery;

import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.delivery.DeliveryPage;
import ui.BasePageTest;
import utils.FailMessages;
import utils.TestConstants;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeliveryPageTest extends BasePageTest {
    private final DeliveryPage deliveryPage = new DeliveryPage(driver);

    @BeforeEach
    public void setUp() {
        step("Открываем страницу условиями доставки", () ->
                deliveryPage.open(TestConstants.Urls.BASE_URL + TestConstants.Urls.DELIVERY_URL)
        );
    }

    @Test
    @DisplayName("Минимальная сумма доставки")
    @Description("Проверка, что на сайте в этом разделе отображается текст «Минимальная сумма заказа — 800 рублей».")
    @Severity(SeverityLevel.MINOR)
    @Owner("Yahor Tserashkevich")
    @Link(name = "Website", url = "https://pizzeria.skillbox.cc/delivery/")
    public void minDeliveryAmount() {
        step("Получение условий доставки", () -> {
            List<String> termsOfDelivery = deliveryPage.getTermsOfDelivery();

            assertTrue(termsOfDelivery.contains("Минимальная сумма заказа — 800 рублей"), FailMessages.EXPECTED_STRING_MISSING);
        });
    }

}
