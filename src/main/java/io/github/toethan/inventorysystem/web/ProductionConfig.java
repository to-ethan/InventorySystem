package io.github.toethan.inventorysystem.web;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@EnableCaching
@Configuration
public class ProductionConfig {
}
