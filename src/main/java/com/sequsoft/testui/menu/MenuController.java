package com.sequsoft.testui.menu;

import com.sequsoft.testui.SettingsChangedEvent;
import com.sequsoft.testui.settings.Settings;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;

import java.util.ResourceBundle;

public class MenuController implements ApplicationListener<SettingsChangedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    private final MenusDefinition menusDefinition;
    private ApplicationEventPublisher publisher;
    private MenuBar menuBar;
    private Settings settings;
    private ResourceBundle menusBundle;

    public MenuController(ApplicationEventPublisher publisher, Settings settings, MenusDefinition menusDefinition) {
        this.publisher = publisher;
        this.settings = settings;
        this.menusDefinition = menusDefinition;
        menusBundle = ResourceBundle.getBundle("menu/menus", settings.getLocale());
        createMenuBar();
    }

    private void createMenuBar() {

        menuBar = new MenuBar();

        menuBar.setUseSystemMenuBar(true);

        menusDefinition.getMenus().forEach(menuDef -> {
            String menuName = menusBundle.getString(menuDef.getName());
            Menu m = new Menu(menuName);
            m.setId(menuName);

            menuDef.getItems().forEach(i -> createMenu(m, i));

            menuBar.getMenus().add(m);
        });

        Menu file = new Menu("File");
        menuBar.setUseSystemMenuBar(true);
    }

    private void createMenu(Menu menu, MenuDefinition menuDef) {
        String id = menu.getId() + "-" + menuDef.getName();
        String text = menusBundle.getString(id);
        if (menuDef.getType().equals("menu")) {
            Menu m = new Menu(text);
            m.setId(id);
            menu.getItems().add(m);
            menuDef.getItems().forEach(i -> createMenu(m, i));
        } else { // menuitem
            MenuItem m = new MenuItem(text);
            m.setId(id);
            addAccelerator(menuDef, m);
            menu.getItems().add(m);
            m.setOnAction(evt -> {
                MenuItem source = (MenuItem)evt.getSource();
                LOGGER.info("Publishing menu item event [{}].", source.getId());
                publisher.publishEvent(new MenuItemEvent(source));
            });
        }
    }

    private void addAccelerator(MenuDefinition menuDef, MenuItem m) {
        String accelerator = menuDef.getAccelerator();
        if (StringUtils.isNotEmpty(accelerator)) {
            try {
                m.setAccelerator(KeyCombination.valueOf(accelerator));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(String.format("Invalid accelerator '%s' for menu item '%s'.", accelerator, m.getId()), e);
            }
        }
    }

    private void updateMenuBar() {
        menuBar.getMenus().forEach(m -> {
            updateMenu(m);
        });
    }

    private void updateMenu(MenuItem m) {
        m.setText(menusBundle.getString(m.getId()));
        if (m instanceof Menu) {
            ((Menu)m).getItems().forEach(i -> updateMenu(i));
        }
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    @Override
    public void onApplicationEvent(SettingsChangedEvent settingsChangedEvent) {
        menusBundle = ResourceBundle.getBundle("menu/menus", settings.getLocale());
        Platform.runLater(() -> updateMenuBar());
    }
}
