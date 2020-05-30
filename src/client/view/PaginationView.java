package client.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PaginationView {
    private HBox hBox;
    private Button first;
    private Button last;
    private Button prev;
    private Button next;
    private ComboBox<Integer> comboBox;
    private Integer totalCount;
    private Integer currentIndex;
    private Label pageLabel;
    private Label pageInfoLabel;

    public PaginationView() {

        ObservableList<Integer> recordsCountValues = FXCollections.observableArrayList(5, 10, 15);

        comboBox = new ComboBox<>(recordsCountValues);
        comboBox.setValue(10);

        totalCount = 0;
        currentIndex = 0;

        hBox = new HBox();
        first = new Button("first");
        last = new Button("last");
        prev = new Button("prev");
        next = new Button("next");
        pageLabel = new Label((currentIndex + 1) + "/" + (1));
        pageInfoLabel = new Label(0 + "/" + 0);

        hBox.getChildren().add(pageInfoLabel);
        hBox.getChildren().add(comboBox);
        hBox.getChildren().add(first);
        hBox.getChildren().add(prev);
        hBox.getChildren().add(pageLabel);
        hBox.getChildren().add(next);
        hBox.getChildren().add(last);
    }

    public void setOnComboBox(EventHandler<ActionEvent> eventHandler) {
        comboBox.setOnAction(eventHandler::handle);
    }

    public void setOnFirst(EventHandler<ActionEvent> eventHandler) {
        first.setOnAction(actionEvent -> {
            currentIndex = 0;
            renderLabel();
            eventHandler.handle(actionEvent);
        });
    }

    public void setOnLast(EventHandler<ActionEvent> eventHandler) {
        last.setOnAction(actionEvent -> {
            currentIndex = totalCount - 1;
            renderLabel();
            eventHandler.handle(actionEvent);
        });
    }

    public void setOnPrev(EventHandler<ActionEvent> eventHandler) {
        prev.setOnAction(actionEvent -> {
            currentIndex = (currentIndex == 0) ? 0 : --currentIndex;
            renderLabel();
            eventHandler.handle(actionEvent);
        });
    }

    public void setOnNext(EventHandler<ActionEvent> eventHandler) {
        next.setOnAction(actionEvent -> {
            currentIndex = (currentIndex == (totalCount - 1)) ? currentIndex : ++currentIndex;
            renderLabel();
            eventHandler.handle(actionEvent);
        });
    }

    public Integer getComboBoxSize() {
        return comboBox.getValue();
    }

    public void setPagination(Integer page) {
        totalCount = page;
    }

    public Integer getCurrentPage() {
        return currentIndex;
    }

    public Node getPagination() {
        return hBox;
    }

    public void renderLabel() {
        pageLabel.setText((currentIndex + 1) + "/" + (totalCount == 0 ? 1 : totalCount));
    }

    public void renderInfoLabel(Integer currentPageNumber, Integer totalNumber) {
        pageInfoLabel.setText(currentPageNumber + "/" + totalNumber);
    }
}
