package client.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import client.model.SearchInfo;

public class SearchView {
    private ComboBox<String> comboBox;
    private ComboBox<String> skipType;
    private VBox vbox;
    private Button submit;
    private Label nameLabel;
    private Label groupLabel;
    private TextField nameField;
    private TextField groupField;

    private TextField lowAmount;
    private TextField highAmount;

    public SearchView() {
        this(new Button("Search"));
    }

    public SearchView(Button submit) {
        ObservableList<String> recordsCountValues = FXCollections.observableArrayList(
                "Search by Name and Group",
                "By name and type of skip",
                "By name and number of skips");
        vbox = new VBox();
        comboBox = new ComboBox<>(recordsCountValues);
        vbox.getChildren().add(comboBox);
        nameLabel = new Label("Full name");
        groupLabel = new Label("Group");
        nameField = new TextField();
        this.submit = submit;
        groupField = new TextField();
        setNumericProperty(groupField);


        lowAmount = new TextField();
        setNumericProperty(lowAmount);

        highAmount = new TextField();
        setNumericProperty(highAmount);

        ObservableList<String> skipTypes = FXCollections.observableArrayList(
                "illness",
                "without reason",
                "another reason");
        skipType = new ComboBox<>(skipTypes);

        comboBox.setOnAction(actionEvent -> {
            String value = comboBox.getValue();
            System.out.println(value);
            switch (value) {
                case "Search by Name and Group":
                    vbox.getChildren().clear();
                    vbox.getChildren().add(comboBox);
                    vbox.getChildren().add(nameLabel);
                    vbox.getChildren().add(nameField);
                    vbox.getChildren().add(groupLabel);
                    vbox.getChildren().add(groupField);
                    vbox.getChildren().add(submit);
                    break;
                case "By name and type of skip":
                    vbox.getChildren().clear();
                    vbox.getChildren().add(comboBox);
                    vbox.getChildren().add(nameLabel);
                    vbox.getChildren().add(nameField);
                    Label skipLabel = new Label("Type of skip");
                    vbox.getChildren().add(skipLabel);
                    vbox.getChildren().add(skipType);
                    vbox.getChildren().add(submit);
                    break;
                case "By name and number of skips":
                    vbox.getChildren().clear();
                    vbox.getChildren().add(comboBox);
                    vbox.getChildren().add(nameLabel);
                    vbox.getChildren().add(nameField);
                    Label lowAmountLabel = new Label("Low amount");
                    Label highAmountLabel = new Label("High amount");
                    vbox.getChildren().add(lowAmountLabel);
                    vbox.getChildren().add(lowAmount);
                    vbox.getChildren().add(highAmountLabel);
                    vbox.getChildren().add(highAmount);
                    vbox.getChildren().add(submit);
                    break;
            }
        });
    }


    public Node getComboBox() {
        return vbox;
    }


    public void setOnSearch(EventHandler<ActionEvent> eventHandler) {
        submit.setOnAction(eventHandler);
    }


    public SearchInfo getSearchInfo() {
        SearchInfo info = new SearchInfo();
        String groupNumber = groupField.getCharacters().toString();
        if (!groupNumber.isEmpty()) {
            info.group = Integer.parseInt(groupNumber);
        }
        info.name = nameField.getCharacters().toString();
        String charHighAmount = highAmount.getCharacters().toString();
        if (!charHighAmount.isEmpty()) {
            info.highAmountOfSkip = Integer.parseInt(highAmount.getCharacters().toString());
        }
        String charLowAmount = lowAmount.getCharacters().toString();
        if (!charLowAmount.isEmpty()) {
            info.lowAmountOfSkip = Integer.parseInt(lowAmount.getCharacters().toString());
        }
        info.typeOfSkip = skipType.getValue();
        return info;
    }

    private void setNumericProperty(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}
