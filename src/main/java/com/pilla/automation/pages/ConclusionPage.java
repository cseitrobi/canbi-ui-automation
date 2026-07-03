package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class ConclusionPage extends BasePage {

    private WebElement streetElement;
    private WebElement streetNumberElement;
    private WebElement postalCodeElement;
    private WebElement townElement;
    private WebElement nationalityElement;
    private WebElement nationalitySelectElement;
    private WebElement continueElement;
    private WebElement acceptButtonElement;
    private WebElement documentDropdown;
    private WebElement passportSelect;
    private WebElement documentUpload;
    private WebElement documentUploadConsent;
    private WebElement documentReady;
    private WebElement documentNext;
    private WebElement consentFirstElement;
    private WebElement otpCode;
    private WebElement otpConfirm;

    public ConclusionPage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        initializeElements();
    }

    private void initializeElements() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        streetElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-street"))));

        streetNumberElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-number"))));

        postalCodeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-postal-code"))));

        townElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-town"))));

        nationalityElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-nationality"))));
    }

    public ConclusionPage setStreet(String street) {
        streetElement.sendKeys(street);
        return this;
    }

    public ConclusionPage setStreetNumber(String streetNumber) {
        streetNumberElement.sendKeys(streetNumber);
        return this;
    }

    public ConclusionPage setPostalCode(String postalCode) {
        postalCodeElement.sendKeys(postalCode);
        return this;
    }

    public ConclusionPage setTown(String town) {
        townElement.sendKeys(town);
        return this;
    }

    public ConclusionPage setNationality() {
        nationalityElement.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        nationalitySelectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-nationality-select"))));
        nationalitySelectElement.click();
        return this;
    }

    public void clickContinueButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        continueElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-continue-button"))));
        continueElement.click();
    }

    public void clickAcceptButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        acceptButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-accept-button"))));
        acceptButtonElement.click();

        continueElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-continue-button"))));
    }

    public void clickContinueButtonAfterAccept() {
        continueElement.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        documentDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-document-element"))));
    }

    public void uploadDocument() {
        documentDropdown.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        passportSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-document-select"))));
        passportSelect.click();

        documentUpload = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-document-upload"))));
        documentUpload.click();

        documentUploadConsent = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-document-consent"))));
        documentUploadConsent.click();

        documentReady = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-document-ready"))));
        documentReady.click();

        documentNext = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-document-next"))));
        documentNext.click();

        continueElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-continue-button"))));
        continueElement.click();

        consentFirstElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-consent-first"))));
    }

    public void contractConsent() {
        consentFirstElement.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        WebElement consentSecondElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-consent-second"))));
        consentSecondElement.click();

        scrollOneScreenDown(driver);

        WebElement consentThirdElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.androidUIAutomator("new UiSelector().className(\"com.horcrux.svg.RectView\").instance(2)")));
        consentThirdElement.click();

        WebElement concludeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-conclude-contract"))));
        concludeElement.click();

        otpCode = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-otp"))));
    }

    public CongratulationsPage setOtpCode(String code) {
        otpCode.sendKeys(code);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        otpConfirm = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath(locators.get("conclusion-confirm"))));
        otpConfirm.click();
        return new CongratulationsPage(driver, locators);
    }
}
