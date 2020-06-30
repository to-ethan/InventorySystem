package io.github.toethan.inventorysystem.e2e.pages;

import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Component
public class InventoryAdminPage extends BasePage {

  @Override
  public void isBeingViewed() {
    assertEquals("Inventory", driver.getTitle());
  }
}
