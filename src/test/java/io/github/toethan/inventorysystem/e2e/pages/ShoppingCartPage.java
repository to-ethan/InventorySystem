package io.github.toethan.inventorysystem.e2e.pages;

import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Component
public class ShoppingCartPage extends BasePage {

    @Override
    public void isBeingViewed() {
        assertEquals("Shopping Cart", driver.getTitle());
    }
}
