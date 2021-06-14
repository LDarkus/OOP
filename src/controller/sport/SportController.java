package controller.sport;

import controller.Utility;
import controller.main.MainController;
import model.DataBase;
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
import model.Sport;

import java.io.IOException;
import java.util.ArrayList;

public class SportController {
    static Stage stage;

    private static ArrayList<Sport> sports = new ArrayList<>();

    @FXML
    TableColumn<Sport, Integer> idColumn;
    @FXML
    TableColumn<Sport, String> nameColumn;
    @FXML
    TableView<Sport> tableView;


    @FXML
    public void initialize() {
        System.out.println("SportController initialized");
        sports = DataBase.getAllSport();

        initTable();
    }

    private void fillTable(ArrayList<Sport> sports) {
        for (Sport sport : sports) {
            tableView.getItems().add(sport);
        }
    }

    public void addSport(Sport sport) {
        sports.add(sport);
        tableView.getItems().add(sport);
    }

    private void initTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            if(event.getNewValue().isEmpty())
            {
                Utility.showAlertDialog("Введите новое название секции");

            }
            else {
                Sport sport = event.getRowValue();
                System.out.println(sport);
                sports.get(sports.indexOf(sport)).setName(event.getNewValue());
                DataBase.updateSport(sport.getId(), event.getNewValue());

            }
            tableView.getItems().clear();
            fillTable(sports);


        });
       fillTable(sports);
    }

    public static void showSportWindow(ActionEvent actionEvent) {
        Parent root;
        stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Секции");
        System.out.println("До инита");
        try {
            root = FXMLLoader.load(SportController.class.getResource("../../view/sportView.fxml"));
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("Не смог открыть");
            return;
        }
        stage.setScene(new Scene(root));

        // Для того чтобы, пока это окно не закроется мы не сможем перейти в пред. окно
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainController.getMainStage());

        stage.showAndWait();
    }

    public void addHandler(MouseEvent event) {
        Sport sport = AddSportController.showAddSection(event);
        if (sport != null)
            addSport(sport);
    }

    public void deleteHandler(MouseEvent event) {
        Sport selectedSport = tableView.getSelectionModel().getSelectedItem();
        if (selectedSport != null) {
            DataBase.deleteSport(selectedSport.getId());
            tableView.getItems().remove(selectedSport);
            System.out.println(sports.remove(selectedSport));
        }
    }

}
