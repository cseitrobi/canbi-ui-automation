package com.pilla.automation.base;

import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

@Slf4j
public class AllureTestWatcher implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            WebDriver driver = WebDriverHolder.getDriver();
            if (driver != null) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Screenshot on Failure", new ByteArrayInputStream(screenshot));
            }
        } catch (Exception e) {
            log.debug("Could not add failure screenshot to Allure: {}", e.getMessage());
        }
        try {
            Throwable cause = result.getThrowable();
            if (cause != null) {
                String msg = cause.getMessage();
                Allure.addAttachment("Error message", msg != null ? msg : cause.getClass().getSimpleName());
            }
        } catch (Exception e) {
            log.debug("Could not add error attachment to Allure: {}", e.getMessage());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            Allure.step("Test passed: " + result.getName());
        } catch (Exception e) {
            log.debug("Could not add Allure step for success: {}", e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        try {
            Allure.step("Test skipped: " + result.getName());
        } catch (Exception e) {
            log.debug("Could not add Allure step for skipped test (no running Allure context): {}", e.getMessage());
        }
    }

    @Override
    public void onStart(ITestContext context) {
        // Not used
    }

    @Override
    public void onFinish(ITestContext context) {
        // Not used
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Not used
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not used
    }
}
