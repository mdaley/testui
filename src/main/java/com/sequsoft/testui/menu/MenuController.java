package com.sequsoft.testui.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.util.Locale;
import java.util.ResourceBundle;

public class MenuController {

    private final MenusDefinition menusDefinition;
    private MenuBar menuBar;
    private ResourceBundle menusBundle;

    public MenuController(MenusDefinition menusDefinition) {
        this.menusDefinition = menusDefinition;
        createMenuBar();
    }

    private void createMenuBar() {

        menusBundle = ResourceBundle.getBundle("menu/menus", Locale.ENGLISH);

        menuBar = new MenuBar();

        menuBar.setUseSystemMenuBar(true);

        menusDefinition.getMenus().forEach(menuDef -> {
            String menuName = menuDef.getName();
            Menu m = new Menu(menuName);
            m.setId(menuName);

            menuDef.getItems().forEach(i -> createMenu(m, i));

            menuBar.getMenus().add(m);
        });

        Menu file = new Menu("File");
        menuBar.setUseSystemMenuBar(true);
    }

    private void createMenu(Menu menu, MenuDefinition menuDef) {
        if (menuDef.getType().equals("menu")) {
            Menu m = new Menu(menuDef.getName());
            m.setId(menu.getId() + "-" + menuDef.getName());
            menu.getItems().add(m);
            menuDef.getItems().forEach(i -> createMenu(m, i));
        } else { // menuitem
            MenuItem m = new MenuItem(menuDef.getName());
            m.setId(menu.getId() + "-" + menuDef.getName());
            menu.getItems().add(m);
        }
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
