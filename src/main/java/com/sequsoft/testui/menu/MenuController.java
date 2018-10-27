package com.sequsoft.testui.menu;

import com.sequsoft.testui.ValueChangedEvent;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class MenuController implements ApplicationListener<ValueChangedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    private final MenusDefinition menusDefinition;
    private ConfigurableApplicationContext ctx;
    private MenuBar menuBar;
    private ResourceBundle menusBundle;

    public MenuController(ConfigurableApplicationContext ctx, Supplier<Locale> initialLocaleSupplier, MenusDefinition menusDefinition) {
        this.ctx = ctx;
        this.menusDefinition = menusDefinition;
        menusBundle = ResourceBundle.getBundle("menu/menus", initialLocaleSupplier.get());
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
        } else if (menuDef.getType().equals("checkedmenuitem")) {
            ListeningCheckMenuItem m = new ListeningCheckMenuItem(ctx, text, menuDef.getValueChangeId());
            m.setId(id);
            addAccelerator(menuDef, m);
            menu.getItems().add(m);
        } else { // menuitem
            MenuItem m = new MenuItem(text);
            m.setId(id);
            addAccelerator(menuDef, m);
            menu.getItems().add(m);
            m.setOnAction(evt -> {
                MenuItem source = (MenuItem)evt.getSource();
                LOGGER.info("Publishing menu item event [{}].", source.getId());
                ctx.publishEvent(new MenuItemEvent(source));
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
    public void onApplicationEvent(ValueChangedEvent valueChangedEvent) {
        if (valueChangedEvent.getValueChangeId().equals("locale")) {
            menusBundle = ResourceBundle.getBundle("menu/menus", (Locale) valueChangedEvent.retrieveValue());
            Platform.runLater(() -> updateMenuBar());
        }
    }
}
