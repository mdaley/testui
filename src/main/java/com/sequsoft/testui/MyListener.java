package com.sequsoft.testui;

import com.sequsoft.testui.menu.MenuItemEvent;
import com.sequsoft.testui.settings.Settings;
import com.sequsoft.testui.settings.TickSource;
import javafx.scene.control.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;

public class MyListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyListener.class);
    private final ConfigurableApplicationContext ctx;

    public MyListener(ConfigurableApplicationContext ctx) {
        this.ctx = ctx;
    }

    @EventListener(MenuItemEvent.class)
    public void onApplicationEvent(MenuItemEvent evt) {
        LOGGER.info("Got event! {}.",evt);
        if (((MenuItem)evt.getSource()).getId().equals("view-a")) {
            LOGGER.info("Setting ticked to true directly.");
            TickSource t = (TickSource) ctx.getBean("tickSource");
            t.setTicked(true);
        }
    }
}
