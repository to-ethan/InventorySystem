package io.github.toethan.inventorysystem.unit.service;

import io.github.toethan.inventorysystem.data.InventoryRepository;
import io.github.toethan.inventorysystem.domain.Inventory;
import io.github.toethan.inventorysystem.service.InventoryIdNotFoundException;
import io.github.toethan.inventorysystem.service.InventoryService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @Before
    void init() {
        inventoryService = new InventoryService(inventoryRepository);
    }

    @Test
    public void whenAddingInventoryShouldReturnInventory() {
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(new Inventory());

        Inventory addedInventory = inventoryService.add(new Inventory());

        assertEquals(new Inventory(), addedInventory);
    }

    @Test
    public void getAllInventory() {
        when(inventoryRepository.findAll())
                .thenReturn(Arrays.asList(new Inventory(0L, "1"), new Inventory(1L, "2")));

        List<Inventory> inventories = inventoryService.all();

        assertEquals(Arrays.asList(
                        new Inventory(0L, "1"),
                        new Inventory(1L, "2")
        ), inventories);

    }

    @Test
    public void updateValidInventory() throws InventoryIdNotFoundException {
        Long id = 0L;
        Inventory oldInventory = new Inventory(id, "old name");
        Inventory expectedInventory = new Inventory(id, "new name");

        inventoryService = mock(InventoryService.class);

        doReturn(expectedInventory).when(inventoryService).update(any(Inventory.class));

        Inventory updated = inventoryService.update(expectedInventory);

        assertThat(updated).isNotNull();
        assertEquals(expectedInventory.getId(), updated.getId());
        assertEquals(expectedInventory.getName(), updated.getName());
        assertEquals(Collections.emptyList(), updated.getItems());
    }

    @Test
    public void inventoryIdNotFoundException() {
        Long id = 0L;
        Exception exception = assertThrows(InventoryIdNotFoundException.class, () ->
                inventoryService.get(id));
    }

    @Test
    public void whenDeletingInventoryShouldRemoveInventory() throws InventoryIdNotFoundException {
        Long id = 0L;
        Inventory inventory = new Inventory(id, "test");

        inventoryService.add(inventory);

        inventoryService.delete(id);

        verify(inventoryRepository, times(1)).deleteById(id);
        assertEquals(Collections.emptyList(), inventoryService.all());
    }
}
