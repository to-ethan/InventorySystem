package io.github.toethan.inventorysystem.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BasePage {
  @Autowired protected WebDriver driver;

  public abstract void isBeingViewed();
}
