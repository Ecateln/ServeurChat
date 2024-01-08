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

    @FXML
    private Text messageError;

    private Main mainApp;

    private ServeurChat serveur;
    private ClientChat client;
    private String pseudo;

    public void setServeur(ServeurChat serveur) {
        this.serveur = serveur;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public String getPseudo() {
        if(pseudoInput.getText() == null)
            return "null";
        else
            return pseudoInput.getText();
    }

    @FXML
    protected void login(ActionEvent event) throws IOException {
        System.out.println("Click!");
        this.pseudo = pseudoInput.getText();
        if (pseudo.isEmpty()) return;

        System.out.println("Pseudo choisi: " + pseudo);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/chat-view.fxml"));
            scene = new Scene(fxmlLoader.load());
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            ChatController chatController = fxmlLoader.getController();
            ClientChat client = this.mainApp.getClient();

            chatController.setClient(client);
            chatController.setMainApp(mainApp);
            chatController.setPseudo(pseudo);

            client.setClientController(chatController);
            if(!client.launchThread()) return;

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setClient(ClientChat client) {
        this.client = client;
    }
}

