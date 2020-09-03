package io.github.toethan.inventorysystem.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Inventory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Size(min = 1, max = 25, message = "Please enter an inventory name between 1 and 25 characters.")
  private String name;

  @ElementCollection()
  @OneToMany(cascade = CascadeType.ALL)
  private List<Item> items;

  public Inventory() {
    this.name = "";
    items = new ArrayList<>();
  }

  public Inventory(String name) {
    this.name = name;
    items = new ArrayList<>();
  }

  public Inventory(Long id, String name) {
    this.id = id;
    this.name = name;
    items = new ArrayList<>();
  }

  public Inventory(Long id, String name, List<Item> items) {
    this.id = id;
    this.name = name;
    this.items = items;
  }

  public Long getId() {
    return id;
  }

  // TODO: temporary fix for object creation from form - id should not be changed otherwise
  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Item> getItems() {
    return this.items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public void addItems(List<Item> items) {
    items.forEach(this::addItem);
  }

  public void addItem(Item item) {
    items.add(item);
  }

  public void deleteItemById(Long id) {
    items.removeIf(x -> x.getId().equals(id));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Inventory)) return false;
    Inventory inventory = (Inventory) o;
    return Objects.equals(id, inventory.id)
        && Objects.equals(name, inventory.name)
        && Objects.equals(items, inventory.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, items);
  }
}
