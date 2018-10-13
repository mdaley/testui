package com.sequsoft.testui;

import com.sequsoft.testui.menu.MenuController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TestUiApplication extends Application {

    private MenuController menuController;
    private ConfigurableApplicationContext ctx;
    private Parent rootNode;

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
