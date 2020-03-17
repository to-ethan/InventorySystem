package io.github.toethan.inventorysystem.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/inventory/all").setViewName("inventory");
        registry.addViewController("/inventory/update").setViewName("inventory-update");
        registry.addViewController("/shopping-cart").setViewName("shopping-cart");
    }
}
