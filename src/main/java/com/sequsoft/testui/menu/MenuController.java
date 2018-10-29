package com.sequsoft.testui.menu;

import com.sequsoft.testui.ValueChangedEvent;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;

public class MenuController implements ApplicationListener<ValueChangedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    private final MenusDefinition menusDefinition;
    private ConfigurableApplicationContext ctx;
    private MenuBar menuBar;
    private ResourceBundle menusBundle;
    private ResourceBundle baseMenusBundle;

    public MenuController(ConfigurableApplicationContext ctx, MenusDefinition menusDefinition) {
        this.ctx = ctx;
        this.menusDefinition = menusDefinition;
        Locale baseLocale = Locale.forLanguageTag(menusDefinition.getDefaultLocale());
        menusBundle = ResourceBundle.getBundle("menu/menus", baseLocale);
        baseMenusBundle = menusBundle;
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

    private Map<String, BiConsumer<Menu, MenuDefinition>> dispatcher;

    private Map<String, BiConsumer<Menu, MenuDefinition>> dispatcher() {

        if (dispatcher != null) {
            return dispatcher;
        }

        dispatcher = new HashMap<>();
        dispatcher.put("separator", this::addSeparator);
        dispatcher.put("menu", this::addMenu);
        dispatcher.put("checkedmenuitem", this::addCheckedMenuItem);
        dispatcher.put("menuitem", this::addMenuItem);

        return dispatcher;
    }

    private void createMenu(Menu menu, MenuDefinition menuDef) {
        dispatcher().getOrDefault(menuDef.getType(), this::failOnInvalidMenuType).accept(menu, menuDef);
    }

    private void failOnInvalidMenuType(Menu __, MenuDefinition menuDef) {
        throw new MenuConstructionException(String.format("The menu type [%s] is invalid", menuDef.getType()));
    }

    private String getTextOrBase(String id) {
        return menusBundle.containsKey(id) ? menusBundle.getString(id) : baseMenusBundle.getString(id);
    }

    private void addMenuItem(Menu menu, MenuDefinition menuDef) {
        String id = menu.getId() + "-" + menuDef.getName();
        String text = getTextOrBase(id);
        MenuItem m = new MenuItem(text);
        m.setId(id);
        addAccelerator(m);
        menu.getItems().add(m);
        m.setOnAction(evt -> {
            MenuItem source = (MenuItem) evt.getSource();
            LOGGER.info("Publishing menu item event [{}].", source.getId());
            ctx.publishEvent(new MenuItemEvent(source));
        });
    }

    private void addCheckedMenuItem(Menu menu, MenuDefinition menuDef) {
        String id = menu.getId() + "-" + menuDef.getName();
        String text = getTextOrBase(id);
        ListeningCheckMenuItem m = new ListeningCheckMenuItem(ctx, text, menuDef.getValueChangeId());
        m.setId(id);
        addAccelerator(m);
        menu.getItems().add(m);
    }

    private void addMenu(Menu menu, MenuDefinition menuDef) {
        String id = menu.getId() + "-" + menuDef.getName();
        String text = getTextOrBase(id);
        Menu m = new Menu(text);
        m.setId(id);
        menu.getItems().add(m);
        menuDef.getItems().forEach(i -> createMenu(m, i));
    }

    private void addSeparator(Menu menu, MenuDefinition __) {
        SeparatorMenuItem m = new SeparatorMenuItem();
        menu.getItems().add(m);
    }

    private void addAccelerator(MenuItem m) {
        String acceleratorKey = m.getId() + "-accelerator";

        String accelerator = null;

        if (menusBundle.containsKey(acceleratorKey)) {
            accelerator = menusBundle.getString(acceleratorKey);
        } else if (baseMenusBundle.containsKey(acceleratorKey)) {
            accelerator = baseMenusBundle.getString(acceleratorKey);
        }

        if (StringUtils.isNotEmpty(accelerator)) {
            try {
                m.setAccelerator(KeyCombination.valueOf(accelerator));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(String.format("Invalid accelerator '%s' for menu item '%s'.", accelerator, m.getId()), e);
            }
        }

    }

    private void updateMenuBar() {
        menuBar.getMenus().forEach(this::updateMenu);
    }

    private void updateMenu(MenuItem m) {
        if (m.getId() == null) {
            return;
        }

        m.setText(getTextOrBase(m.getId()));
        if (m instanceof Menu) {
            ((Menu)m).getItems().forEach(this::updateMenu);
        }

        addAccelerator(m);
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    @Override
    public void onApplicationEvent(ValueChangedEvent valueChangedEvent) {
        if (valueChangedEvent.getValueChangeId().equals("locale")) {
            menusBundle = ResourceBundle.getBundle("menu/menus", (Locale) valueChangedEvent.retrieveValue());
            Platform.runLater(this::updateMenuBar);
        }
    }
}
