package io.github.toethan.inventorysystem.endtoend;

public class WebPage {
    private final String homeUrl;
    private WebPageDriver driver;

    public WebPage(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public void shouldShowHomePageContent() {
        // TODO: Add assertions about home page content.
    }

    public void open() {
        this.driver = new WebPageDriver();
    }

    public void close() {
        if (driver != null) {
            driver.dispose();
        }
    }
}
