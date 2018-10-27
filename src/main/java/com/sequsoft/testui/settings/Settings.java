package com.sequsoft.testui.settings;

import com.sequsoft.testui.ValueChangedEvent;
import com.sequsoft.testui.menu.MenuItemEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import java.util.Locale;
import java.util.function.Function;

public class Settings implements ApplicationListener<MenuItemEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Settings.class);
    private final ApplicationEventPublisher publisher;

    private Locale locale;

    public Settings(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
        locale = Locale.UK;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        Function fn = o -> ((Settings)o).getLocale();
        publisher.publishEvent(new ValueChangedEvent(this, "locale", fn));
    }

    @Override
    public void onApplicationEvent(MenuItemEvent menuItemEvent) {
        String id = menuItemEvent.getMenuItemId();
        if (id.equals("language-french")) {
            setLocale(Locale.FRANCE);
        } else if (id.equals("language-english")) {
            setLocale(Locale.ENGLISH);
        }
    }
}
