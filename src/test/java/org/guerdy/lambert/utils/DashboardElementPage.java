package org.guerdy.lambert.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.guerdy.lambert.base.BaseTest;
import org.guerdy.lambert.pages.UploadPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.guerdy.lambert.base.BaseTest.dashboardLocatorsProp;

public class DashboardElementPage extends BaseTest {
    private static final Logger log = LogManager.getLogger(DashboardElementPage.class);

    public static UploadPage clickUploadButton() {
        log.debug("Attempting to click upload button");
        try {
            //locate and click on the upload button
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dashboardLocatorsProp.getProperty("upload_button"))));
            log.debug("Upload button is present and ready");

            driver.findElement(By.xpath(dashboardLocatorsProp.getProperty("upload_button"))).click();
            log.info("Upload button clicked successfully");

            return new UploadPage();
        } catch (Exception e) {
            log.error("Failed to click upload button: {}", e.getMessage(), e);
            throw e;
        }
    }


    public static void clickSwaps(WebElement e) {
        e.click();
    }

    public static void clickDsc(WebElement e) {
        e.click();
    }

    public static WebElement getBrowseButton() {
        //locate and click on the browse button
        return driver.findElement(By.xpath(dashboardLocatorsProp.getProperty("browse_button")));    //.click();
    }

    public static void clickSubmitButton() {
        log.debug("Attempting to click submit button");
        try {
            //locate and click on the submit button
            driver.findElement(By.xpath(dashboardLocatorsProp.getProperty("file_submit_btn"))).click();
            log.info("Submit button clicked successfully");
        } catch (Exception e) {
            log.error("Failed to click submit button: {}", e.getMessage(), e);
            throw e;
        }
    }

    public static void upload1004Form() {
        log.info("Starting 1004 form upload process");
        
        try {
            clickUploadButton();
            log.debug("Upload button clicked");
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20L));
            
            // Wait for the file input element to be present (not the span button)
            // The actual file input is a hidden <input type="file"> element inside the dropzone
            // HTML structure: <div class="_dropzone_ostu9_45">...<input type="file" style="display: none;">...</div>
            // The visible <span class="_browse_ostu9_75">browse</span> is just UI decoration
            log.debug("Waiting for file input element to be present");
            WebElement fileInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath(
                            "//div[contains(@class, '_dropzone')]//input[@type='file']")  // Specific to dropzone
                )
            );
            log.debug("File input element found and ready");
            
            // Use absolute path - convert relative path to absolute
            String filePath = System.getProperty("user.home") +
                    "/Desktop/Residential/Magny.pdf";
                //"/Desktop/Residential/Elizabeth- 873 Kilsyth Rd -1P.5b.col.3036.pdf";

            log.info("Preparing to upload file: {}", filePath);
            
            // Send keys to the INPUT element, not the span
            // Selects file to upload
            fileInput.sendKeys(filePath);
            log.info("File path sent to input element successfully");

            // Click Submit button
            log.debug("Waiting for submit button to be visible");
            WebElement submitBtn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(dashboardLocatorsProp.getProperty("file_submit_btn"))
                )
            );
            submitBtn.click();
            log.info("Submit button clicked - file upload in progress");

            Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(120))  // Total wait time
                    .pollingEvery(Duration.ofSeconds(15))  // Check every 2 seconds
                    .ignoring(NoSuchElementException.class)  // Ignore specific exceptions
                    .ignoring(StaleElementReferenceException.class)
                    .withMessage("Element not found within timeout");

            // Using fluent wait
            WebElement element = fluentWait.until(driver -> {
                WebElement elem = driver.findElement(By.xpath(dashboardLocatorsProp.getProperty("upload_button")));
                return elem.isDisplayed() ? elem : null;

            });

            log.info("File upload completed successfully");
            
        } catch (org.openqa.selenium.TimeoutException e) {
            log.error("Timeout waiting for element during file upload: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during file upload: {}", e.getMessage(), e);
            throw e;
        }
    }

}
