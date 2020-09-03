package io.github.toethan.inventorysystem.web;

import io.github.toethan.inventorysystem.domain.Inventory;
import io.github.toethan.inventorysystem.domain.Item;
import io.github.toethan.inventorysystem.service.InventoryIdNotFoundException;
import io.github.toethan.inventorysystem.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static io.github.toethan.inventorysystem.web.InventoryController.INVENTORY_EDIT_VIEW;
import static io.github.toethan.inventorysystem.web.InventoryController.NEW_ITEM;

@Controller
@RequestMapping("/inventory/edit")
public class InventoryEditController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("{id}")
    public String showInventory(Model model, @PathVariable Long id) {
        try {
            model.addAttribute("inventoryToEdit", inventoryService.get(id));
            model.addAttribute(NEW_ITEM, new Item());
            return INVENTORY_EDIT_VIEW;
        } catch (InventoryIdNotFoundException e) {
            // TODO: Show error message on UI
            e.printStackTrace();
            return "redirect:/inventory/all";
        }
    }

    // TODO: Fix issue where all items are deleted except first item in list.
    @PostMapping("{id}")
    public String updateInventory(Model model, @PathVariable Long id,
                                  @Valid @ModelAttribute("inventoryToEdit") Inventory inventory, Errors errors) {
        if (errors.hasErrors()) {
            System.out.println(errors.toString());
            model.addAttribute("inventoryToEdit", inventory);
            model.addAttribute(NEW_ITEM, new Item());
            return INVENTORY_EDIT_VIEW;
        }

        try {
            inventoryService.update(inventory);
            model.addAttribute("inventory", inventoryService.all());
            return "redirect:/inventory/edit/" + id;
        } catch (InventoryIdNotFoundException e) {
            e.printStackTrace();
            return INVENTORY_EDIT_VIEW;
        }
    }

    @PostMapping("{id}/new")
    public String newItem(Model model, @PathVariable Long id,
                          @Valid @ModelAttribute(NEW_ITEM) Item item, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute(NEW_ITEM, item);
            return INVENTORY_EDIT_VIEW;
        }

        try {
            Inventory inventory = inventoryService.get(id);
            // Temporary fix because model attribute NEW_ITEM does not generate the correct ID.
            Item newItem =
                    new Item(item.getName(), item.getDescription(), item.getQuantity(), item.getPrice());
            inventory.getItems().add(newItem);
            inventoryService.update(inventory);
            model.addAttribute("inventory", inventoryService.all());
            return "redirect:/inventory/edit/" + id;
        } catch (InventoryIdNotFoundException e) {
            // TODO: show error on page
            e.printStackTrace();
            return INVENTORY_EDIT_VIEW;
        }
    }

    @PostMapping("{id}/delete/{itemId}")
    public String deleteItem(Model model, @PathVariable Long id, @PathVariable Long itemId) {
        try {
            Inventory inventory = inventoryService.get(id);
            inventory.deleteItemById(itemId);
            inventoryService.update(inventory);
            model.addAttribute(NEW_ITEM, new Item());

            // TODO: Fix issue where last item of list cant be deleted.
            if (inventory.getItems().isEmpty()) {
                model.addAttribute("inventory", inventoryService.all());
                return "inventory/all";
            } else {
                return "redirect:/inventory/edit/" + id;
            }
        } catch (InventoryIdNotFoundException e) {
            // TODO: show error on page
            e.printStackTrace();
            return INVENTORY_EDIT_VIEW;
        }
    }
}
