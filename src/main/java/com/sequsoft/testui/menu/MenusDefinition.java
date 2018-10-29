package com.sequsoft.testui.menu;

import java.util.List;

public class MenusDefinition {
    private List<MenuDefinition> menus;
    private String defaultLocale;

    public List<MenuDefinition> getMenus() {
        return menus;
    }

    @SuppressWarnings("unused")
    public void setMenus(List<MenuDefinition> menus) {
        this.menus = menus;
    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }
}
