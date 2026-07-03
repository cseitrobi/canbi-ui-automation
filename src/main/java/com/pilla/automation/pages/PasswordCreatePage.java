package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class PasswordCreatePage extends BasePage {

    private final String emailAddress;

    private WebElement passwordField;
    private WebElement confirmPasswordField;
    private WebElement registerButton;

    public PasswordCreatePage(String emailAddress, AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        this.emailAddress = emailAddress;
        initializeElements();
    }

    private void initializeElements() {
        String passwordFieldLocator = locators.get("password-create-password");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                passwordFieldLocator)));

        String confirmPasswordFieldLocator = locators.get("password-create-confirm-password");
        confirmPasswordField = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                confirmPasswordFieldLocator)));

        String registerButtonFieldLocator = locators.get("password-register-button");
        registerButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                registerButtonFieldLocator)));
    }

    public PasswordCreatePage setPassword(String password) {
        this.passwordField.sendKeys(password);
        return this;
    }

    public PasswordCreatePage setConfirmPassword(String confirmPassword) {
        this.confirmPasswordField.sendKeys(confirmPassword);
        return this;
    }

    public LoginPage clickRegisterButton() {
        this.registerButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        String continueButtonLocator = locators.get("password-continue-button");
        WebElement continueButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                continueButtonLocator)));
        continueButton.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new LoginPage(emailAddress, driver, locators);
    }
}
