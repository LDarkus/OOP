package controller.title;

import controller.main.MainController;
import controller.sport.SportController;
import database.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.SportTitle;

import java.io.IOException;
import java.util.ArrayList;

public class TitleController {

    static Stage stage;

    private static ArrayList<SportTitle> sportTitles = new ArrayList<>();

    @FXML
    TableColumn<SportTitle, Integer> idColumn;
    @FXML
    TableColumn<SportTitle, String> nameColumn;
    @FXML
    TableView<SportTitle> tableView;


    @FXML
    public void initialize() {
        sportTitles = DataBase.getAllSportTitle();
        initTable();
    }

    private void fillTable(ArrayList<SportTitle> sportTitles) {
        for (SportTitle sportTitle : sportTitles) {
            tableView.getItems().add(sportTitle);
        }
    }

    public void addSportTitle(SportTitle sportTitle) {
        sportTitles.add(sportTitle);
        tableView.getItems().add(sportTitle);
    }

    private void initTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            SportTitle sportTitle = event.getRowValue();
            System.out.println(sportTitle);
            sportTitles.get(sportTitles.indexOf(sportTitle)).setName(event.getNewValue());
            DataBase.updateSportTitle(sportTitle.getId(), event.getNewValue());
        });
        fillTable(sportTitles);
    }

    public static void showSportTitleWindow(ActionEvent actionEvent) {
        Parent root;
        stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Спортивные разряды");
        try {
            root = FXMLLoader.load(SportController.class.getResource("../../view/sportTitleView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("sportTitleView.fxml not loaded");
            return;
        }
        stage.setScene(new Scene(root));

        // Для того чтобы, пока это окно не закроется мы не сможем перейти в пред. окно
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainController.getMainStage());

        stage.showAndWait();
    }

    public void addHandler(MouseEvent event) {
        SportTitle sportTitle = AddSportTitleController.showAddSportTitle(event);
        if (sportTitle != null)
            addSportTitle(sportTitle);
    }

    public void deleteHandler(MouseEvent event) {
        SportTitle selectedSportTitle = tableView.getSelectionModel().getSelectedItem();
        if (selectedSportTitle != null) {
            DataBase.deleteSportTitle(selectedSportTitle.getId());
            tableView.getItems().remove(selectedSportTitle);
            System.out.println(sportTitles.remove(selectedSportTitle));
        }
    }

}
