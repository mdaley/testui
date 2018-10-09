package com.sequsoft.testui;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.sequsoft.testui.utils.Utils.loadYaml;

@Configuration
public class TestUiConfiguration {

    @Bean
    MenusDefinition menusDefinition() {
        return loadYaml("/menus.yml", MenusDefinition.class);
    }

    @Bean
    MenuController menuController() {
        return new MenuController(menusDefinition());
    }
}
