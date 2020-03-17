package io.github.toethan.inventorysystem.data;

import io.github.toethan.inventorysystem.domain.Inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryCreationDto {
    private List<Inventory> inventories;

    public InventoryCreationDto() {
        inventories = new ArrayList<>();
    }

    public InventoryCreationDto(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    public void addInventory(Inventory inventory) {
        this.inventories.add(inventory);
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }
}