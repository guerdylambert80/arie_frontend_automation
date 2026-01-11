package resources.custom_exception;

public class StaleElementException extends FrameworkException{
    private final String elementDescription;

    public StaleElementException(String elementDescription, Throwable cause) {
        super(String.format("Element became stale: %s", elementDescription), cause);
        this.elementDescription = elementDescription;
    }
}
