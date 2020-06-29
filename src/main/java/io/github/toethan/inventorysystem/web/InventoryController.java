package io.github.toethan.inventorysystem.web;

import io.github.toethan.inventorysystem.data.InventoryCreationDto;
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
import java.util.Map;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

  private static final String INVENTORY_VIEW = "inventory";
  private static final String INVENTORY_EDIT_VIEW = "inventory-edit";
  private static final String INVENTORY_UPDATE_VIEW = "inventory-update";
  private static final String NEW_ITEM = "newItem";
  private final InventoryService inventoryService;

  @Autowired
  public InventoryController(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }

  @GetMapping("/all")
  public String showAll(Model model) {
    // TODO: DRY GET and POST methods
    updateModel(model);
    return INVENTORY_VIEW;
  }

  @PostMapping("/new")
  public String processForm(
      Model model, @Valid @ModelAttribute("newInventory") Inventory inventory, Errors errors) {
    if (errors.hasErrors()) {
      model.addAttribute("inventory", inventoryService.all());
      return INVENTORY_VIEW;
    }

    inventoryService.add(inventory);
    updateModel(model);
    return INVENTORY_VIEW;
  }

  @GetMapping("/update")
  public String showUpdateForm(Model model) {
    List<Inventory> inventories = inventoryService.all();
    InventoryCreationDto inventoriesForm = new InventoryCreationDto(inventories);

    model.addAttribute("form", inventoriesForm);
    return INVENTORY_UPDATE_VIEW;
  }

  // TODO: update validation on form parameters
  @PostMapping("/update")
  public String updateInventory(
      Model model,
      @Valid @ModelAttribute("form") InventoryCreationDto form,
      Errors errors,
      @RequestParam Map<String, String> allRequestParams) {
    if (errors.hasErrors()) {
      model.addAttribute("form", form);
      return INVENTORY_UPDATE_VIEW;
    }

    System.out.println(allRequestParams.keySet());
    System.out.println(allRequestParams);
    System.out.println(allRequestParams.values());
    System.out.println(form.toString());

    form.getInventories()
        .forEach(
            (Inventory inventory) -> {
              try {
                inventoryService.update(inventory);
              } catch (InventoryIdNotFoundException e) {
                e.printStackTrace();
              }
            });

    model.addAttribute("inventory", inventoryService.all());
    return "redirect:/inventory/all";
  }

  @PostMapping("/delete/{id}")
  public String deleteBuyer(@PathVariable Long id) {
    inventoryService.delete(id);
    return "redirect:/inventory/all";
  }

  @GetMapping("/edit/{id}")
  public String showInventory(Model model, @PathVariable Long id) {
    try {
      model.addAttribute("inventoryToEdit", inventoryService.get(id));
      System.out.println(inventoryService.get(id).getId());
      System.out.println(inventoryService.get(id).getName());
      System.out.println(inventoryService.get(id).getItems());
    } catch (Exception e) {
      // TODO: Show error message on UI
      e.printStackTrace();
      return "redirect:/inventory/all";
    }
    model.addAttribute(NEW_ITEM, new Item());

    return INVENTORY_EDIT_VIEW;
  }

  @PostMapping("/edit/{id}")
  public String editInventory(
      Model model, @Valid @ModelAttribute("inventoryToEdit") Inventory inventory, Errors errors) {
    if (errors.hasErrors()) {
      System.out.println(errors.toString());
      model.addAttribute("inventoryToEdit", inventory);
      model.addAttribute(NEW_ITEM, new Item());
      return INVENTORY_EDIT_VIEW;
    }

    try {
      inventoryService.update(inventory);
    } catch (InventoryIdNotFoundException e) {
      e.printStackTrace();
      return INVENTORY_EDIT_VIEW;
    }
    model.addAttribute("inventory", inventoryService.all());
    return "redirect:/inventory/all";
  }

  @PostMapping("/edit/{id}/new")
  public String newInventoryItem(
      Model model,
      @PathVariable Long id,
      @Valid @ModelAttribute(NEW_ITEM) Item item,
      Errors errors) {
    if (errors.hasErrors()) {
      // TODO: Add valid parameters to the domain class of Item.
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
    } catch (InventoryIdNotFoundException e) {
      // TODO: show error on page
      e.printStackTrace();
      return INVENTORY_EDIT_VIEW;
    }
    model.addAttribute("inventory", inventoryService.all());
    return "redirect:/inventory/all";
  }

  @PostMapping("/edit/{id}/delete/{itemId}")
  public String deleteInventoryItem(
      Model model,
      @PathVariable Long id,
      @PathVariable Long itemId,
      Errors errors,
      BindingResult results) {
    /*
    if (errors.hasErrors()) {
        return INVENTORY_EDIT_VIEW;
    }
    */
    try {
      // /edit/{id}/delete/{id}
      // TODO: remove item with id matching from inventory.
      Inventory inventory = inventoryService.get(id);
      List<Item> items = inventory.getItems();
      items.removeIf(x -> x.getId().equals(itemId));
      inventoryService.update(inventory);
    } catch (InventoryIdNotFoundException e) {
      // TODO: show error on page
      e.printStackTrace();
      return INVENTORY_EDIT_VIEW;
    }
    model.addAttribute(NEW_ITEM, new Item());
    return INVENTORY_EDIT_VIEW;
  }

  private void updateModel(Model model) {
    model.addAttribute("inventory", inventoryService.all());
    model.addAttribute("newInventory", new Inventory());
  }
}
