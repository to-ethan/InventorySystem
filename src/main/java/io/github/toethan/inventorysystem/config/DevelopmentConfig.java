package io.github.toethan.inventorysystem.config;

import io.github.toethan.inventorysystem.data.InventoryRepository;
import io.github.toethan.inventorysystem.domain.Inventory;
import io.github.toethan.inventorysystem.domain.Item;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class DevelopmentConfig {
    @Bean
    public CommandLineRunner dataLoader(InventoryRepository inventoryRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Inventory fruits = new Inventory("Fruits");
                fruits.addItem(new Item("Apple", "A red fruit.", 1, (double) 0.99));
                fruits.addItem(new Item("Orange", "An orange fruit.", 1, (double) 1.99));
                fruits.addItem(new Item("Banana", "A yellow fruit.", 1, (double) 0.49));
                fruits.addItem(new Item("Grape", "A purple fruit.", 1, (double) 3.99));

                Inventory vegetables = new Inventory("Vegetables");
                vegetables.addItem(new Item("Carrot", "An orange vegetable", 1, (double) 1.00));
                vegetables.addItem(new Item("Lettuce", "A green vegetable", 1, (double) 1.00));
                vegetables.addItem(new Item("Mushroom", "A brown vegetable", 1, (double) 1.00));
                vegetables.addItem(new Item("Onion", "A white vegetable", 1, (double) 1.00));

                Inventory protein = new Inventory("Protein");
                protein.addItem(new Item("Beef", "Meat from a cow.", 1, (double) 1.00));
                protein.addItem(new Item("Chicken", "Meat from a chicken.", 1, (double) 1.00));
                protein.addItem(new Item("Duck", "Meat from a duck.", 1, (double) 1.00));
                protein.addItem(new Item("Lamb", "Meat from a lamb.", 1, (double) 1.00));
                protein.addItem(new Item("Pork", "Meat from a pig.", 1, (double) 1.00));
                protein.addItem(new Item("Turkey", "Meat from a turkey.", 1, (double) 1.00));

                Inventory grains = new Inventory("Grains");
                grains.addItem(new Item("Banana bread", "Bread made from bananas.", 1, (double) 1.00));
                grains.addItem(new Item("Pita bread", "A round flat-bread.", 1, (double) 1.00));
                grains.addItem(new Item("Rye bread", "Bread made from rye grain.", 1, (double) 1.00));
                grains.addItem(new Item("White bread", "Bread made from specially-processed wheat flour.", 1, (double) 1.00));
                grains.addItem(new Item("Wheat bread", "Bread made from wheat flour.", 1, (double) 1.00));

                inventoryRepository.save(fruits);
                inventoryRepository.save(vegetables);
                inventoryRepository.save(protein);
                inventoryRepository.save(grains);
            }
        };
    }
}
