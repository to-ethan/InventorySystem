package io.github.toethan.inventorysystem.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

@Entity
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String description;

  private int quantity;

  private Double price;

  public Item(Long id, String name, String description, int quantity, Double price) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.quantity = quantity;
    this.price = price;
  }

  public Item(String name, String description, int quantity, Double price) {
    this.name = name;
    this.description = description;
    this.quantity = quantity;
    this.price = price;
  }

  public Item() {
    this.name = "";
    this.description = "";
    this.quantity = 0;
    this.price = (double) 0;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Item)) return false;
    Item item = (Item) o;
    return Objects.equals(name, item.name) && Objects.equals(description, item.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description);
  }

  @Override
  public String toString() {
    return NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(this.price)
        + " "
        + this.name
        + " ("
        + this.quantity
        + ")";
  }
}
