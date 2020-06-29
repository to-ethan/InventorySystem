package io.github.toethan.inventorysystem.endtoend;

import io.github.toethan.inventorysystem.InventorySystemApplication;
import io.github.toethan.inventorysystem.endtoend.pages.HomePage;
import io.github.toethan.inventorysystem.endtoend.pages.InventoryAdminPage;
import org.junit.AfterClass;
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

  @Autowired private HomePage homePage;

  @Autowired private InventoryAdminPage inventoryAdminPage;

  @Autowired private User user;

  @Autowired private WebDriver driver;

  @AfterClass
  public void teardown() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Test
  public void userNavigatesToHomePage() {
    navigateToHomePage();
    user.isCurrentlyOn(homePage);
  }

  @Test
  public void userNavigatesToInventoryAdminPageFromHomePage() {
    navigateToHomePage();
    user.isCurrentlyOn(homePage);
    homePage.goToInventoryAdminPage();
    user.isCurrentlyOn(inventoryAdminPage);
  }

  private void navigateToHomePage() {
    driver.get("http://localhost:" + port);
  }
}
