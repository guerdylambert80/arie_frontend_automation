package org.guerdy.lambert.utils.screenshot;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.guerdy.lambert.base.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screenshot {

    private static final Logger log = LogManager.getLogger(BaseTest.class);
    private static final String SCREENSHOTS_DIRECTORY = System.getProperty("user.dir") + "/src/test/java/resources/screenshots/";
    private static final String EVIDENCE_DIRECTORY = System.getProperty("user.dir") + "/src/test/java/resources/evidence/";

    /**
     * Takes a screenshot and saves it with the given test name and timestamp
     * @param driver WebDriver instance
     * @param testName Name of the test method
     * @param status Test status (pass/fail/warning)
     * @return Path to the saved screenshot
     */
    public static String captureScreenshot(WebDriver driver, String testName, String status) {
        if (driver == null) {
            log.error("WebDriver is null. Cannot capture screenshot.");
            return null;
        }

        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String fileName = testName + "_" + status + "_" + timestamp + ".png";
        String filePath = SCREENSHOTS_DIRECTORY + fileName;

        try {
            // Create screenshots directory if it doesn't exist
            File directory = new File(SCREENSHOTS_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Take screenshot and save to file
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File(filePath));

            log.info("Screenshot captured: " + filePath);
            return filePath;
        } catch (IOException e) {
            log.error("Failed to capture screenshot: " + e.getMessage());    //, e
            return null;
        }
    }

    /**
     * Takes a base64 screenshot for embedding in reports
     * @param driver WebDriver instance
     * @return Base64 string representation of the screenshot
     */
    public static String captureBase64Screenshot(WebDriver driver) {
        if (driver == null) {
            log.error("WebDriver is null. Cannot capture base64 screenshot.");
            return null;
        }

        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }

    /**
     * Saves an evidence file (like page source, logs, etc.) for debugging
     * @param testName Name of the test method
     * @param fileName Name of the file to save
     * @param content Content to write to the file
     * @return Path to the saved evidence file
     */
    public static String saveEvidence(String testName, String fileName, String content) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String directory = EVIDENCE_DIRECTORY + testName + "/" + timestamp + "/";
        String filePath = directory + fileName;

        try {
            // Create evidence directory if it doesn't exist
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Write content to file
            File evidenceFile = new File(filePath);
            FileUtils.writeStringToFile(evidenceFile, content, "UTF-8");

            log.info("Evidence saved: {}", filePath);
            return filePath;
        } catch (IOException e) {
            log.error("Failed to save evidence: {}", e.getMessage()); //, e
            return null;
        }
    }

    /**
     * Captures additional test evidence including screenshot and page source
     * @param driver WebDriver instance
     * @param testName Name of the test method
     * @param status Test status (pass/fail/warning)
     * @param message Additional message to log
     */
    public static void captureEvidence(WebDriver driver, String testName, String status, String message) {
        //log.info("Capturing evidence for test: " + testName + " - Status: " + status);
        log.info("Capturing evidence for test: {} - Status: {}", testName, status);

        if (driver != null) {
            // Capture screenshot
            captureScreenshot(driver, testName, status);

            // Save page source
            try {
                String pageSource = driver.getPageSource();
                saveEvidence(testName, "page_source.html", pageSource);
            } catch (Exception e) {
                log.error("Failed to capture page source: {}", e.getMessage());  //, e
            }

            // Save current URL
            try {
                String currentUrl = driver.getCurrentUrl();
                saveEvidence(testName, "current_url.txt", currentUrl);
            } catch (Exception e) {
                log.error("Failed to capture current URL: {}", e.getMessage());  //, e
            }
        }

        // Save additional message if provided
        if (message != null && !message.isEmpty()) {
            saveEvidence(testName, "additional_info.txt", message);
        }

        log.info("Evidence capture completed for test: {}", testName);
    }
}
