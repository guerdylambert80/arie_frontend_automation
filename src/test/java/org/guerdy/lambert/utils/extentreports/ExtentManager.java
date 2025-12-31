package org.guerdy.lambert.utils.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentManager {
    public static final ExtentReports extentReports = new ExtentReports();

    public synchronized static ExtentReports createExtentReports() {
        // Use absolute path to ensure the report is created in the correct location
        String reportPath = System.getProperty("user.dir") + "/src/test/java/resources/reports/ExtentReportResults.html";
        
        // Ensure the reports directory exists
        File reportsDir = new File(System.getProperty("user.dir") + "/src/test/java/resources/reports");
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }
        
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
        reporter.config().setReportName("ARIE Frontend Automation Report");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Author", "Guerdy Lambert");

        return extentReports;
    }
}
