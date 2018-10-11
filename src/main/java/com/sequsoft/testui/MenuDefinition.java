package com.sequsoft.testui;

import java.util.List;

public class MenuDefinition {
    private String type;
    private String name;
    private List<MenuDefinition> items;

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

    public List<MenuDefinition> getItems() {
        return items;
    }

    public void setItems(List<MenuDefinition> items) {
        this.items = items;
    }
}
