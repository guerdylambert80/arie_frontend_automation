package resources.custom_exception;

public class TestFailureException extends FrameworkException {
    private final FailureCategory category;

    public enum FailureCategory {
        BLOCKER, FLAPPY, ENVIRONMENT, TEST_DATA
    }

    public TestFailureException(String message, FailureCategory category) {
        super(message);
        this.category = category;
    }

    public TestFailureException(String message, FailureCategory category, Throwable cause) {
        super(message, cause);
        this.category = category;
    }

    public FailureCategory getCategory() { return category; }
}
