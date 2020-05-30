package client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import client.model.*;
import client.view.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private View view;
    private Model model;

    public Controller(View view) {
        this.view = view;
        model = new Model();
        view.getOptionView().setOnAddButton(actionEvent -> {
            InfoView infoView = new InfoView();
            Stage stage = new Stage();
            Scene scene = new Scene((Parent) infoView.getNode(), 250, 250);
            stage.setScene(scene);
            stage.show();
            infoView.setOnSubmit(actionEvent1 -> {
                stage.close();
                StudentInfo info = infoView.getStudentInfo();
                try {
                    model.addStudentInfo(info);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    renderTable();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        view.getOptionView().setOnDeleteButton(actionEvent -> {
            Stage stage = new Stage();
            VBox vbox = new VBox();
            DeleteInfoView searchView = new DeleteInfoView();
            vbox.getChildren().add(searchView.getView().getComboBox());
            Scene scene = new Scene(vbox, 700, 400);
            Label label = new Label("Deleted: -");
            vbox.getChildren().add(label);
            stage.setScene(scene);
            stage.show();
            searchView.getView().setOnSearch(actionEvent1 -> {
                SearchInfo info = searchView.getView().getSearchInfo();
                int counter = 0;
                if (info.group != null) {
                    try {
                        counter = model.removeByNameAndGroup(info.name, info.group);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (info.lowAmountOfSkip != null && info.highAmountOfSkip != null) {
                    try {
                        counter = model.removeByNameLowAndHighSkip(info.name, info.lowAmountOfSkip, info.highAmountOfSkip);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (!info.typeOfSkip.isEmpty()) {
                    try {
                        counter = model.removeByNameAndTypeOfSkip(info.name, info.typeOfSkip);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                label.setText("Deleted: " + counter);
                try {
                    renderTable();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        view.getOptionView().setOnFindButton(actionEvent -> {
            TableWithPagination tableWithPagination = new TableWithPagination();
            Stage stage = new Stage();
            HBox hbox = new HBox();
            SearchView searchView = new SearchView();
            hbox.getChildren().add(searchView.getComboBox());
            hbox.getChildren().add(tableWithPagination.getHBox());
            Scene scene = new Scene(hbox, 700, 400);
            stage.setScene(scene);
            stage.show();
            searchView.setOnSearch(actionEvent1 -> {
                SearchInfo info = searchView.getSearchInfo();
                List<StudentInfo> result = new ArrayList<>();
                if (info.group != null) {
                    try {
                        result = model.searchByNameAndGroup(info.name, info.group);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (info.lowAmountOfSkip != null && info.highAmountOfSkip != null) {
                    try {
                        result = model.searchByNameHighAndLowAmountOfSkip(info.name, info.lowAmountOfSkip, info.highAmountOfSkip);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (!info.typeOfSkip.isEmpty()) {
                    try {
                        result = model.searchByNameAndTypeOfSkip(info.name, info.typeOfSkip);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                renderSpecificTable(result, tableWithPagination);

            });
        });

        view.getOptionView().setOnOpenFile(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("XML", "*.xml")
            );
            File selectedFile = fileChooser.showOpenDialog(null);
            try {
                var info = getTableDataFromFile(selectedFile);
                model.setStudentInfoViews(info, "");
                renderTable();
            } catch (ParserConfigurationException | SAXException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        view.getTableWithPagination().setRender(actionEvent -> {
            try {
                renderTable();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    public ArrayList<StudentInfo> getTableDataFromFile(File file) throws
            ParserConfigurationException, SAXException,
            IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        SAXParser saxParser = new SAXParser();
        var p = factory.newSAXParser();
        p.parse(file, saxParser);
        return saxParser.getStudents();
    }

    private void renderTable() throws IOException, InterruptedException {
        Integer size = view.getTableWithPagination().getPaginationView().getComboBoxSize();
        Integer currentPage = view.getTableWithPagination().getPaginationView().getCurrentPage();
        List<StudentInfo> studentList = model.getStudentInfos(size, size * currentPage);
        Integer pageNumber = roundUp(model.getStudentInfoViewsSize(), size);
        view.getTableWithPagination().getPaginationView().setPagination(pageNumber);
        view.getTableWithPagination().setInfo(FXCollections.observableArrayList(studentList));
        view.getTableWithPagination().getPaginationView().renderLabel();
        view.getTableWithPagination().getPaginationView().renderInfoLabel(studentList.size(), model.getStudentInfoViewsSize());
    }

    private void renderSpecificTable(List<StudentInfo> studentInfoViews, TableWithPagination
            tableWithPagination) {
        Integer size = tableWithPagination.getPaginationView().getComboBoxSize();
        Integer currentPage = tableWithPagination.getPaginationView().getCurrentPage();
        Integer pageNumber = roundUp(studentInfoViews.size(), size);
        tableWithPagination.getPaginationView().setPagination(pageNumber);
        ObservableList<StudentInfo> studentList = FXCollections.observableArrayList(studentInfoViews);
        tableWithPagination.setInfo(studentList);
        tableWithPagination.getPaginationView().renderLabel();
        tableWithPagination.getPaginationView().renderInfoLabel(studentList.size(), studentInfoViews.size());
    }

    private static Integer roundUp(Integer num, Integer divisor) {
        return (num + divisor - 1) / divisor;
    }
}
