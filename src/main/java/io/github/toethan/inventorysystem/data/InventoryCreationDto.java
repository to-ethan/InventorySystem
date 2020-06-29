package io.github.toethan.inventorysystem.data;

import io.github.toethan.inventorysystem.domain.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryCreationDto {
  private List<Inventory> inventories;

  public InventoryCreationDto() {
    inventories = new ArrayList<>();
  }

  public InventoryCreationDto(List<Inventory> inventories) {
    this.inventories = inventories;
  }

  public List<Inventory> getInventories() {
    return inventories;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof InventoryCreationDto)) return false;
    InventoryCreationDto that = (InventoryCreationDto) o;
    return Objects.equals(inventories, that.inventories);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inventories);
  }
}
