package controller.title;

import controller.Utility;
import controller.sport.SportController;
import database.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import model.SportTitle;

import java.io.IOException;

public class AddSportTitleController {

    static Stage stage;
    static Parent root;

    static SportTitle sportTitle;

    @FXML
    private TextField sectionNameField;

    public static SportTitle showAddSportTitle(MouseEvent mouseEvent) {
        sportTitle = null;

        stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Добавить");

        try {
            root = FXMLLoader.load(AddSportTitleController.class.getResource("../../view/addSportTitle.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));

        // Для того чтобы, пока это окно не закроется мы не сможем перейти в начальное окно
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)mouseEvent.getTarget()).getScene().getWindow());

        stage.showAndWait();

        return sportTitle;
    }

    public void closeHandler(MouseEvent mouseEvent) {
        stage.close();
    }

    public void addHandler(MouseEvent mouseEvent) {
        if (sectionNameField.getText().isEmpty()) {
            Utility.showAlertDialog("Заполните все поля!");
        } else {
            int id = DataBase.addSportTitle(sectionNameField.getText());

            sportTitle = new SportTitle(id, sectionNameField.getText());
            stage.close();
        }
    }
}
