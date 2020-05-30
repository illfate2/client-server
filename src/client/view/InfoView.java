package client.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import client.model.StudentInfo;

public class InfoView {
    private Button submit;
    private Node node;
    private TextField group;
    private TextField nameField;
    private TextField byIllness;
    private TextField anotherReason;
    private TextField withoutReason;


    public InfoView() {
        Text nameText = new Text("Full name");
        nameField = new TextField();

        Text groupText = new Text("Group");
        group = new TextField();
        setNumericProperty(group);

        Text illnessText = new Text("By illness");
        byIllness = new TextField();
        setNumericProperty(byIllness);

        Text anotherReasonText = new Text("Another reason");
        anotherReason = new TextField();
        setNumericProperty(anotherReason);

        Text withoutReasonText = new Text("Without reason");
        withoutReason = new TextField();
        setNumericProperty(withoutReason);

        submit = new Button("Submit");

        VBox vbox = new VBox();
        vbox.getChildren().add(nameText);
        vbox.getChildren().add(nameField);
        vbox.getChildren().add(groupText);
        vbox.getChildren().add(group);
        vbox.getChildren().add(illnessText);
        vbox.getChildren().add(byIllness);
        vbox.getChildren().add(anotherReasonText);
        vbox.getChildren().add(anotherReason);
        vbox.getChildren().add(withoutReasonText);
        vbox.getChildren().add(withoutReason);
        vbox.getChildren().add(submit);
        node = vbox;
    }

    public Node getNode() {
        return node;
    }

    public void setOnSubmit(EventHandler<ActionEvent> eventHandler) {
        submit.setOnAction(eventHandler);
    }

    public StudentInfo getStudentInfo() {
        return new StudentInfo(nameField.getCharacters().toString(),
                Integer.parseInt(group.getCharacters().toString()),
                Integer.parseInt(byIllness.getCharacters().toString()),
                Integer.parseInt(anotherReason.getCharacters().toString()),
                Integer.parseInt(withoutReason.getCharacters().toString()));
    }

    private void setNumericProperty(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}
