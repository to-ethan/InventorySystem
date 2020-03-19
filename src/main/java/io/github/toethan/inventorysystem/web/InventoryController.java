package io.github.toethan.inventorysystem.web;

import io.github.toethan.inventorysystem.data.InventoryCreationDto;
import io.github.toethan.inventorysystem.domain.Inventory;
import io.github.toethan.inventorysystem.domain.Item;
import io.github.toethan.inventorysystem.service.InventoryIdNotFoundException;
import io.github.toethan.inventorysystem.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    private final static String INVENTORY_VIEW = "inventory";

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
        return "inventory-update";
    }

    // TODO: update validation on form parameters
    @PostMapping("/update")
    public String updateInventory(Model model, @Valid @ModelAttribute("form") InventoryCreationDto form, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("form", form);
            return "inventory-update";
        }

        form.getInventories().forEach((Inventory inventory) -> {
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
    public String deleteBuyer(@PathVariable Long id){
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
        } catch (InventoryIdNotFoundException e) {
            // TODO: Show error message on UI
            e.printStackTrace();
            return "redirect:/inventory/all";
        }

        return "inventory-edit";
    }

    @PostMapping("/edit/{id}")
    public String editInventory(Model model, @Valid @ModelAttribute("inventoryToEdit") Inventory inventory, Errors errors) {
        if (errors.hasErrors()) {
            System.out.println(errors.toString());
            model.addAttribute("inventoryToEdit", inventory);
            return "inventory-edit";
        }

        try {
            inventoryService.update(inventory);
        } catch (InventoryIdNotFoundException e) {
            // TODO: show error on page
            e.printStackTrace();
            return "inventory-edit";
        }
        model.addAttribute("inventory", inventoryService.all());
        return "redirect:/inventory/all";
    }

    private void updateModel(Model model) {
        model.addAttribute("inventory", inventoryService.all());
        model.addAttribute("newInventory", new Inventory());
    }
}
