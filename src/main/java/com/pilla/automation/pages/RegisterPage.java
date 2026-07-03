package com.pilla.automation.pages;

import com.pilla.automation.mail.MailTmService;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class RegisterPage extends BasePage {

    private WebElement salutationRadioButton;
    private WebElement firstName;
    private WebElement lastName;
    private WebElement placeOfResidence;
    private WebElement email;
    private WebElement registerButton;

    private final MailTmService mailTmService;
    private String emailAddress;

    protected RegisterPage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        this.mailTmService = new MailTmService();
        initializeElements();
    }

    /** Resolves locator string to By (xpath, accessibilityId, or androidUIAutomator). */
    private By byFrom(String locatorKey) {
        String v = locators.get(locatorKey);
        if (v == null || v.isEmpty()) return null;
        if (v.startsWith("accessibilityId:")) {
            return AppiumBy.accessibilityId(v.substring("accessibilityId:".length()));
        }
        if (v.startsWith("new UiSelector") || locatorKey != null && locatorKey.endsWith("-uiautomator")) {
            return AppiumBy.androidUIAutomator(v);
        }
        return AppiumBy.xpath(v);
    }

    private void initializeElements() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        salutationRadioButton = wait.until(ExpectedConditions.visibilityOfElementLocated(byFrom("register-salutation")));

        firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(byFrom("register-first-name")));
        lastName = wait.until(ExpectedConditions.visibilityOfElementLocated(byFrom("register-last-name")));

        By placeBy = byFrom("register-place-of-residence");
        placeOfResidence = wait.until(ExpectedConditions.visibilityOfElementLocated(placeBy));
    }

    public RegisterPage clickSalutationRadioButton() {
        salutationRadioButton.click();
        return this;
    }

    public RegisterPage setFirstName(String firstName) {
        this.firstName.sendKeys(firstName);
        return this;
    }

    public RegisterPage setLastName(String lastName) {
        this.lastName.sendKeys(lastName);
        return this;
    }

    public RegisterPage setPlaceOfResidence() {
        placeOfResidence.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        WebElement swissSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(byFrom("register-place-of-residence-select")));
        swissSelect.click();
        email = wait.until(ExpectedConditions.visibilityOfElementLocated(byFrom("register-email")));
        registerButton = wait.until(ExpectedConditions.visibilityOfElementLocated(byFrom("register-register-button")));
        return this;
    }

    /** Phone country selector (after email): tap 🇨🇭 then choose Switzerland +41. */
    public RegisterPage setPhoneCountryCode() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        By triggerBy = byFrom("register-phone-country-trigger");
        if (triggerBy != null) {
            WebElement trigger = wait.until(ExpectedConditions.elementToBeClickable(triggerBy));
            trigger.click();
        }
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        By optionBy = byFrom("register-phone-country-option");
        if (optionBy != null) {
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionBy));
            option.click();
        }
        // Let picker close and UI settle before interacting with mobile field (reduces crash risk)
        try { Thread.sleep(1500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return this;
    }

    public RegisterPage setEmail() {
        emailAddress = mailTmService.getEmailAddress();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        email = wait.until(ExpectedConditions.visibilityOfElementLocated(byFrom("register-email")));
        email.sendKeys(emailAddress);
        return this;
    }

    public RegisterPage setMobileNumber(String mobileNumber) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        By mobileBy = byFrom("register-mobile-number-uiautomator");
        WebElement mobileField = wait.until(ExpectedConditions.elementToBeClickable(mobileBy));
        mobileField.sendKeys(mobileNumber);
        return this;
    }

    public PasswordCreatePage clickRegisterButton() {
        registerButton.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        String activationUrl = mailTmService.getActivationUrl();
        if (activationUrl == null) {
            throw new RuntimeException("Activation email not received within timeout (Mail.tm polling).");
        }
        driver.get(activationUrl);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        return new PasswordCreatePage(emailAddress, driver, locators);
    }
}
