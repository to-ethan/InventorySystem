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
  public static final String INVENTORY_VIEW = "inventory";
  public static final String INVENTORY_EDIT_VIEW = "inventory-edit";
  public static final String INVENTORY_UPDATE_VIEW = "inventory-update";
  public static final String NEW_ITEM = "newItem";

  @Autowired
  private InventoryService inventoryService;

  @GetMapping("/all")
  public String showAll(Model model) {
    updateModel(model);
    return INVENTORY_VIEW;
  }

  @PostMapping("/new")
  public String processForm(Model model, @Valid @ModelAttribute("newInventory") Inventory inventory, Errors errors) {
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
  public String updateInventory(Model model,
      @Valid @ModelAttribute("form") InventoryCreationDto form,
      Errors errors, @RequestParam Map<String, String> allRequestParams) {

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

  private void updateModel(Model model) {
    model.addAttribute("inventory", inventoryService.all());
    model.addAttribute("newInventory", new Inventory());
  }
}
