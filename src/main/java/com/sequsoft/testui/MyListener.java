package com.sequsoft.testui;

import com.sequsoft.testui.menu.MenuItemEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

public class MyListener implements ApplicationListener<MenuItemEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyListener.class);

    @Override
    public void onApplicationEvent(MenuItemEvent evt) {
        LOGGER.info("Got event! {}.",evt);
    }
}
