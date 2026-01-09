package org.guerdy.lambert;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This test is disabled because:
 * 1. This is a Selenium automation project that doesn't require Spring Boot context loading
 * 2. The actual tests are in the tests package (LoginTest, DashboardTest, etc.)
 * 3. Spring Boot is included but not actively used for Selenium tests
 */
@SpringBootTest
@Disabled("Selenium automation project - Spring Boot context test not needed")
class ArieFrontendAutomationApplicationTests {

	@Test
	void contextLoads() {
		// Disabled - see class-level @Disabled annotation
	}

}
