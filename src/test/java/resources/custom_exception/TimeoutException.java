package resources.custom_exception;

public class TimeoutException  extends FrameworkException{
    private final int timeoutSeconds;

    public TimeoutException(String message, int timeoutSeconds) {
        super(String.format("%s (Timeout: %d seconds)", message, timeoutSeconds));
        this.timeoutSeconds = timeoutSeconds;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }
}
