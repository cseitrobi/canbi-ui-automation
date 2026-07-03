package com.pilla.automation;

import com.pilla.automation.base.AllureTestWatcher;
import com.pilla.automation.base.BaseTest;
import com.pilla.automation.pages.*;
        import com.pilla.automation.util.AhvGenerator;
import io.qameta.allure.*;
        import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Test class validating the complete Growth user onboarding flow in the Pilla application.
 * This class breaks the E2E flow into a sequence of dependent tests for clarity,
 * maintainability, and robust reporting. Assertions are performed by checking page source content.
 *
 * @see <a href="https://testng.org/doc/documentation-main.html#dependent-methods">TestNG Dependent Methods</a>
 */
@Slf4j
@Listeners(AllureTestWatcher.class)
@Epic("User Onboarding")
@Feature("Balanced Onboarding Flow for vested benefit solutions")
@Story("As a new user, I want to register, define my profile as Balanced for FZ, select a product, and complete the onboarding process.")
public class FzAccountSolutionOnboardingFlowTest extends BaseTest {

    public static final String OTP_CODE = "0000";
    // --- Test Data ---
    private static final String PASSWORD = "Test@1234";
    private static final String AHV_NUMBER = AhvGenerator.generateAhv();
    // --- Page Objects - Initialized as the flow progresses ---
    private WelcomePage welcomePage;
    private RegisterPage registerPage;
    private PasswordCreatePage passwordCreatePage;
    private LoginPage loginPage;
    private SolutionPage solutionPage;
    private BeforeStartPageFz beforeStartPage;
    private PersonalSituationPageFz personalSituationPageFz;
    private InvestmentProfilePage investmentProfilePage;
    private InvestmentProfileSelectionPage investmentProfileSelectionPage;
    private AccountSolutionProfilePage accountSolutionProfilePage;
    private ConclusionPage conclusionPage;
    private CongratulationsPage congratulationsPage;
    private ChooseFzSolutionPage chooseFzSolutionPage;
    private TransferingYourCapitalPage transferingYourCapitalPage;
    private DashboardPage dashboardPage;

    @BeforeClass
    public void setup() throws InterruptedException {
        Allure.suite(getDeviceName());

        ConsentPage consentPage = new ConsentPage(getDriver(), getLocators());
        boolean permissionHandledBeforeConsent =
                consentPage.clickPermissionAllowIfPresent(Duration.ofSeconds(15));

        boolean consentHandled = consentPage.clickAcceptAllIfPresent(Duration.ofSeconds(10));
        if (consentHandled) {
            consentPage.switchDeToEnglishIfPresent();
            consentPage.clickSkipIfPresent(Duration.ofSeconds(5));
            welcomePage = new WelcomePage(getDriver(), getLocators());
            registerPage = welcomePage.clickRegistrationButton();
        }

        if (!permissionHandledBeforeConsent) {
            consentPage.clickPermissionAllowIfPresent(Duration.ofSeconds(5));
        }
    }

    @Test(priority = 1, description = "Navigate from Welcome screen and register a new user.")
    @Description("This test verifies the initial welcome screen and completes the user registration form.")
    public void testUserRegistration() {
        Allure.step(
                "Verify the registration form is open", () ->
                        Assert.assertTrue(
                                getDriver().getPageSource().contains("First name"),
                                "Registration screen text not found in page source."
                        )
        );

        Allure.step(
                "Navigate to the registration page and fill in user details", () -> {
                    if (registerPage == null) {
                        welcomePage = new WelcomePage(getDriver(), getLocators());
                        registerPage = welcomePage.clickRegistrationButton();
                    }
                    passwordCreatePage = registerPage
                            .clickSalutationRadioButton()
                            .setFirstName(faker.name().firstName())
                            .setLastName(faker.name().lastName())
                            .setPlaceOfResidence()
                            .setEmail()
                            .setPhoneCountryCode()
                            .setMobileNumber(faker.number().digits(9))
                            .clickRegisterButton();

                    // Assert that we have successfully moved to the password creation screen
                    Assert.assertTrue(
                            getDriver().getPageSource().contains("Confirm your password"),
                            "Text 'Confirm your password' not found on password creation page."
                    );
                }
        );
    }
    @Test(priority = 2, dependsOnMethods = "testUserRegistration", description = "Create a password and log in for the first time.")
    @Description("Sets a password for the newly registered user and performs the initial login.")
    public void testPasswordCreationAndLogin() {
        Allure.step(
                "Create and confirm the password", () ->
                        loginPage = passwordCreatePage
                                .setPassword(PASSWORD)
                                .setConfirmPassword(PASSWORD)
                                .clickRegisterButton()
        );

        Allure.step(
                "Log in with the newly created account credentials", () -> {
                    solutionPage = loginPage
                            .setEmail()
                            .setPassword(PASSWORD)
                            .clickLoginButton();

                    // Assert successful login by checking for an element on the next page
                    Assert.assertTrue(
                            getDriver().getPageSource().contains("Choose the solution that suits you"),
                            "Text 'Choose the solution' not found on the solution page."
                    );
                }
        );
    }

