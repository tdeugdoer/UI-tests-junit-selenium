package ui.bonus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.bonus.BonusPage;
import ui.BasePageTest;
import utils.FailMessages;
import utils.TestConstants;
import utils.data.BonusData;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BonusPageTest extends BasePageTest {
    private final BonusPage bonusPage = new BonusPage(driver);

    @BeforeEach
    public void setUp() {
        bonusPage.open(TestConstants.Urls.BASE_URL + TestConstants.Urls.BONUS_URL);
    }

    /**
     * Успешное оформление карты с проверкой текста «Заявка отправлена, дождитесь, пожалуйста, оформления карты!»
     */
    @Test
    public void createBonusCard() {
        String alertMessage = bonusPage.fillOutBonusForm(BonusData.USERNAME, BonusData.PHONE_NUMBER)
                .clickSubmit()
                .getAlertMessage();
        String resultMessage = bonusPage.getResultMessage();

        assertAll(
                () -> assertEquals("Заявка отправлена, дождитесь, пожалуйста, оформления карты!", alertMessage, FailMessages.STRING_NOT_MATCH_EXPECTED),
                () -> assertEquals("Ваша карта оформлена!", resultMessage, FailMessages.STRING_NOT_MATCH_EXPECTED)
        );
    }


}
