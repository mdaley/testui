package com.sequsoft.testui;

import com.sequsoft.testui.menu.MenuItemEvent;
import org.springframework.context.ApplicationListener;

public class MyListener implements ApplicationListener<MenuItemEvent> {

    @Override
    public void onApplicationEvent(MenuItemEvent evt) {
        System.out.println("GOT EVENT " + evt);
    }
}