    @Test(priority = 3, dependsOnMethods = "testPasswordCreationAndLogin", description = "Select a solution and navigate through the pre-check page.")
    @Description("Chooses the 'Vested Benefit solution' and proceeds past the 'Before you start' information screen.")
    public void testSolutionSelectionAndPreCheck() {
        Allure.step(
                "Select the 'Pillar' solution and continue", () -> {
                    beforeStartPage = solutionPage
                            .clickFzSolution()
                            .clickContinueButtonFz();
                    Assert.assertTrue(
                            getDriver().getPageSource().contains("Before you start"),
                            "Text 'Before you start' not found on the pre-check page."
                    );
                }
        );

        Allure.step(
                "Proceed from the 'Before you start' page", () -> {
                    personalSituationPageFz = beforeStartPage.clickBeforeStartButton();
                    Assert.assertTrue(
                            getDriver().getPageSource().contains("Your personal data"),
                            "Text 'Your personal data' not found on the personal situation page."
                    );
                }
        );
    }

    @Test(priority = 4, dependsOnMethods = "testSolutionSelectionAndPreCheck", description = "Fill in personal and investment profile information.")
    @Description("Enters user's personal details (AHV, DOB, etc.) and determines the initial investment profile.")
    public void testFillPersonalAndInvestmentProfile() throws InterruptedException {
        Allure.step(
                "Fill in personal situation details", () -> {
                    chooseFzSolutionPage = personalSituationPageFz

                            .setDateOfBirth()
                            .setCivilStatus()
                            .setAhvNumber(AHV_NUMBER)
                            .clickContinueButton();
                    Assert.assertTrue(
                            getDriver().getPageSource().contains("Selection of suitable investment solutions, higher earnings potential"),
                            "Text 'Selection of suitable investment solutions, higher earnings potential' not found on page."
                    );
                }
        );


        Allure.step(
                "Choose FZ Account Solution", () -> {
                    accountSolutionProfilePage = chooseFzSolutionPage
                            .clickChooseFzAccountSolutionButtonLocator();
                    Assert.assertTrue(
                            getDriver().getPageSource().contains("Investment strategy / Product selection"),
                            "Text 'Investment strategy / Product selection' not found on page."
                    );
                }
        );
    }

    @Test(priority = 5, dependsOnMethods = "testFillPersonalAndInvestmentProfile", description = "Select the 'Investment Strategy' and a corresponding product.")
    @Description("Overrides the determined profile to select 'Balanced', then chooses the 'Balanced' solution product from full selection screen.")
    public void testBalancedProfileAndProductSelection() throws InterruptedException {
        Allure.step(
                "Confirm the balanced-risk profile and select the 'Balanced' product", () -> {
//                    Assert.assertTrue(
//                            getDriver().getPageSource().contains("The following range of products is available to you"),
//                            "Product range text not found on page."
//                    );

                    transferingYourCapitalPage = accountSolutionProfilePage
                            .clickSelectStrategyFz();
//                    Assert.assertTrue(
//                            getDriver().getPageSource().contains("Would you like Liberty to take care of recovering your pension assets from your previous institution?"),
//                            "Text 'Would you like Liberty to take care of recovering your pension assets from your previous institution?' not found on the final conclusion page."
//                    );
                }
        );
        Allure.step(
                "Choose dont transfer FZ asset", () -> {
                    conclusionPage = transferingYourCapitalPage

                            .clickCTransferActionLocatorButton();
                    Assert.assertTrue(
                            getDriver().getPageSource().contains("Conclusion"),
                            "Text 'Conclusion' not found on page."
                    );
                }
        );

    }



