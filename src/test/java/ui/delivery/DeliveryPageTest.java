package ui.delivery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.delivery.DeliveryPage;
import ui.BasePageTest;
import utils.FailMessages;
import utils.TestConstants;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeliveryPageTest extends BasePageTest {
    private final DeliveryPage deliveryPage = new DeliveryPage(driver);

    @BeforeEach
    public void setUp() {
        deliveryPage.open(TestConstants.Urls.BASE_URL + TestConstants.Urls.DELIVERY_URL);
    }

    /**
     * Проверка, что на сайте в этом разделе отображается текст «Минимальная сумма заказа — 800 рублей».
     */
    @Test
    public void minDeliveryAmount() {
        List<String> termsOfDelivery = deliveryPage.getTermsOfDelivery();

        assertTrue(termsOfDelivery.contains("Минимальная сумма заказа — 800 рублей"), FailMessages.EXPECTED_STRING_MISSING);
    }

}
