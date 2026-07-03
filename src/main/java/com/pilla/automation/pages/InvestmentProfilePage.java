package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class InvestmentProfilePage extends BasePage {

    private WebElement horizonDropdown;
    private WebElement horizonAgeDropdown;
    private WebElement horizonAgeDropdownSelect;
    private WebElement horizonSelect;
    private WebElement horizonYearsSelect;
    private WebElement expensesDropdown;
    private WebElement expensesMostOfItSelect;
    private WebElement expensesLessThenSelect;
    private WebElement incomeDropdown;
    private WebElement incomeIncreaseSelect;
    private WebElement incomeAssetsSelect;
    private WebElement incomeSecuritySelect;
    private WebElement riskProfileDropdown;
    private WebElement riskFluctuationSelect;
    private WebElement riskLessRiskySelect;
    private WebElement selectDifferentInvestmentProfileButton;

    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected InvestmentProfilePage(AppiumDriver driver, Map<String, String> locators) {
        super(driver, locators);
        initializeElements();
    }

    private void initializeElements() {
        String horizonDropdownLocator = locators.get("investment-profile-horizon");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        horizonDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                horizonDropdownLocator)));
    }

    public InvestmentProfilePage clickHorizonDropdown() {
        horizonDropdown.click();

        String horizonAgeDropdownLocator = locators.get("investment-profile-horizon-age");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        horizonAgeDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                horizonAgeDropdownLocator)));

        return this;
    }

    public InvestmentProfilePage setHorizonAge() {
        horizonAgeDropdown.click();

        String horizonAgeDropdownSelectLocator = locators.get("investment-profile-horizon-age-select");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        horizonAgeDropdownSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                horizonAgeDropdownSelectLocator)));
        horizonAgeDropdownSelect.click();

        String horizonSelectLocator = locators.get("investment-profile-horizon-select");
        horizonSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(horizonSelectLocator)));
        horizonSelect.click();

        String horizonYearsSelectLocator = locators.get("investment-profile-horizon-years-select");
        horizonYearsSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                horizonYearsSelectLocator)));
        horizonYearsSelect.click();

        String expensesDropdownLocator = locators.get("investment-profile-expenses");
        expensesDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(expensesDropdownLocator)));
        return this;
    }

    /**
     * Sets the expenses by scrolling to the expenses dropdown, clicking it,
     * and selecting the appropriate options.
     *
     * @return This InvestmentProfilePage instance for method chaining
     */
    public InvestmentProfilePage setExpenses() {
        expensesDropdown.click();

        String expensesMostOfItSelectLocator = locators.get("investment-profile-expenses-most-of-it");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        expensesMostOfItSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                expensesMostOfItSelectLocator)));
        expensesMostOfItSelect.click();

        scrollOneScreenDown(driver);

        String expensesLessThenSelectLocator = locators.get("investment-profile-expenses-longer-than-24-months");
        expensesLessThenSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                expensesLessThenSelectLocator)));
        expensesLessThenSelect.click();

        return this;
    }

    /**
     * Sets the income options by scrolling to the income dropdown, clicking it,
     * and selecting the appropriate options.
     *
     * @return This InvestmentProfilePage instance for method chaining
     */
    public InvestmentProfilePage setIncome() {
        // Find the income dropdown
        String incomeDropdownLocator = locators.get("investment-profile-income");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        incomeDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(incomeDropdownLocator)));

        // Click the income dropdown
        incomeDropdown.click();

        // Wait for and select "Increase" option
        String incomeIncreaseSelectLocator = locators.get("investment-profile-income-increase");
        incomeIncreaseSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                incomeIncreaseSelectLocator)));
        incomeIncreaseSelect.click();
        scrollOneScreenDown(driver);
        // Wait for and select "Assets" option
        String incomeAssetsSelectLocator = locators.get("investment-profile-income-assets");
        incomeAssetsSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                incomeAssetsSelectLocator)));
        incomeAssetsSelect.click();
//        scrollOneScreenDown(driver);
        // Wait for and select "Security" option
        String incomeSecuritySelectLocator = locators.get("investment-profile-income-security");
        incomeSecuritySelect = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                incomeSecuritySelectLocator)));
        incomeSecuritySelect.click();
        return this;
    }

    /**
     * Sets the risk profile options by scrolling to the risk profile dropdown, clicking it,
     * and selecting the appropriate options.
     *
     * @return This InvestmentProfilePage instance for method chaining
     */
    public InvestmentProfilePage setRiskProfile() {
        // Find the risk profile dropdown
        String riskProfileDropdownLocator = locators.get("investment-profile-risk-profile");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(900));
        riskProfileDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                riskProfileDropdownLocator)));
        // Click the risk profile dropdown
        riskProfileDropdown.click();
        scrollOneScreenDown(driver);
        // Wait for and select "Fluctuation" option
        String riskFluctuationSelectLocator = locators.get("investment-profile-risk-fluctuation");
        riskFluctuationSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().text(\"I am willing to accept large value fluctuations (e.g. –20% to +20% per year).\")")));
        riskFluctuationSelect.click();
        scrollOneScreenDown(driver);
        // Wait for and select "Less Risky" option
        String riskLessRiskySelectLocator = locators.get("investment-profile-risk-less-risky");
        riskLessRiskySelect = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                riskLessRiskySelectLocator)));
        riskLessRiskySelect.click();

        scrollOneScreenDown(driver);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * Clicks the continue button. Re-finds the element immediately before clicking
     * to avoid StaleElementReferenceException (DOM can refresh after setRiskProfile).
     *
     * @return This InvestmentProfilePage instance for method chaining
     */
    public InvestmentProfilePage clickContinueButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        String continueButtonLocator = locators.get("investment-profile-continue-button");
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath(continueButtonLocator)));
        button.click();
        return this;
    }

    public boolean waitForProfileDetermined() {
        Duration timeout = Duration.ofSeconds(30);
        String selectDifferentInvestmentProfileButtonLocator =
                locators.get("investment-profile-different-investment-profile");

        if (waitForVisible(By.xpath(selectDifferentInvestmentProfileButtonLocator), timeout).isPresent()) {
            return true;
        }

        WebDriverWait wait = new WebDriverWait(driver, timeout);
        return Boolean.TRUE.equals(wait.until(d -> {
            String src = d.getPageSource();
            return src.contains("Risk profile determined for you")
                    || src.contains("Select different investment profile")
                    || src.contains("Confirm investment profile");
        }));
    }

    /**
     * Clicks the "Select Different Investment Profile" button after scrolling to ensure it's visible.
     * Then navigates to the InvestmentProfileSelectionPage.
     *
     * @return A new InvestmentProfileSelectionPage instance
     */
    public InvestmentProfileSelectionPage clickSelectDifferentInvestmentProfileButton() throws InterruptedException {
        Thread.sleep(7000);
        scrollOneScreenDown(driver);
        String selectDifferentInvestmentProfileButtonLocator = locators.get("investment-profile-different-investment-profile");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        selectDifferentInvestmentProfileButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                selectDifferentInvestmentProfileButtonLocator)));
        selectDifferentInvestmentProfileButton.click();
        return new InvestmentProfileSelectionPage(driver, locators);
    }
}
