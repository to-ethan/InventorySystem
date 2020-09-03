package io.github.toethan.inventorysystem.service;

import io.github.toethan.inventorysystem.data.InventoryRepository;
import io.github.toethan.inventorysystem.domain.Inventory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

  private final InventoryRepository repository;

  public InventoryService(InventoryRepository repository) {
    this.repository = repository;
  }

  public List<Inventory> all() {
    return (List<Inventory>) repository.findAll();
  }

  public Inventory get(Long id) throws InventoryIdNotFoundException {
    return repository.findById(id).orElseThrow(() -> new InventoryIdNotFoundException(id));
  }

  public Inventory add(Inventory inventory) {
    return repository.save(inventory);
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }

  public Inventory update(Inventory updated) throws InventoryIdNotFoundException {
    Inventory inventory = get(updated.getId());
    inventory.setName(updated.getName());
    inventory.setItems(updated.getItems());
    return repository.save(inventory);
  }
}
