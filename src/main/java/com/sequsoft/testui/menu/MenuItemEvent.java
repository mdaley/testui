package com.sequsoft.testui.menu;

import org.springframework.context.ApplicationEvent;

public class MenuItemEvent extends ApplicationEvent {

    private String menuItemId;

    public MenuItemEvent(Object source, String menuItemId) {
        super(source);
        this.menuItemId = menuItemId;
    }

    public String getMenuItemId() {
        return this.menuItemId;
    }
}
