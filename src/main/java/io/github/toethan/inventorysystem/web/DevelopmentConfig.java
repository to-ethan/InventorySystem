package io.github.toethan.inventorysystem.web;

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
                fruits.addItem(new Item("Apple", "A red fruit."));
                fruits.addItem(new Item("Orange", "An orange fruit."));
                fruits.addItem(new Item("Banana", "A yellow fruit."));
                fruits.addItem(new Item("Grape", "A purple fruit."));

                Inventory vegetables = new Inventory("Vegetables");
                vegetables.addItem(new Item("Carrot", "An orange vegetable"));
                vegetables.addItem(new Item("Lettuce", "A green vegetable"));
                vegetables.addItem(new Item("Mushroom", "A brown vegetable"));
                vegetables.addItem(new Item("Onion", "A white vegetable"));

                Inventory protein = new Inventory("Protein");
                protein.addItem(new Item("Beef", "Meat from a cow."));
                protein.addItem(new Item("Chicken", "Meat from a chicken."));
                protein.addItem(new Item("Duck", "Meat from a duck."));
                protein.addItem(new Item("Lamb", "Meat from a lamb."));
                protein.addItem(new Item("Pork", "Meat from a pig."));
                protein.addItem(new Item("Turkey", "Meat from a turkey."));

                Inventory grains = new Inventory("Grains");
                grains.addItem(new Item("Banana bread", "Bread made from bananas."));
                grains.addItem(new Item("Pita bread", "A round flat-bread."));
                grains.addItem(new Item("Rye bread", "Bread made from rye grain."));
                grains.addItem(new Item("White bread", "Bread made from specially-processed wheat flour."));
                grains.addItem(new Item("Wheat bread", "Bread made from wheat flour."));

                inventoryRepository.save(fruits);
                inventoryRepository.save(vegetables);
                inventoryRepository.save(protein);
                inventoryRepository.save(grains);
            }
        };
    }
}
