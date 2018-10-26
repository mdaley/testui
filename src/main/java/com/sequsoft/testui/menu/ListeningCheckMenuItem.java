package com.sequsoft.testui.menu;

import com.sequsoft.testui.ChangeValueEvent;
import com.sequsoft.testui.ValueChangedEvent;
import javafx.scene.control.CheckMenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

public class ListeningCheckMenuItem extends CheckMenuItem implements ApplicationListener<ValueChangedEvent> {
    public static final Logger LOGGER = LoggerFactory.getLogger(ListeningCheckMenuItem.class);
    private final String valueChangeId;

    public ListeningCheckMenuItem(ConfigurableApplicationContext ctx, String text, String valueChangeId) {
        super(text);
        this.valueChangeId = valueChangeId;
        ctx.addApplicationListener(this);
        setOnAction(evt -> {
           ctx.publishEvent(new ChangeValueEvent(this, valueChangeId));
        });
    }

    @Override
    public void onApplicationEvent(ValueChangedEvent valueChangedEvent) {
        LOGGER.info("Received setting changed event {}", valueChangedEvent);
    }
}
