package com.sequsoft.testui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;

@SpringBootApplication
public class TestUiApplication extends Application {

    private ConfigurableApplicationContext context;
    private Parent rootNode;

    Locale currentLocale = null;
    ResourceBundle menusBundle = null;

    private static EventType LOCALE_CHANGE = new EventType("LOCALE_CHANGE");


    @Override
    public void init() throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(TestUiApplication.class);
        context = builder.run(getParameters().getRaw().toArray(new String[0]));
        rootNode = new AnchorPane();
    }

    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        //Scene scene = new Scene(l, 640, 480);
        //AnchorPane anchor = new AnchorPane();
        //Scene scene = new Scene(anchor, 640, 480);
        Scene scene = new Scene(rootNode, 640, 480);
        stage.setScene(scene);
        stage.show();

        currentLocale = Locale.getDefault();
        menusBundle = ResourceBundle.getBundle("menus", Locale.ENGLISH);

        Yaml yaml = new Yaml(new Constructor(MenusDefinition.class));
        InputStream yamlInputStream = this.getClass().getResourceAsStream("/menus.yml");
        MenusDefinition menus = yaml.load(yamlInputStream);

        MenuBar menuBar = new MenuBar();

        menus.getMenus().forEach(menuDef -> {
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

        ObservableList<Node> children = ((AnchorPane)scene.getRoot()).getChildren();

        children.add(l);

        menuBar.setUseSystemMenuBar(true);
        /*menuBar.getMenus().add(file);

        MenuItem one = new MenuItem(menusBundle.getString("FILE_ONE"));
        one.setId("FILE_ONE");
        MenuItem two = new MenuItem(menusBundle.getString("FILE_TWO"));
        two.setId("FILE_TWO");

        file.getItems().add(one);
        file.getItems().add(two);

        menuBar.addEventHandler(LOCALE_CHANGE, x -> {
            menuBar.getMenus().forEach(m -> {
                m.getItems().forEach(i -> {
                    i.setText(menusBundle.getString(i.getId()));
                });
            });
        });

        one.setOnAction(evt -> {
            currentLocale = Locale.ENGLISH;
            menusBundle = ResourceBundle.getBundle("menus", currentLocale);
            Event.fireEvent(menuBar, new Event(LOCALE_CHANGE));
        });

        two.setOnAction(evt -> {
            currentLocale = Locale.FRANCE;
            menusBundle = ResourceBundle.getBundle("menus", currentLocale);
            Event.fireEvent(menuBar, new Event(LOCALE_CHANGE));
        });*/

        children.add(menuBar);
    }

    @Override
    public void stop() throws Exception {
        context.close();
    }

    public static void main(String[] args) {
        launch(TestUiApplication.class, args);
    }
}
