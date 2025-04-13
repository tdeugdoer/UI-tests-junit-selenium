package pages.checkout;

import formData.CheckoutFormData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class CheckoutDataFillingPage extends CheckoutBasePage {
    @FindBy(id = "billing_first_name")
    private WebElement firstNameInput;

    @FindBy(id = "billing_last_name")
    private WebElement lastNameInput;

    @FindBy(id = "billing_country")
    private WebElement countrySelect;

    @FindBy(id = "billing_address_1")
    private WebElement addressInput;

    @FindBy(id = "billing_city")
    private WebElement cityInput;

    @FindBy(id = "billing_state")
    private WebElement stateInput;

    @FindBy(id = "billing_postcode")
    private WebElement postcodeInput;

    @FindBy(id = "billing_phone")
    private WebElement phoneInput;

    @FindBy(id = "order_date")
    private WebElement orderDateInput;

    @FindBy(id = "payment_method_cod")
    private WebElement paymentInCashOnDeliveryRadioButton;

    @FindBy(id = "terms")
    private WebElement termsCheckbox;

    @FindBy(id = "place_order")
    private WebElement placeOrderButton;

    public CheckoutDataFillingPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutDataFillingPage fillOutOrderDetails(CheckoutFormData formData) {
        enterField(firstNameInput, formData.getFirstName());
        enterField(lastNameInput, formData.getLastName());
        selectCountry(formData.getCountry());
        enterField(addressInput, formData.getAddress());
        enterField(cityInput, formData.getCity());
        enterField(stateInput, formData.getState());
        enterField(postcodeInput, formData.getPostcode());
        enterField(phoneInput, formData.getPhone());
        return this;
    }

    private void enterField(WebElement inputField, String value) {
        inputField.clear();
        inputField.sendKeys(value);
    }

    public void selectCountry(String country) {
        new Select(countrySelect).selectByVisibleText(country);
    }

    public CheckoutDataFillingPage enterOrderDate(String orderDate) {
        orderDateInput.clear();
        orderDateInput.sendKeys(orderDate);
        return this;
    }

    public CheckoutDataFillingPage selectPaymentInCashOnDeliveryRadioButton() {
        paymentInCashOnDeliveryRadioButton.click();
        return this;
    }

    public CheckoutDataFillingPage clickTermsCheckbox() {
        termsCheckbox.click();
        return this;
    }

    public CheckoutDataFillingPage clickPlaceOrderButton() {
        placeOrderButton.click();
        return this;
    }

}
