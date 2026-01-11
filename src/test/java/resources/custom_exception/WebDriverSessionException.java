package resources.custom_exception;

public class WebDriverSessionException extends FrameworkException{
    public WebDriverSessionException(String message, Throwable cause) {
        super(message, cause);
    }
}
