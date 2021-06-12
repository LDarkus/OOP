package controller.main;

import controller.Utility;
import controller.sport.SportController;
import database.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Sport;
import model.SportTitle;
import model.Sportsman;
import model.table.TableSportsman;

import java.io.IOException;
import java.util.List;

public class EditSportsmanController {

    private static TableSportsman sportsman;
    static Stage stage;


    private static Parent root;


    private List<SportTitle> sportTitles;

    @FXML
    private TextField fullNameField;
    @FXML
    private TextField sportNameField;
    @FXML
    private ComboBox<SportTitle> titleComboBox;
    @FXML
    private TextField coachNameField;
    @FXML
    private TextField phoneNumberField;


    public void initialize() {
        sportTitles = DataBase.getAllSportTitle();
        titleComboBox.getItems().addAll(sportTitles);

        for (SportTitle sportTitle: sportTitles) {
            if (sportsman.getSportTitleId() == sportTitle.getId()) {
                titleComboBox.getSelectionModel().select(sportTitle);
                break;
            }
        }

        fullNameField.setText(sportsman.getFullName());
        sportNameField.setText(sportsman.getSportName());

        coachNameField.setText(sportsman.getCoachName());
        if (!sportsman.getSimplePhoneNumber().isEmpty()) {
            phoneNumberField.setText(sportsman.getSimplePhoneNumber());
        }
    }

    public static TableSportsman showEditWindow (MouseEvent mouseEvent, TableSportsman selectedSportsman) {
        sportsman = selectedSportsman;
        stage=new Stage();
        stage.setResizable(false);
        stage.setTitle("Редактирование");

        try {
            root = FXMLLoader.load(SportController.class.getResource("../../view/editSportsman.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)mouseEvent.getTarget()).getScene().getWindow());
        stage.showAndWait();

        return sportsman;
    }

    public void editHandler(MouseEvent mouseEvent) {
        if (coachNameField.getText().isEmpty()) {
            Utility.showAlertDialog("Заполните все неоходимые поля");
            return;
        }
        sportsman.setSportTitleId(titleComboBox.getSelectionModel().getSelectedItem().getId());
        sportsman.setCoachName(coachNameField.getText());

        if (Utility.isValidPhoneNumber(phoneNumberField.getText())) {
            sportsman.setPhoneNumber(phoneNumberField.getText());
            stage.close();
        } else {
            Utility.showAlertDialog("Неверный формат номера телефона");
        }
    }

    public void cancelHandler(MouseEvent mouseEvent) {
        stage.close();
    }
}
