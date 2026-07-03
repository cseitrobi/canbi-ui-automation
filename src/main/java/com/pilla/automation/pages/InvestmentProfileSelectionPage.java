package com.pilla.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class InvestmentProfileSelectionPage extends BasePage {

    private WebElement lowRiskProfile;
    private WebElement threeALowRiskProfile;
    private WebElement confirmButton;
    private WebElement conservativeProfile;
    private WebElement balancedProfile;
    private WebElement dynamicProfile;
    private WebElement growthProfile;
    private WebElement capitalProfile;


    /**
     * Constructor for the BasePage.
     *
     * @param driver   The Appium driver instance
     * @param locators Map of locator identifiers to their expressions
     */
    protected InvestmentProfileSelectionPage(
            AppiumDriver driver,
            Map<String, String> locators
    ) {
        super(driver, locators);
        initializeElements();
    }

    private void initializeElements() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3000));

        String lowRiskProfileLocator = locators.get("investment-profile-selection-low-risk");
        lowRiskProfile = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                lowRiskProfileLocator)));

        String threeALowRiskProfileLocator = locators.get("investment-profile-selection-low-risk");
        threeALowRiskProfile = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                threeALowRiskProfileLocator)));

        String conservativeRiskProfileLocator = locators.get("investment-profile-selection-conservative-risk");
        conservativeProfile = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                conservativeRiskProfileLocator)));

        String balancedProfileLocator = locators.get("investment-profile-selection-balanced-risk");
        balancedProfile = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                balancedProfileLocator)));

        String dynamicProfileLocator = locators.get("investment-profile-selection-dynamic-risk");
        dynamicProfile = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                dynamicProfileLocator)));

        String growthProfileLocator = locators.get("investment-profile-selection-growth-risk");
        growthProfile = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                growthProfileLocator)));

        String capitalProfileLocator = locators.get("investment-profile-selection-capital-risk");
        capitalProfile = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                capitalProfileLocator)));
    }

    public InvestmentProfileSelectionPage clickLowRiskProfile() {
        lowRiskProfile.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scrollOneScreenDown(driver);
        String confirmButtonLocator = locators.get("investment-profile-selection-confirm");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3000));
        confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                confirmButtonLocator)));
        return this;
    }

    public InvestmentProfileSelectionPage clickThreeALowRiskProfile() {
        threeALowRiskProfile.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scrollOneScreenDown(driver);
        String confirmButtonLocator = locators.get("investment-profile-selection-confirm");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3000));
        confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                confirmButtonLocator)));
        return this;
    }

    public InvestmentProfileSelectionPage clickConservativeRiskProfile() {
        conservativeProfile.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scrollOneScreenDown(driver);
        String confirmButtonLocator = locators.get("investment-profile-selection-confirm");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3000));
        confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                confirmButtonLocator)));
        return this;
    }

    public InvestmentProfileSelectionPage clickBalancedProfile() {
        balancedProfile.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scrollOneScreenDown(driver);
        String confirmButtonLocator = locators.get("investment-profile-selection-confirm");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3000));
        confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                confirmButtonLocator)));
        return this;
    }
    public InvestmentProfileSelectionPage clickDynamicProfile() {
        dynamicProfile.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scrollOneScreenDown(driver);
        String confirmButtonLocator = locators.get("investment-profile-selection-confirm");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3000));
        confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                confirmButtonLocator)));
        return this;
    }

    public InvestmentProfileSelectionPage clickGrowthProfile() {
        growthProfile.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scrollOneScreenDown(driver);
        String confirmButtonLocator = locators.get("investment-profile-selection-confirm");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3000));
        confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                confirmButtonLocator)));
        return this;
    }
    public InvestmentProfileSelectionPage clickCapitalProfile() {
        capitalProfile.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scrollOneScreenDown(driver);
        String confirmButtonLocator = locators.get("investment-profile-selection-confirm");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3000));
        confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(
                confirmButtonLocator)));
        return this;
    }

    public ProductSelectionPage clickConfirmButton() {
        confirmButton.click();
        return new ProductSelectionPage(driver, locators);
    }

    public ConservativePortfolioPage clickConservativeConfirmButton() {
        confirmButton.click();
        return new ConservativePortfolioPage(driver, locators);
    }

    public BalancedProfilePage clickBalancedConfirmButton() {
        confirmButton.click();
        return new BalancedProfilePage(driver, locators);
    }
    public DynamicProfilePage clickDynamicConfirmButton() {
        confirmButton.click();
        return new DynamicProfilePage(driver, locators);
    }
    public LowRiskPortfolioPage clickLowRiskConfirmButton() {
        confirmButton.click();
        return new LowRiskPortfolioPage(driver, locators);
    }

    public ThreeALowRiskPortfolioPage clickThreeALowRiskConfirmButton() {
        confirmButton.click();
        return new ThreeALowRiskPortfolioPage(driver, locators);
    }

    public void clickDynamicConfirmButtonOnly() {
        confirmButton.click();
    }

    public DisclaimerTextPage getDisclaimerTextPage() {
        return new DisclaimerTextPage(driver, locators);
    }

    public GrowthProfilePage clickGrowthConfirmButton() {
        confirmButton.click();
        return new GrowthProfilePage(driver, locators);
    }

    public CapitalProfilePage clickCapitalConfirmButton() {
        confirmButton.click();
        return new CapitalProfilePage(driver, locators);
    }


}