    @Test(priority = 6, dependsOnMethods = "testBalancedProfileAndProductSelection", description = "Complete final details, upload documents, and agree to terms.")
    @Description("Fills in final address details, performs document upload, and accepts the contract to finish onboarding.")
    public void testOnboardingConclusionAndDocumentUpload() throws InterruptedException {
        Allure.step(
                "Fill in final personal details", () -> {
                    conclusionPage.setStreet("Alpenstrasse")
                            .setStreetNumber("80")
                            .setPostalCode("8000")
                            .setTown("Zurich")
                            .setNationality()
                            .clickContinueButton();
                    Thread.sleep(5000);
                    conclusionPage.clickAcceptButton();
                    conclusionPage.clickContinueButtonAfterAccept();
                    conclusionPage.uploadDocument();
                    conclusionPage.contractConsent();
                    Thread.sleep(20000);
                    congratulationsPage = conclusionPage.setOtpCode(OTP_CODE);
                    Thread.sleep(20000);
////                    Assert.assertTrue(
////                            getDriver().getPageSource().contains("Your personal details"),
////                            "Personal details summary is not visible."
////                    );
////                    Assert.assertTrue(
////                            getDriver().getPageSource().contains("Your profile"),
////                            "Profile summary is not visible."
////                    );
                }
        );

//        // Allure.step(
//        //         "Accept details and proceed to document upload", () -> {
//        //             conclusionPage.clickAcceptButton();
//        //             Assert.assertTrue(
//        //                     getDriver().getPageSource()
//        //                             .contains("Make sure you have your passport or ID card with you."),
//        //                     "Passport/ID upload prompt is not visible."
//        //             );
//        //             conclusionPage.clickContinueButtonAfterAccept();
//        //         }
//        // );
//
//        // Allure.step(
//        //         "Upload identification document", () -> {
//        //             conclusionPage.uploadDocument();
//        //             Assert.assertTrue(
//        //                     getDriver().getPageSource().contains("Agree to contract terms"),
//        //                     "Agree to contract terms screen is not visible."
//        //             );
//        //         }
//        // );
//
//        // Allure.step(
//        //         "Agree to contract terms to complete the flow", () -> {
//        //             conclusionPage.contractConsent();
//        //             Assert.assertTrue(
//        //                     getDriver().getPageSource().contains("Please verify your mobile number"),
//        //                     "Please verify your mobile number is not visible."
//        //             );
//        //         }
//        // );
//
//        // // --- DEBUG: end of step 6 ---
//        // log.info("[Test 6 END] All steps passed. About to call setOtpCode(OTP_CODE)...");
//        // try {
//        //     congratulationsPage = conclusionPage.setOtpCode(OTP_CODE);
//        //     log.info("[Test 6 END] setOtpCode() returned. congratulationsPage is {} (null = problem)", congratulationsPage != null ? "set" : "NULL");
//        // } catch (Throwable t) {
//        //     log.error("[Test 6 END] setOtpCode() threw: {} - test 6 will FAIL, test 7 will be SKIPPED", t.getMessage(), t);
//        //     throw t;
//        // }
//        // log.info("[Test 6 END] testOnboardingConclusionAndDocumentUpload finished successfully.");
    }

    @Test(priority = 7, dependsOnMethods = "testOnboardingConclusionAndDocumentUpload", description = "Congratulations page, your opening application is complete.")
    @Description("Congratulations page, your opening application is complete.")
    public void testCongratulationPageAndNavigateToDashboard() throws InterruptedException {

        Allure.step(
                "Congratulations page is visible",
                () -> {
                    Assert.assertTrue(
                            getDriver().getPageSource()
                                    .contains("Congratulations, your opening application is complete!"),
                            "Congratulations, your opening application is complete!"
                    );
                }
        );

        log.info("[Test 7] About to click dashboard button...");
        dashboardPage = congratulationsPage.clickDashboardButton();
        log.info("[Test 7] clickDashboardButton() returned.");

        Allure.step(
                "Dashboard page is visible, onboarding successful",
                () -> Assert.assertTrue(
                        getDriver().getPageSource()
                                .contains("Welcome"),
                        "Dashboard page is not visible."
                )
        );
        Allure.step(
                "Total assets is visible, onboarding successful",
                () -> Assert.assertTrue(
                        getDriver().getPageSource()
                                .contains("Total assets vested benefits solution"),
                        "Total assets is not visible."
                )
        );
        Allure.step(
                "Your portfolio will now be set up in the background, which may take up to 24 hours. is visible, onboarding successful",
                () -> Assert.assertTrue(
                        getDriver().getPageSource()
                                .contains("Your portfolio will now be set up in the background, which may take up to 24 hours."),
                        "Your portfolio will now be set up in the background, which may take up to 24 hours is not visible."
                )
        );
    }
}
