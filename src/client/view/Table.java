package client.view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import client.model.StudentInfo;

import java.util.ArrayList;


public class Table {
    private TableView<StudentInfoView> tableView;

    public Table() {
        this.tableView = new TableView<StudentInfoView>();
        tableView.setPrefSize(750, 400);
        tableView.setColumnResizePolicy(p -> true);
        TableColumn<StudentInfoView, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.getColumns().add(nameColumn);

        TableColumn<StudentInfoView, Integer> groupColumn = new TableColumn<>("Group");
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        tableView.getColumns().add(groupColumn);

        TableColumn<StudentInfoView, Integer> byIllnessColumn = new TableColumn<>("By Illness");
        byIllnessColumn.setPrefWidth(150);
        byIllnessColumn.setCellValueFactory(new PropertyValueFactory<>("byIllness"));
        tableView.getColumns().add(byIllnessColumn);

        TableColumn<StudentInfoView, Integer> anotherReasonColumn = new TableColumn<>("Another Reason");
        anotherReasonColumn.setCellValueFactory(new PropertyValueFactory<>("anotherReason"));
        anotherReasonColumn.setPrefWidth(150);
        tableView.getColumns().add(anotherReasonColumn);

        TableColumn<StudentInfoView, Integer> withoutReasonColumn = new TableColumn<>("Without Reason");
        withoutReasonColumn.setCellValueFactory(new PropertyValueFactory<>("withoutReason"));
        withoutReasonColumn.setPrefWidth(150);
        tableView.getColumns().add(withoutReasonColumn);

        TableColumn<StudentInfoView, Integer> summaryColumn = new TableColumn<>("Summary");
        summaryColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));
        summaryColumn.setPrefWidth(150);
        tableView.getColumns().add(summaryColumn);
    }

    public Node getTableView() {
        return tableView;
    }

    public void setInfo(ObservableList<StudentInfo> infos) {
        this.tableView.setItems(convertToStudentInfoView(infos));
    }

    private ObservableList<StudentInfoView> convertToStudentInfoView(ObservableList<StudentInfo> infos) {
        ArrayList<StudentInfoView> infoViews = new ArrayList<StudentInfoView>();
        for (var info :
                infos) {
            infoViews.add(new StudentInfoView(info.getName(), info.getGroup(), info.getByIllness(), info.getAnotherReason(), info.getWithoutReason()));
        }
        return FXCollections.observableArrayList(infoViews);
    }
}
