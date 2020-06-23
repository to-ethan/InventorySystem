package io.github.toethan.inventorysystem.endtoend;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InventorySystemEndToEndTests {
    private final User user = new User();
    private final WebPage webpage = new WebPage("localhost:8080");

    @BeforeEach
    public void startBrowser() {
        user.opens(webpage);
    }

    @AfterEach
    public void closeBrowser() {
        user.closes(webpage);
    }

    @Test
    public void personLoadsHomePage() {
        user.navigatesToHomePage();
        webpage.shouldShowHomePageContent();
    }
}
