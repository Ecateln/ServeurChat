package chat.com.chat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;


public class ConnexionController {


    private Stage stage;

    private Scene scene;

    private Parent root;

    @FXML
    private TextField pseudoInput;

    @FXML
    private Button submitButton;

    String pseudo;

    @FXML
    private Text messageError;

    private Main mainApp;

    private ServeurChat serveur;


    public void setServeur(ServeurChat serveur) {
        this.serveur = serveur;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }



    public String getPseudo() {
        return pseudoInput.getText();
    }


    public void login(ActionEvent event) {
        pseudo = pseudoInput.getText();

        System.out.println(pseudo);
    }
}
