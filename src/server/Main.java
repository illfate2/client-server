package server;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.model.server.Server;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        View view = new View();
        MyLogger logger = new MyLogger(view.getTextArea());
        primaryStage.setTitle("Server");
        primaryStage.setScene(new Scene(view.getGroup(), 300, 275));
        primaryStage.show();
        Server server = new Server(logger);
        view.setOnStart(actionEvent -> {
            server.start();
        });

        view.setOnStop(actionEvent -> {
            server.stop();
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
