package com.sequsoft.testui;

import org.springframework.context.ApplicationEvent;

public class SettingsChangedEvent extends ApplicationEvent {
    private final String type;

    public SettingsChangedEvent(Object source, String type) {
        super(source);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
