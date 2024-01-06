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
    private ChatController chatController;

    public static void main(String[] args) {
        launch();
    }



    public void start(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("connexion-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ChatDuTurfu!");
        stage.setScene(scene);
        stage.show();

        ClientChat clientChat = new ClientChat();

        Socket socket = new Socket("localhost", 7777);
        BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter sortie = new PrintWriter(socket.getOutputStream(), true);



        try{
            // contient le code qui instancie un client vers le serveur tout en le lian à l'interface graohique




        }
        catch (Exception e){
            e.printStackTrace();
        }


    }



}
