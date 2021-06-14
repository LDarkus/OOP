package controller.main;

import controller.Utility;
import controller.sport.SportController;
import controller.title.TitleController;
import model.DataBase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Sport;
import model.SportTitle;
import model.Sportsman;
import model.TableSportsman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private List<Sportsman> sportsmen;
    private List<SportTitle> sportTitles;
    private List<Sport> sports;

    @FXML
    private MenuItem closeButton;
    @FXML
    private MenuItem showSport;
    @FXML
    private MenuItem showTitle;
    @FXML
    private MenuItem exportToExcel;
    @FXML
    private MenuItem exportToPDF;

    @FXML
    private TableView<TableSportsman> tableView;
    @FXML
    private ComboBox<Object> sportSelector;

    static Stage mainStage;

    @FXML
    void initialize() {
        new DataBase();
        refresh();
        initTable();
    }

    void refresh() {
        sportsmen = DataBase.getAllSportsman();
        sports = DataBase.getAllSport();
        sportTitles = DataBase.getAllSportTitle();

        sportSelector.getItems().clear();
        sportSelector.getItems().add("Все секции");
        sportSelector.getSelectionModel().selectFirst();
        sportSelector.getItems().addAll(sports);
    }

    @FXML
    private TableColumn<TableSportsman, String> fullNameCol;
    @FXML
    private TableColumn<TableSportsman, String> sportCol;
    @FXML
    private TableColumn<TableSportsman, String> titleCol;
    @FXML
    private TableColumn<TableSportsman, String> phoneNumberCol;
    @FXML
    private TableColumn<TableSportsman, String> coachNameCol;

    //method for work with table
    private void initTable() {
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        sportCol.setCellValueFactory(new PropertyValueFactory<>("sportName"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("titleName"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        coachNameCol.setCellValueFactory(new PropertyValueFactory<>("coachName"));

        fillTable();

    }

    private void fillTable() {
        tableView.getItems().clear();
        for (Sportsman sportsman: sportsmen) {
            addSportsman(sportsman);
        }
    }

    public static void showMainWindow(Stage stage) throws IOException {
        mainStage = stage;
        Parent root = FXMLLoader.load(MainController.class.getResource("../../view/home.fxml"));
        stage.setResizable(false);
        stage.setTitle("SportsmanInfo");
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void menuActionHandler(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source == closeButton) {
            Platform.exit();
        }
        if (source == showSport) {
            SportController.showSportWindow(actionEvent);
            refresh();
        }
        if (source == showTitle) {
            TitleController.showSportTitleWindow(actionEvent);
            refresh();
        }
        if (source == exportToExcel) {
            Utility.saveToExcel(tableView.getItems());
        }
        if(source==exportToPDF)
        {
            Utility.SaveToPDF(tableView.getItems());
        }
    }

    public void addSportsmanHandler(MouseEvent event) {
        Sportsman sportsman = AddSportsmanController.showWindow(event, sportSelector.getSelectionModel().getSelectedIndex()-1);
        if (sportsman != null) {
            sportsmen.add(sportsman);
            addSportsman(sportsman);
        }
    }

    private void addSportsman(Sportsman sportsman) {
        TableSportsman tableSportsman = new TableSportsman(sportsman);
        tableSportsman.setTitleName(DataBase.getSportTitleName(sportsman.getSportTitleId()));
        tableSportsman.setSportName(DataBase.getSportName(sportsman.getSportId()));
        tableView.getItems().add(tableSportsman);
    }



    public void updateSportsmanHandler(MouseEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        TableSportsman ts = EditSportsmanController.showEditWindow(event, tableView.getSelectionModel().getSelectedItem());
        DataBase.updateSportsman(ts.getId(),
                ts.getFullName(),
                ts.getSportId(),
                ts.getSimplePhoneNumber(),
                ts.getSportTitleId(),
                ts.getCoachName()
        );
        tableView.getItems().set(tableView.getSelectionModel().getSelectedIndex(), ts);
        refresh();
    }

    public void deleteSportsmanHandler(MouseEvent event) {
        Sportsman selectedSportsman = tableView.getSelectionModel().getSelectedItem();
        if (selectedSportsman != null) {
            DataBase.deleteSportsman(selectedSportsman.getId());
            tableView.getItems().remove(selectedSportsman);
            System.out.println(sportsmen.remove(selectedSportsman));
        }
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public void comboBoxListener(ActionEvent actionEvent) {
        Object selectedItem = sportSelector.getSelectionModel().getSelectedItem();
        sportsmen = DataBase.getAllSportsman();
        if (selectedItem instanceof Sport) {
            Sport selectedSport = (Sport) selectedItem;
            List<Sportsman> sportsmanList = new ArrayList<>();
            for (Sportsman sportsman: sportsmen) {
                if (sportsman.getSportId() == selectedSport.getId()) {
                    sportsmanList.add(sportsman);
                }
            }
            sportsmen = sportsmanList;
        }
        fillTable();
    }

}
