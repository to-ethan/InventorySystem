package io.github.toethan.inventorysystem.endtoend.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

@Component
public class HomePage extends BasePage {
  @Autowired private InventoryAdminPage inventoryAdminPage;

  private By inventoryAdminPageButton = By.xpath("//*[@id=\"inventoryAdmin\"]");

  public InventoryAdminPage goToInventoryAdminPage() {
    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(presenceOfElementLocated(inventoryAdminPageButton)).click();
    return inventoryAdminPage;
  }

  @Override
  public void isBeingViewed() {
    assertEquals("Homepage", driver.getTitle());
  }
}
