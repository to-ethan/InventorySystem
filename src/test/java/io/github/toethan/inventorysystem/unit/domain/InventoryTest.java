package io.github.toethan.inventorysystem.unit.domain;

import io.github.toethan.inventorysystem.domain.Inventory;
import io.github.toethan.inventorysystem.domain.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InventoryTest {

  private Inventory inventory;

  @BeforeEach
  public void init() {
    this.inventory = new Inventory("name");
  }

  @Test
  public void setAndGetNameTest() {
    assertEquals("name", inventory.getName());
    inventory.setName("test");
    assertEquals("test", inventory.getName());
  }

  @Test
  public void setAndGetItemTest() {
    assertEquals(inventory.getItems().size(), 0);
    List<Item> expected = new ArrayList<Item>();
    expected.add(new Item("1", "2", 1, (double) 1.00));
    expected.add(new Item("3", "4", 1, (double) 1.00));
    inventory.setItems(expected);

    assertEquals(expected, inventory.getItems());
    assertEquals(2, inventory.getItems().size());
  }

  @Test
  public void addItemAndGetItemTest() {
    assertEquals(inventory.getItems().size(), 0);

    List<Item> expected = new ArrayList<Item>();
    Item expectedItem = new Item("1", "2", 1, (double) 1.00);

    expected.add(expectedItem);
    inventory.addItem(expectedItem);

    assertEquals(expected, inventory.getItems());
    assertEquals(1, inventory.getItems().size());
  }

  @Test
  public void addItemsAndGetItemsTest() {
    assertEquals(inventory.getItems().size(), 0);

    List<Item> expected = new ArrayList<Item>();

    expected.add(new Item("1", "2", 1, (double) 1.00));
    expected.add(new Item("3", "4", 1, (double) 1.00));

    inventory.addItems(expected);

    assertEquals(expected, inventory.getItems());
    assertEquals(2, inventory.getItems().size());
  }

  @Test
  public void testConstructor() {
    final Constructor<?>[] constructors = Item.class.getDeclaredConstructors();
    for (Constructor<?> constructor : constructors) {
      assertTrue(Modifier.isPublic(constructor.getModifiers()));
    }
  }

  @Test
  public void deleteItemFromInventoryById() {
    List<Item> expected = new ArrayList<Item>();
    expected.add(new Item(0L,"1", "", 1, (double) 1.00));
    expected.add(new Item(1L,"2", "", 1, (double) 1.00));

    assertEquals(inventory.getItems().size(), 0);
    inventory.addItem(new Item(0L,"1", "", 1, (double) 1.00));
    inventory.addItem(new Item(1L,"2", "", 1, (double) 1.00));
    inventory.addItem(new Item(2L,"3", "", 1, (double) 1.00));

    inventory.deleteItemById(1L);

    assertEquals(expected, inventory.getItems());
    assertEquals(2, inventory.getItems().size());
  }

  @Test
  public void deleteLastItemOfListById() {
    List<Item> expected = new ArrayList<Item>();

    assertEquals(inventory.getItems().size(), 0);
    inventory.addItem(new Item(1L,"2", "", 1, (double) 1.00));

    inventory.deleteItemById(1L);

    assertEquals(expected, inventory.getItems());
    assertEquals(0, inventory.getItems().size());
    assertEquals(true, inventory.getItems().isEmpty());
  }
}
