package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

/**
 * Page object representing the Welcome page of the application.
 * This is typically the first page users see when opening the app.
 * It provides options to register or login to the application.
 */
public class WelcomePage extends BasePage {

    /**
     * Button element for initiating the registration process.
     */
    private WebElement registrationButton;

    /**
     * Button element for initiating the login process.
     */
    private WebElement loginButton;

    /**
     * Constructor for the WelcomePage.
     * Initializes the page elements after calling the parent constructor.
     *
     * @param driver The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    public WelcomePage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        initializeElements();
    }

    /**
     * Initializes the page elements by finding them in the DOM.
     * Uses explicit waits to ensure elements are visible before proceeding.
     */
    private void initializeElements() {
        String registrationButtonLocator = locators.get("welcome-registration-button");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        registrationButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                registrationButtonLocator)));

        String loginButtonLocator = locators.get("welcome-login-button");
        loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(loginButtonLocator)));
    }

    /**
     * Clicks the registration button and navigates to the registration page.
     *
     * @return A new instance of the RegisterPage
     */
    public RegisterPage clickRegistrationButton() {
        registrationButton.click();
        return new RegisterPage(driver, locators);
    }

    /**
     * Clicks the login button to navigate to the login page.
     */
    public void clickLoginButton() {
        loginButton.click();
    }
}
