package chat.com.chat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatController {

    @FXML
    private AnchorPane sceneChat;
    @FXML
    private Label welcomeText;
    @FXML
    private TextField messageInput;
    @FXML
    private Main mainApp;
    @FXML
    private Button envoyerButton;
    @FXML
    private  Button logOutButton;
    @FXML
    private Button changeNameButton;

    private ServeurChat serveur;

    Stage stage;

    private Socket clientSocket;
    private PrintWriter sortie;
    public BufferedReader entrée;
    private String pseudo = null;

    @FXML
    protected void onEnvoyerButtonClick() {

        

        String message = messageInput.getText();
    }
    @FXML
    public  void onLogOutButtonClick(ActionEvent event) {

            Alert alerte = new Alert(Alert.AlertType.CONFIRMATION);
            alerte.setTitle("Déconnexion");
            alerte.setHeaderText("Déconnexion");
            alerte.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");

            if(alerte.showAndWait().get() == ButtonType.OK) {
                Stage stage = (Stage) logOutButton.getScene().getWindow();
                System.out.println("Tu as bien été déconnecté");
                stage.close();
            }
            else {
                System.out.println("Tu n'as pas été déconnecté");
            }

    }

    @FXML
    protected void onChangeNameButtonClick() {



    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }


    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
        welcomeText.setText("Bienvenue " + pseudo + " !");

    }

    public void setServeur(ServeurChat serveur) {
        this.serveur = serveur;

    }
}


