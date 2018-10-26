package com.sequsoft.testui.settings;

import com.sequsoft.testui.SettingChangedEvent;
import com.sequsoft.testui.menu.MenuItemEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import java.util.Locale;

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
    }

    @Override
    public void onApplicationEvent(MenuItemEvent menuItemEvent) {
        String id = menuItemEvent.getMenuItemId();
        boolean update = false;
        if (id.equals("language-french")) {
            locale = Locale.FRANCE;
            update = true;
        } else if (id.equals("language-english")) {
            locale = Locale.ENGLISH;
            update = true;
        }

        if (update) {
            LOGGER.info("Publishing LOCALE settings changed event");
            publisher.publishEvent(new SettingChangedEvent(this, "LOCALE"));
        }
    }
}
