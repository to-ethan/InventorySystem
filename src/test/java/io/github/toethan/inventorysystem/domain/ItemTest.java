package io.github.toethan.inventorysystem.domain;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class ItemTest {

    @Test
    public void testGetterName() {
        final String name = "New name";
        Item newItem = new Item(name, "");
        Assert.assertEquals(newItem.getName(), name);
    }

    @Test
    public void testGetterDescription() {
        final String description = "This is a sample description.";
        Item newItem = new Item("", description);
        Assert.assertEquals(newItem.getDescription(), description);
    }

    @Test
    public void toStringEqualsName() {
        Item newItem = new Item("name", "");
        Assert.assertEquals(newItem.getName(), newItem.toString());
    }

    @Test
    public void testConstructor() {
        final Constructor<?>[] constructors = Item.class.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            Assert.assertTrue(Modifier.isPublic(constructor.getModifiers()));
        }
    }
}
