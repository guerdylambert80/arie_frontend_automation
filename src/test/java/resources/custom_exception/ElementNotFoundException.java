package resources.custom_exception;

public class ElementNotFoundException extends FrameworkException {
    private final String elementName;
    private final String locator;

    public ElementNotFoundException(String elementName, String locator, Throwable cause) {
        super(String.format("Element '%s' not found with locator: %s", elementName, locator), cause);
        this.elementName = elementName;
        this.locator = locator;
    }

    public String getElementName() { return elementName; }
    public String getLocator() { return locator; }
}
