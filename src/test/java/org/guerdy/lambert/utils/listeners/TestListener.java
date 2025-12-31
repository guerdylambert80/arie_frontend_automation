package org.guerdy.lambert.utils.listeners;

import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.guerdy.lambert.base.BaseTest;
import org.guerdy.lambert.utils.extentreports.ExtentManager;
import org.guerdy.lambert.utils.screenshot.Screenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static org.guerdy.lambert.utils.extentreports.ExtentTestManager.getTest;

public class TestListener extends BaseTest implements ITestListener {

    private static final Logger log = LogManager.getLogger(BaseTest.class);

    private static String getTestMethodName(ITestResult result) {
        return result.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        log.info("==========================================================================");
        log.info("Starting test suite: " + iTestContext.getName());
        log.info("==========================================================================");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        log.info("==========================================================================");
        log.info("Finished test suite: " + iTestContext.getName());
        log.info("==========================================================================");

        // Flush ExtentReports
        ExtentManager.extentReports.flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        String testName = iTestResult.getMethod().getMethodName();
        log.info("==========================================================================");
        log.info("STARTING TEST: " + testName);
        log.info("==========================================================================");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        String testName = iTestResult.getMethod().getMethodName();
        log.info("Test PASSED: {}", testName);

        // Update ExtentReports
        if (getTest() != null) {
            getTest().log(Status.PASS, "Test passed");
        }

        // Capture screenshot on success (optional)
        WebDriver driver = BaseTest.driver;
        if (driver != null) {
            String screenshotPath = Screenshot.captureScreenshot(driver, testName, "PASS");
            if (screenshotPath != null) {
                log.info("Success screenshot saved: " + screenshotPath);
            }
        }
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        String testName = iTestResult.getMethod().getMethodName();
        log.error("Test FAILED: " + testName);
        if (iTestResult.getThrowable() != null) {
            log.error("Failure details: " + iTestResult.getThrowable().getMessage());
        }

        // Update ExtentReports
        if (getTest() != null) {
            getTest().log(Status.FAIL, "Test Failed");
            if (iTestResult.getThrowable() != null) {
                getTest().log(Status.FAIL, iTestResult.getThrowable());
            }

            // Get WebDriver instance
            WebDriver driver = BaseTest.driver;

            if (driver != null) {
                // Take screenshot and add to ExtentReports
                String base64Screenshot = Screenshot.captureBase64Screenshot(driver);
                if (base64Screenshot != null) {
                    String base64Image = "data:image/png;base64," + base64Screenshot;
                    getTest().log(Status.FAIL, "Screenshot of failure:",
                            getTest().addScreenCaptureFromBase64String(base64Image).getModel().getMedia().get(0));
                }

                // Capture comprehensive evidence for debugging
                Screenshot.captureEvidence(driver, testName, "FAIL",
                        "Exception: " + (iTestResult.getThrowable() != null ? iTestResult.getThrowable().getMessage() : "Unknown error"));

                log.info("Failure evidence has been captured for test: {}", testName);
            } else {
                log.error("WebDriver was null. Could not capture failure evidence.");
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        String testName = iTestResult.getMethod().getMethodName();
        log.warn("Test SKIPPED: {}", testName);

        // Update ExtentReports
        if (getTest() != null) {
            getTest().log(Status.SKIP, "Test Skipped");
        }

        // Capture evidence for skipped test (optional)
        WebDriver driver = BaseTest.driver;
        if (driver != null) {
            Screenshot.captureScreenshot(driver, testName, "SKIP");
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        String testName = iTestResult.getMethod().getMethodName();
        log.warn("Test failed but within success percentage: " + testName);

        // Capture evidence
        WebDriver driver = BaseTest.driver;
        if (driver != null) {
            Screenshot.captureEvidence(driver, testName, "PARTIAL",
                    "Test failed but within success percentage");
        }
    }
}
