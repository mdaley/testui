package com.sequsoft.testui;

import com.sequsoft.testui.menu.MenuController;
import com.sequsoft.testui.menu.MenusDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.sequsoft.testui.utils.Utils.loadYaml;

@Configuration
public class TestUiConfiguration {

    @Bean
    MenusDefinition menusDefinition() {
        return loadYaml("/menu/menus.yml", MenusDefinition.class);
    }

    @Bean
    MenuController menuController() {
        return new MenuController(menusDefinition());
    }
}
