package client.view;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import client.TableWithPagination;

public class View {
    private Group root;
    private OptionView optionView;
    private TableWithPagination tableWithPagination;

    public View() {
        root = new Group();
        VBox vbox = new VBox();
        optionView = new OptionView();
        tableWithPagination = new TableWithPagination(new Table(), new PaginationView());
        StackPane pane = new StackPane();
        pane.setPadding(new Insets(5));
        pane.getChildren().add(tableWithPagination.getHBox());

        vbox.getChildren().add(optionView.getHBox());
        vbox.getChildren().add(pane);
        root.getChildren().add(vbox);
    }


    public OptionView getOptionView() {
        return optionView;
    }

    public Group getRoot() {
        return root;
    }

    public TableWithPagination getTableWithPagination() {
        return tableWithPagination;
    }
}
