package com.sequsoft.testui.menu;

import java.util.List;

public class MenuDefinition {
    private String type;
    private String name;
    private String valueChangeId;

    private List<MenuDefinition> items;

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    @SuppressWarnings("unused")
    public void setType(String type) {
        this.type = type;
    }

    public List<MenuDefinition> getItems() {
        return items;
    }

    @SuppressWarnings("unused")
    public void setItems(List<MenuDefinition> items) {
        this.items = items;
    }

    public String getValueChangeId() {
        return valueChangeId;
    }

    public void setValueChangeId(String valueChangeId) {
        this.valueChangeId = valueChangeId;
    }
}
