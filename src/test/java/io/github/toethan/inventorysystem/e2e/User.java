package io.github.toethan.inventorysystem.e2e;

import io.github.toethan.inventorysystem.e2e.pages.BasePage;
import org.springframework.stereotype.Component;

@Component
public class User {
  public void isCurrentlyOn(BasePage page) {
    page.isBeingViewed();
  }
}
