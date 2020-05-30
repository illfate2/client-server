package client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import client.view.View;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        View view = new View();
        Controller controller = new Controller(view);
        primaryStage.setTitle("Student list");
        primaryStage.setScene(new Scene(view.getRoot(), 1000, 500));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
