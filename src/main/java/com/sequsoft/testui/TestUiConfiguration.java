package com.sequsoft.testui;

import com.sequsoft.testui.menu.MenuController;
import com.sequsoft.testui.menu.MenusDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import static com.sequsoft.testui.utils.Utils.loadYaml;

@Configuration
public class TestUiConfiguration {

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster
                = new SimpleApplicationEventMulticaster();

        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return eventMulticaster;
    }

    @Bean
    MyListener myListener() {
        return new MyListener();
    }

    @Bean
    MenusDefinition menusDefinition() {
        return loadYaml("/menu/menus.yml", MenusDefinition.class);
    }

    @Bean
    MenuController menuController() {
        return new MenuController(menusDefinition());
    }
}
