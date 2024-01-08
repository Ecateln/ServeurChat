package chat.com.chat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatController {

    @FXML
    private AnchorPane sceneChat;
    @FXML
    private Label welcomeText;
    @FXML
    private TextArea messageArea;
    @FXML
    private TextArea chatArea;
    @FXML
    private Main mainApp;
    @FXML
    private Button envoyerButton;
    @FXML
    private  Button logOutButton;
    @FXML
    private Button changeNameButton;



    Stage stage;

    String pseudo = null;
    ClientChat client;

    @FXML
    protected void onEnvoyerButtonClick() {
        String message = messageArea.getText();
        if (message.isEmpty()) return;

        try {
            System.out.println("Message envoyé : " + message);
            client.sendMessage(message);
            messageArea.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ajouterMessage(String message) {
        chatArea.appendText(message + "\n");
    }

    @FXML
    protected void onLogOutButtonClick(ActionEvent event) throws IOException {
        Alert alerte = new Alert(Alert.AlertType.CONFIRMATION);
        alerte.setTitle("Déconnexion");
        alerte.setHeaderText("Déconnexion");
        alerte.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");

        if(alerte.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            System.out.println("Déconnexion effectuée avec succès.");
            stage.close();
            System.exit(0);
        }
    }

    @FXML
    protected void onChangeNameButtonClick() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Changer de pseudo");
        dialog.setHeaderText("Changer de pseudo");
        dialog.setContentText("Ce bouton ne fait absolument rien pour le moment.\nPour savoir pourquoi, veuillez prendre contact avec nos investisseurs.\nVous pouvez tout de même entrer du texte dans cette magnifique zone dédiée:");

        pseudo = dialog.showAndWait().get();
        // System.out.println("Nouveau pseudo : " + pseudo);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setClient(ClientChat client) {
        this.client = client;
    }

    public String getPseudo() {
        return pseudo;
    }
}


