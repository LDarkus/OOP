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
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Sport;
import model.SportTitle;
import model.Sportsman;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddSportsmanController {

    private static Sportsman sportsMan;
    private static Stage stage;
    private static Parent root;

    private static int comboBoxIndex;

    private List<Sport> sports;
    private List<SportTitle> sportTitles;

    @FXML
    private TextField fullNameField;
    @FXML
    private TextField coachNameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private ComboBox<Sport> sportComboBox;
    @FXML
    private ComboBox<SportTitle> titleComboBox;

    @FXML
    public void initialize() {
        sports = DataBase.getAllSport();
        sportTitles = DataBase.getAllSportTitle();
        sportComboBox.getItems().addAll(sports);
        sportComboBox.getSelectionModel().select(comboBoxIndex);
        titleComboBox.getItems().addAll(sportTitles);
    }

    public static Sportsman showWindow(MouseEvent event, int sportIndex) {
        comboBoxIndex = sportIndex;
        sportsMan = null;

        stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Добавить");

        try {
            root = FXMLLoader.load(SportController.class.getResource("../../view/addSportsman.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));

        // Для того чтобы, пока это окно не закроется мы не сможем перейти в начальное окно
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getTarget()).getScene().getWindow());

        stage.showAndWait();

        return sportsMan;
    }


    public void addHandler(MouseEvent mouseEvent) {

        SingleSelectionModel<SportTitle> titleComboBoxSelectionModel = titleComboBox.getSelectionModel();
        String nameFieldText = fullNameField.getText();
        String coachNameFieldText = coachNameField.getText();
        String phoneNumberFieldText = phoneNumberField.getText();
        SingleSelectionModel<Sport> sportComboBoxSelectionModel = sportComboBox.getSelectionModel();

        if (sportComboBoxSelectionModel.isEmpty() ||
                titleComboBoxSelectionModel.isEmpty() ||
                nameFieldText.isEmpty() ||
                coachNameFieldText.isEmpty()
        ) {
            Utility.showAlertDialog("Заполните все поля");
            return;
        }

        if (!Utility.isValidPhoneNumber(phoneNumberFieldText)) {
            Utility.showAlertDialog("Неверный формат номера телефона");
            return;
        }
        int id = DataBase.addSportsman(
                nameFieldText,
                sportComboBoxSelectionModel.getSelectedItem().getId(),
                phoneNumberFieldText,
                titleComboBoxSelectionModel.getSelectedItem().getId(),
                coachNameFieldText
        );
        sportsMan = new Sportsman(
                id,
                nameFieldText,
                sportComboBoxSelectionModel.getSelectedItem().getId(),
                phoneNumberFieldText,
                titleComboBoxSelectionModel.getSelectedItem().getId(),
                coachNameFieldText
        );
        stage.close();
    }

    public void cancelHandler(MouseEvent mouseEvent) {
        stage.close();
    }
}
