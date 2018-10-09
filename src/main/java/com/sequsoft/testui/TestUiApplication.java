package com.sequsoft.testui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Locale;
import java.util.ResourceBundle;

@SpringBootApplication
public class TestUiApplication extends Application {

    private static EventType LOCALE_CHANGE = new EventType("LOCALE_CHANGE");

    private MenuController menuController;
    private ConfigurableApplicationContext ctx;
    private Parent rootNode;

    Locale currentLocale = null;
    ResourceBundle menusBundle = null;

    @Override
    public void init() throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(TestUiApplication.class);
        ctx = builder.run(getParameters().getRaw().toArray(new String[0]));
        rootNode = new AnchorPane();
    }

    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

        Scene scene = new Scene(rootNode, 640, 480);
        stage.setScene(scene);
        stage.show();

        ObservableList<Node> children = ((AnchorPane)scene.getRoot()).getChildren();

        children.add(l);

        /*menuBar.setUseSystemMenuBar(true);
        menuBar.getMenus().add(file);

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

        menuController = ctx.getBean(MenuController.class);
        children.add(menuController.getMenuBar());
    }

    @Override
    public void stop() throws Exception {
        ctx.close();
    }

    public static void main(String[] args) {
        launch(TestUiApplication.class, args);
    }
}
