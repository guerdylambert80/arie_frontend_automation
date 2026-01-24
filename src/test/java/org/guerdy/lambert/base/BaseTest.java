package org.guerdy.lambert.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.guerdy.lambert.utils.extentreports.ExtentManager;
import org.guerdy.lambert.utils.listeners.TestListener;

@Listeners(TestListener.class)
public class BaseTest {
    // Static initializer to ensure logs directory exists BEFORE Log4j2 initializes
    static {
        ensureLogsDirectoryExistsStatic();
        // Force Log4j2 to use its own provider instead of SLF4J bridge
        System.setProperty("log4j2.loggerContextFactory", "org.apache.logging.log4j.core.impl.Log4jContextFactory");
    }
    
    private static final Logger log = LogManager.getLogger(BaseTest.class);
    @Getter
    //public static WebDriver driver;
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static Properties configLocatorsProp = new Properties();
    public static Properties loginLocatorsProp = new Properties();
    public static Properties dashboardLocatorsProp = new Properties();
    public static Properties uploadLocatorsProp = new Properties();

    public static FileReader configFileReader;
    public static FileReader loginLocatorsReader;
    public static FileReader dashboardLocatorsReader;
    public static FileReader uploadLocatorsReader;


    //@BeforeClass
    @BeforeMethod
    //@BeforeTest
    public void setUp() throws IOException {
        if(driver==null) {

            configFileReader = new FileReader(System.getProperty("user.dir") + "/src/test/resources/elements/config.properties");
            loginLocatorsReader = new FileReader(System.getProperty("user.dir") + "/src/test/resources/elements/loginLocators.properties");
            dashboardLocatorsReader = new FileReader(System.getProperty("user.dir") + "/src/test/resources/elements/dashboardLocators.properties");
            uploadLocatorsReader = new FileReader(System.getProperty("user.dir") + "/src/test/resources/elements/uploadLocators.properties");

            configLocatorsProp.load(configFileReader);
            loginLocatorsProp.load(loginLocatorsReader);
            dashboardLocatorsProp.load(dashboardLocatorsReader);
            uploadLocatorsProp.load(uploadLocatorsReader);

        }
        
        // Determine browser: Priority: Environment variable > System property > config file
        String browser = System.getenv("BROWSER");
        if (browser == null || browser.isEmpty()) {
            browser = System.getProperty("browser");
        }
        if (browser == null || browser.isEmpty()) {
            browser = configLocatorsProp.getProperty("browser", "chrome");
        }
        browser = browser.toLowerCase();
        
        // Determine if headless mode: Priority: Environment variable > System property
        boolean headless = Boolean.parseBoolean(System.getenv("HEADLESS"));
        if (!headless) {
            headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        }
        
        log.info("Initializing browser: {} (headless: {})", browser, headless);
        
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            
            if (headless) {
                options.addArguments("--headless");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
                log.info("Chrome running in headless mode");
            }

            //driver = new ChromeDriver(options);
            driver.set(new ChromeDriver(options));
            //driver.get(configLocatorsProp.getProperty("url"));
            driver.get().get(configLocatorsProp.getProperty("url"));
            
            if (!headless) {
                //driver.manage().window().maximize();
                driver.get().manage().window().maximize();
            }
        }
        else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            
            if (headless) {
                options.addArguments("--headless");
                options.addArguments("--width=1920");
                options.addArguments("--height=1080");
                log.info("Firefox running in headless mode");
            }
            
            //driver = new FirefoxDriver(options);
            driver.set(new FirefoxDriver(options));
            //driver.get(configLocatorsProp.getProperty("url"));
            driver.get().get(configLocatorsProp.getProperty("url"));
            
            if (!headless) {
                //driver.manage().window().maximize();
                driver.get().manage().window().maximize();
            }
        }
        else {
            throw new IllegalArgumentException("Unsupported browser: " + browser + ". Supported: chrome, firefox");
        }
        
        log.info("Browser initialized successfully: {}", browser);
    }

    public void takeScreenshot(String methodName) throws IOException {

        Date currentDate = new Date();
        String screenShotFilename = currentDate.toString().replace(" ", "-").replace(":", "-");

        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotFile,
                    new File("src/test/java/resources/screenshots/" + methodName + "_" + screenShotFilename + ".png"));
        } catch (IOException e) {
            throw e;
        }
    }

//    @AfterTest
//    //@AfterMethod
//    public void tearDown() {
//        if (driver != null) {
//            driver.close();
//        }
//        log.info("Tear down successful");
//    }

    @AfterSuite
    public void afterSuite() {
        // Ensure ExtentReports is flushed even if listener doesn't fire
        ExtentManager.extentReports.flush();
        log.info("ExtentReports flushed in AfterSuite");
    }

    /**
     * Static method to ensure the logs directory exists BEFORE Log4j2 initializes.
     * This is called from a static initializer block to run before Logger creation.
     * Logs directory is created in project root as 'logs/' for simplicity.
     */
    private static void ensureLogsDirectoryExistsStatic() {
        try {
            // Create logs directory in project root (simpler path)
            File logsDir = new File(System.getProperty("user.dir") + "/logs");
            if (!logsDir.exists()) {
                boolean created = logsDir.mkdirs();
                if (created) {
                    System.out.println("Created logs directory: " + logsDir.getAbsolutePath());
                } else {
                    System.err.println("Failed to create logs directory: " + logsDir.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            System.err.println("Error creating logs directory: " + e.getMessage());
            throw e;
        }
    }
}
