package io.github.toethan.inventorysystem.domain;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class ItemTest {

    @Test
    public void testGetterName() {
        final String name = "New name";
        Item newItem = new Item(name, "", 0, (double) 0);
        Assert.assertEquals(newItem.getName(), name);
    }

    @Test
    public void testGetterDescription() {
        final String description = "This is a sample description.";
        Item newItem = new Item("", description, 0, (double) 0);
        Assert.assertEquals(newItem.getDescription(), description);
    }

    @Test
    public void testGetterQuantity() {
        final int quantity = 350;
        Item newItem = new Item("", "", quantity, (double) 0);
        Assert.assertEquals(newItem.getQuantity(), quantity);
    }

    @Test
    public void testGetterPrice() {
        final Double price = (double) 3.50;
        Item newItem = new Item("", "", 0, price);
        Assert.assertEquals(newItem.getPrice(), price);
    }

    @Test
    public void testConstructor() {
        final Constructor<?>[] constructors = Item.class.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            Assert.assertTrue(Modifier.isPublic(constructor.getModifiers()));
        }
    }

    @Test
    public void testToStringOutput() {
        String expectedString = "$1.55 Apple (1)";
        Item apple = new Item("Apple", "", 1, 1.55);

        Assert.assertEquals(apple.toString(), expectedString);
    }
}
