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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("connexion-view.fxml")); //charge le fichier page_connexion.fxml
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

    public void afficherPageChat(String pseudo){

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("chat-view.fxml")); //charge le fichier page_connexion.fxml
                Parent root = loader.load();
                Scene scene = new Scene(root);
                primaryStage.setTitle("Chat en tant que " + pseudo );
                primaryStage.setScene(scene);
                primaryStage.show();


                // Controleur de cette page (ConnexionController)
                ChatController controller = loader.getController();

                client.setClientController(controller); //Passage de la référence du controleur au client
                client.setPseudo(pseudo);
                controller.setPseudo(pseudo);
                controller.setClient(client);
                controller.setStage(primaryStage);


                client.launchThread();



            } catch (Exception e) {
                e.printStackTrace();
            }
    }



}
