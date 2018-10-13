package com.sequsoft.testui.menu;

import javafx.scene.control.MenuItem;
import org.springframework.context.ApplicationEvent;

public class MenuItemEvent extends ApplicationEvent {

    public MenuItemEvent(MenuItem source) {
        super(source);
    }

    public String getMenuItemId() {
        return ((MenuItem)getSource()).getId();
    }
}
