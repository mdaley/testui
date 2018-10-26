package com.sequsoft.testui;

import org.springframework.context.ApplicationEvent;

public class ChangeValueEvent extends ApplicationEvent {

    private final String valueChangeId;

    public ChangeValueEvent(Object source, String valueChangeId) {
        super(source);
        this.valueChangeId = valueChangeId;
    }
}
