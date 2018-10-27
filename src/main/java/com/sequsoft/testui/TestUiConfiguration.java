package com.sequsoft.testui;

import com.sequsoft.testui.menu.MenuController;
import com.sequsoft.testui.menu.MenusDefinition;
import com.sequsoft.testui.settings.Settings;
import com.sequsoft.testui.settings.TickSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import static com.sequsoft.testui.utils.Utils.loadYaml;

@Configuration
public class TestUiConfiguration {

    @Autowired
    ConfigurableApplicationContext ctx;

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return eventMulticaster;
    }

    @Bean
    MyListener myListener() {
        return new MyListener(ctx);
    }

    @Bean
    TickSource tickSource() {return new TickSource(ctx); }

    @Bean
    Settings settings() {
        return new Settings(ctx);
    }

    @Bean
    MenusDefinition menusDefinition() {
        return loadYaml("/menu/menus.yml", MenusDefinition.class);
    }

    @Bean
    MenuController menuController() {
        return new MenuController(ctx, settings(), menusDefinition());
    }
}
