package io.github.toethan.inventorysystem.endtoend.pages;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BasePage {
    @Autowired
    protected WebDriver driver;

    abstract public void isBeingViewed();
}
