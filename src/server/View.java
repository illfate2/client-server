package server;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class View {
    private Group group;
    private Button start;
    private Button stop;
    private TextArea textArea;

    public View() {
        this.group = new Group();
        start = new Button("Start");
        stop = new Button("Stop");
        HBox hbox = new HBox();
        hbox.getChildren().add(start);
        hbox.getChildren().add(stop);
        VBox vbox = new VBox();
        textArea = new TextArea();
        textArea.setDisable(true);

        vbox.getChildren().add(hbox);
        vbox.getChildren().add(textArea);
        this.group.getChildren().add(vbox);
    }

    public void setOnStart(EventHandler<ActionEvent> eventHandler) {
        start.setOnAction(eventHandler);
    }

    public void setOnStop(EventHandler<ActionEvent> eventHandler) {
        stop.setOnAction(eventHandler);
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public Group getGroup() {
        return group;
    }
}

