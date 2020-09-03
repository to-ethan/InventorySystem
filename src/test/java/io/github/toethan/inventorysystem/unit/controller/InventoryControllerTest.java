package io.github.toethan.inventorysystem.unit.controller;

import io.github.toethan.inventorysystem.data.InventoryCreationDto;
import io.github.toethan.inventorysystem.domain.Inventory;
import io.github.toethan.inventorysystem.domain.Item;
import io.github.toethan.inventorysystem.service.InventoryService;
import io.github.toethan.inventorysystem.web.InventoryController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {

  @MockBean
  private InventoryService inventoryServiceMock;

  private MockMvc mockMvc;

  @BeforeEach
  void setup(WebApplicationContext wac) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  void getRequestForAll() throws Exception {
    Item defaultItem = new Item();
    Inventory first = new Inventory(0L, "a", Arrays.asList(defaultItem));
    Inventory second = new Inventory(1L, "b", Arrays.asList(defaultItem));
    List expectedInventory = Arrays.asList(first, second);

    when(inventoryServiceMock.all()).thenReturn(expectedInventory);

    RequestBuilder request = get("/inventory/all");

    this.mockMvc
        .perform(request)
        .andExpect(status().isOk())
        .andExpect(
            content()
                .contentType(
                    new MediaType(
                        MediaType.TEXT_HTML.getType(),
                        MediaType.TEXT_HTML.getSubtype(),
                        Charset.forName("utf8"))))
        .andExpect(model().attribute("inventory", equalTo(expectedInventory)))
        .andExpect(model().attribute("newInventory", is(new Inventory())))
        .andExpect(view().name("inventory"));
  }

  @Test
  void updateInventoryGetRequest() throws Exception {
    Inventory expectedInventory = new Inventory(null, "test", Arrays.asList());
    when(inventoryServiceMock.all()).thenReturn(Arrays.asList(expectedInventory));

    List<Inventory> inventories = inventoryServiceMock.all();
    InventoryCreationDto inventoriesForm = new InventoryCreationDto(inventories);

    RequestBuilder request = get("/inventory/update");

    this.mockMvc
        .perform(request)
        .andExpect(status().isOk())
        .andExpect(
            content()
                .contentType(
                    new MediaType(
                        MediaType.TEXT_HTML.getType(),
                        MediaType.TEXT_HTML.getSubtype(),
                        Charset.forName("utf8"))))
        .andExpect(view().name("inventory-update"))
        .andExpect(model().attribute("form", equalTo(inventoriesForm)));
  }

  @Test
  void postRequestForNew() throws Exception {
    Inventory expectedInventory = new Inventory(null, "test", Arrays.asList());

    when(inventoryServiceMock.all()).thenReturn(Arrays.asList(expectedInventory));

    RequestBuilder request = post("/inventory/new").param("name", "test");

    this.mockMvc
        .perform(request)
        .andExpect(status().isOk())
        .andExpect(
            content()
                .contentType(
                    new MediaType(
                        MediaType.TEXT_HTML.getType(),
                        MediaType.TEXT_HTML.getSubtype(),
                        Charset.forName("utf8"))))
        .andExpect(view().name("inventory"))
        .andExpect(model().attribute("inventory", equalTo(inventoryServiceMock.all())))
        .andExpect(model().attribute("inventory", contains(expectedInventory)))
        .andExpect(model().attribute("newInventory", hasProperty("id", nullValue())))
        .andExpect(model().attribute("newInventory", hasProperty("name", is(""))))
        .andExpect(model().attribute("newInventory", hasProperty("items", empty())));
  }

  @Test
  void updateInventoryPostRequest() throws Exception {
    Inventory expectedInventory = new Inventory(null, "test", Arrays.asList());
    when(inventoryServiceMock.all())
        .thenReturn(Arrays.asList(expectedInventory, expectedInventory, expectedInventory));

    List<Inventory> inventories = inventoryServiceMock.all();
    InventoryCreationDto expectedInventoryCreationDto = new InventoryCreationDto(inventories);

    RequestBuilder request = post("/inventory/update");

    // TODO: Is null value the correct value of "form"?
    this.mockMvc
        .perform(request)
        .andDo(print())
        .andExpect(model().attribute("form", nullValue()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/inventory/all"));
  }

  /*
  @Test
  void getSpecificInventoryNotFound() throws Exception {
      Long id = 51L;
      Inventory expectedInventory = new Inventory(null, "test", Arrays.asList());
      when(inventoryServiceMock.get(id)).thenThrow(InventoryIdNotFoundException.class);

      List<Inventory> inventories = inventoryServiceMock.all();
      InventoryCreationDto inventoriesForm = new InventoryCreationDto(inventories);

      RequestBuilder request = get("/edit/{id}", id);

      // TODO: Why is the URL not redirected?
      this.mockMvc.perform(request)
              .andDo(print())
              .andExpect(status().is4xxClientError())
              .andExpect(redirectedUrl("/inventory/all"));

  }
  */

  // TODO: Temporary helper function to build forms
  private String buildUrlEncodedFormEntity(String... params) {
    if ((params.length % 2) > 0) {
      throw new IllegalArgumentException("Need to give an even number of parameters");
    }
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < params.length; i += 2) {
      if (i > 0) {
        result.append('&');
      }
      try {
        result
            .append(URLEncoder.encode(params[i], StandardCharsets.UTF_8.name()))
            .append('=')
            .append(URLEncoder.encode(params[i + 1], StandardCharsets.UTF_8.name()));
      } catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e);
      }
    }
    return result.toString();
  }
}
