package io.github.toethan.inventorysystem.endtoend;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.toethan.inventorysystem.InventorySystemApplication;
import io.github.toethan.inventorysystem.endtoend.pages.HomePage;
import io.github.toethan.inventorysystem.endtoend.pages.InventoryAdminPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@SpringJUnitConfig(SpringConfig.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {InventorySystemApplication.class},  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@ActiveProfiles("test")
public class InventorySystemEndToEndTests {

    @LocalServerPort
    private int port;

    @Autowired
    private HomePage homePage;

    @Autowired
    private InventoryAdminPage inventoryAdminPage;

    @Autowired
    private User user;

    @Autowired
    private WebDriver driver;

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