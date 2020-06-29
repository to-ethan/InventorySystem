package io.github.toethan.inventorysystem.service;

public class InventoryIdNotFoundException extends Exception {
  public InventoryIdNotFoundException(Long id) {
    super("Unable to find Inventory for id: " + Long.toString(id) + ".");
  }
}
