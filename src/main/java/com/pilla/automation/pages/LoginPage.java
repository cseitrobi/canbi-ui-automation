package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

@Slf4j
public class LoginPage extends BasePage {

    private final String emailAddress;

    private WebElement emailField;
    private WebElement passwordField;
    private WebElement loginButton;

    public LoginPage(String emailAddress, AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        this.emailAddress = emailAddress;
        initializeElements();
    }

    private void initializeElements() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        String emailFieldLocator = locators.get("login-email");
        emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                emailFieldLocator)));

        String passwordFieldLocator = locators.get("login-password");
        passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                passwordFieldLocator)));

        String loginButtonLocator = locators.get("login-button");
        loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                loginButtonLocator)));
    }

    public LoginPage setEmail() {
        this.emailField.sendKeys(emailAddress);
        return this;
    }

    public LoginPage setPassword(String password) {
        this.passwordField.sendKeys(password);
        return this;
    }

    public SolutionPage clickLoginButton() {
        this.loginButton.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        String biometricsDialogLocator = locators.get("login-biometrics");
        waitForClickable(AppiumBy.xpath(biometricsDialogLocator), Duration.ofSeconds(5))
                .ifPresent(biometricsDialog -> {
            if (biometricsDialog.isDisplayed()) {
                biometricsDialog.click();
            }
        });
        log.info("Continuing login flow after optional biometrics prompt check.");
        return new SolutionPage(driver, locators);
    }
}
