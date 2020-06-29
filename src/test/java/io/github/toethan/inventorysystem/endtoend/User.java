package io.github.toethan.inventorysystem.endtoend;

import io.github.toethan.inventorysystem.endtoend.pages.BasePage;
import org.springframework.stereotype.Component;

@Component
public class User {
  public void isCurrentlyOn(BasePage page) {
    page.isBeingViewed();
  }
}
