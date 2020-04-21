package io.github.toethan.inventorysystem.controller;

import io.github.toethan.inventorysystem.domain.Inventory;
import io.github.toethan.inventorysystem.domain.Item;
import io.github.toethan.inventorysystem.service.InventoryService;
import io.github.toethan.inventorysystem.web.InventoryController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private InventoryService service;

    @Test
    public void getRequestForAll() throws Exception {
        Inventory defaultInventory = new Inventory();
        ArrayList<Inventory> defaultInventoryList = new ArrayList<>();
        defaultInventoryList.add(defaultInventory);
        when(service.all()).thenReturn(defaultInventoryList);

        // TODO: Fix high coupling when checking newInventory attributes
        this.mvc.perform(
                get("/inventory/all"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("inventory"))
                    .andExpect(model().attribute("inventory", equalTo(service.all())))
                    .andExpect(model().attribute("newInventory", hasProperty("id", nullValue())))
                    .andExpect(model().attribute("newInventory", hasProperty("name", is(""))))
                    .andExpect(model().attribute("newInventory", hasProperty("items", is(new ArrayList<Item>()))));
    }

}
