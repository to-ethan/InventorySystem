package io.github.toethan.inventorysystem.endtoend;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "io.github.toethan.inventorysystem.endtoend")
public class SpringConfig {
    @Bean
    public WebDriver webDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }
}
