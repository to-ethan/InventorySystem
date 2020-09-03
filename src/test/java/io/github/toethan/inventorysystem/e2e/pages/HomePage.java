package io.github.toethan.inventorysystem.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

@Component
public class HomePage extends BasePage {
  @Autowired private InventoryAdminPage inventoryAdmin;
  @Autowired private ShoppingCartPage shoppingCart;

  private By inventoryAdminButton = By.xpath("//*[@id=\"inventoryAdmin\"]");
  private By shoppingCartButton = By.xpath("//*[@id=\"shoppingCart\"]");

  public InventoryAdminPage goToInventoryAdmin() {
    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(presenceOfElementLocated(inventoryAdminButton)).click();
    return inventoryAdmin;
  }

  public ShoppingCartPage goToShoppingCart() {
    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(presenceOfElementLocated(shoppingCartButton)).click();
    return shoppingCart;
  }

  @Override
  public void isBeingViewed() {
    assertEquals("Homepage", driver.getTitle());
  }
}
