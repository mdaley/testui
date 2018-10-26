package com.sequsoft.testui;

import org.springframework.context.ApplicationEvent;

public class SettingChangedEvent extends ApplicationEvent {
    private final String type;

    public SettingChangedEvent(Object source, String type) {
        super(source);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
