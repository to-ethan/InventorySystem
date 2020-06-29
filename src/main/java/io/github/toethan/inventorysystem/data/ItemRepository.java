package io.github.toethan.inventorysystem.data;

import io.github.toethan.inventorysystem.domain.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {}
