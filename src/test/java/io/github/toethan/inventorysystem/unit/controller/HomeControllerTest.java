package io.github.toethan.inventorysystem.unit.controller;

import io.github.toethan.inventorysystem.web.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(HomeController.class)
public class HomeControllerTest {

  @Autowired private MockMvc mvc;

  @Test
  public void homeControllerPage() throws Exception {
    this.mvc
        .perform(get("/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("home"));
  }
}
