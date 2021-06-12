package controller.sport;

import controller.Utility;
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
import model.Sport;

import java.io.IOException;

public class AddSportController {

    static Stage stage;
    static Parent root;

    static Sport sport;

    @FXML
    private TextField sectionNameField;

    public static Sport showAddSection(MouseEvent mouseEvent) {
        sport = null;

        stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Добавить");

        try {
            root = FXMLLoader.load(SportController.class.getResource("../../view/addSport.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));

        // Для того чтобы, пока это окно не закроется мы не сможем перейти в начальное окно
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)mouseEvent.getTarget()).getScene().getWindow());

        stage.showAndWait();

        return sport;
    }

    public void closeHandler(MouseEvent mouseEvent) {
        stage.close();
    }

    public void addHandler(MouseEvent mouseEvent) {
        if (sectionNameField.getText().isEmpty()) {
            Utility.showAlertDialog("Заполните все поля!");
        } else {
            int id = DataBase.addSport(sectionNameField.getText());
            System.out.println(id);
            sport = new Sport(id, sectionNameField.getText());
            stage.close();
        }
    }

}
