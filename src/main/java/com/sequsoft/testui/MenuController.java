package com.sequsoft.testui;

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

        menusBundle = ResourceBundle.getBundle("menus", Locale.ENGLISH);

        menuBar = new MenuBar();

        menuBar.setUseSystemMenuBar(true);

        menusDefinition.getMenus().forEach(menuDef -> {
            String menuName = menuDef.getName();
            Menu m = new Menu(menuName);
            m.setId(menuName);
            menuDef.getItems().forEach(itemDef -> {
                String itemName = itemDef.getName();
                String itemText = menusBundle.getString(menuName + "-" + itemName);
                MenuItem i = new MenuItem(itemText);
                i.setId(itemName);
                m.getItems().add(i);
            });

            menuBar.getMenus().add(m);
        });

        Menu file = new Menu("File");
        menuBar.setUseSystemMenuBar(true);
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
