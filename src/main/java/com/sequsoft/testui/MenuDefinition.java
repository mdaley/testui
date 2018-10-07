package com.sequsoft.testui;

import java.util.List;

public class MenuDefinition {
    private String type;
    private String name;
    private List<MenuItemDefinition> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MenuItemDefinition> getItems() {
        return items;
    }

    public void setItems(List<MenuItemDefinition> items) {
        this.items = items;
    }
}
