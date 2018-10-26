package com.sequsoft.testui;

import org.springframework.context.ApplicationEvent;

public class ValueChangedEvent extends ApplicationEvent {
    public ValueChangedEvent(Object source) {
        super(source);
    }
}
