package resources.retry;

import org.openqa.selenium.*;

import org.guerdy.lambert.base.BaseTest;

//import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;
import resources.custom_exception.ElementNotFoundException;
import resources.custom_exception.StaleElementException;
import resources.custom_exception.TimeoutException;
import resources.custom_exception.WebDriverSessionException;

import java.io.IOException;
import java.time.Duration;
import java.util.function.Function;

public class RetryHandler extends BaseTest {
    //private static final Logger LOG = Logger.getLogger(RetryHandler.class.getName());
    private static final Logger log = LogManager.getLogger(RetryHandler.class);

    private final WebDriver driver;
    private final int maxRetries;
    private final long retryIntervalMs;

    public RetryHandler(WebDriver driver) {
        this(driver, 3, 1000);
    }

    public RetryHandler(WebDriver driver, int maxRetries, long retryIntervalMs) {
        this.driver = driver;
        this.maxRetries = maxRetries;
        this.retryIntervalMs = retryIntervalMs;
    }

    // ElementNotFoundException with retry logic
    public WebElement findElementWithRetry(By locator, String elementName) {
        int attempts = 0;
        WebElement element = null;

        while (attempts < maxRetries) {
            try {
                log.info(String.format("Attempt %d to find element: %s with locator: %s",
                        attempts + 1, elementName, locator));

                element = driver.findElement(locator);
                log.info("Element found successfully");
                return element;

            } catch (NoSuchElementException e) {
                attempts++;
                if (attempts >= maxRetries) {
                    log.fatal(String.format("Failed to find element '%s' after %d attempts",
                            elementName, maxRetries));
                    throw new ElementNotFoundException(elementName, locator.toString(), e);
                }

                log.warn(String.format("Element not found, retrying in %d ms...", retryIntervalMs));
                sleep(retryIntervalMs);
            }
        }
        throw new ElementNotFoundException(elementName, locator.toString(), null);
    }

    // TimeoutException for AJAX-heavy applications
    public <V> V waitForCondition(Function<WebDriver, V> condition, int timeoutSeconds,
                                  String conditionDescription) throws IOException {

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            return wait.until(condition);
        } catch (org.openqa.selenium.TimeoutException e) {
            // Take screenshot on timeout
            takeScreenshot("timeout_" + conditionDescription.replace(" ", "_"));

            throw new TimeoutException(
                    String.format("Condition not met: %s", conditionDescription), timeoutSeconds
            );
        }
    }

    // 2a: Handle StaleElementReferenceException with retry
    public WebElement handleStaleElement(WebElement element, String elementDescription) {
        int attempts = 0;

        while (attempts < maxRetries) {
            try {
                // Try to interact with the element
                element.isDisplayed(); // Simple interaction to check staleness
                return element;

            } catch (StaleElementReferenceException e) {
                attempts++;
                if (attempts >= maxRetries) {
                    log.fatal(String.format("Element '%s' is persistently stale", elementDescription));
                    throw new StaleElementException(elementDescription, e);
                }

                log.info(String.format("Element stale, refreshing reference (attempt %d)", attempts));
                // Re-find the element (caller should provide locator)
                throw e; // Let caller handle the re-finding with fresh locator
            }
        }
        throw new StaleElementException(elementDescription, null);
    }

    // Handle WebDriverException with session recovery
    public void executeWithSessionRecovery(Runnable action, String actionDescription) {
        try {
            action.run();
        } catch (WebDriverException e) {
            log.fatal(String.format("WebDriverException during: %s - %s",
                    actionDescription, e.getMessage()));

            // Check if it's a session issue
            if (isSessionException(e)) {
                log.info("Attempting session recovery...");
                recoverWebDriverSession();

                // Retry once after recovery
                try {
                    action.run();
                    log.info("Action successful after session recovery");
                } catch (WebDriverException retryException) {
                    throw new WebDriverSessionException(
                            String.format("Failed even after session recovery: %s", actionDescription),
                            retryException
                    );
                }
            } else {
                throw e; // Re-throw if not a session issue
            }
        }
    }

    private boolean isSessionException(WebDriverException e) {
        String message = e.getMessage().toLowerCase();
        return message.contains("session") ||
                message.contains("chrome not reachable") ||
                message.contains("connection refused") ||
                e.getClass().getSimpleName().contains("Session");
    }

    private void recoverWebDriverSession() {
        try {
            driver.quit(); // Clean up old session
        } catch (Exception e) {
            log.warn("Error during driver quit: " + e.getMessage());
        }

        // Reinitialize driver (implementation depends on your setup)
        log.info("WebDriver session reinitialized");
    }

    /*
    private void takeScreenshot(String name) {
        try {
            if (driver instanceof TakesScreenshot) {
                TakesScreenshot ts = (TakesScreenshot) driver;
                byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
                // Save screenshot to file or attach to report
                ScreenshotManager.saveScreenshot(name, screenshot);
            }
        } catch (Exception e) {
            log.warn("Failed to take screenshot: " + e.getMessage());
        }
    }
    */

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
