package chat.com.chat;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Main extends Application {

    private Stage primaryStage;
    private ClientChat client = new ClientChat();

    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage primaryStage) throws IOException{
        this.primaryStage = primaryStage;
        afficherPageConnexion();
    }

    public void afficherPageConnexion(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Connexion-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Connexion");
            primaryStage.setScene(scene);
            primaryStage.show();

            // Controleur de cette page (ConnexionController)
            ConnexionController controller = loader.getController();
            controller.setMainApp(this);
            controller.setClient(client); //Partage de la référence du client
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ClientChat getClient() {
        return client;
    }
}
