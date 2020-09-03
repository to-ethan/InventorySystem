package io.github.toethan.inventorysystem.e2e;

import io.github.toethan.inventorysystem.InventorySystemApplication;
import io.github.toethan.inventorysystem.e2e.pages.HomePage;
import io.github.toethan.inventorysystem.e2e.pages.InventoryAdminPage;
import io.github.toethan.inventorysystem.e2e.pages.ShoppingCartPage;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(SpringConfig.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(
    classes = {InventorySystemApplication.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
// @ActiveProfiles("test")
public class InventorySystemEndToEndTests {

  @LocalServerPort private int port;

  @Autowired private HomePage home;

  @Autowired private InventoryAdminPage inventoryAdmin;

  @Autowired private ShoppingCartPage shoppingCart;

  @Autowired private User user;

  @Autowired private WebDriver driver;

  @AfterClass
  public void teardown() {
    if (driver != null) {
      driver.quit();
    }
  }

  @BeforeEach
  public void goToHomePage() {
    navigateToHomePage();
  }

  @Test
  public void userNavigatesToHomePage() {
    user.isCurrentlyOn(home);
  }

  @Test
  public void userNavigatesToInventoryAdminFromHome() {
    user.isCurrentlyOn(home);
    home.goToInventoryAdmin();
    user.isCurrentlyOn(inventoryAdmin);
  }

  @Test
  public void userNavigatesToShoppingCartFromHome() {
    user.isCurrentlyOn(home);
    home.goToShoppingCart();
    user.isCurrentlyOn(shoppingCart);
  }

  // TODO: e2e tests for deleting inventories, creating new inventories, creating new products to an inventory,
  //  deleting products from an inventory, updating products from an inventory.

  private void navigateToHomePage() {
    driver.get("http://localhost:" + port);
  }
}
