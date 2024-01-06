package chat.com.chat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

public class ClientChat extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String serveurAdresse = "localhost";
        int port = 7777;



        try {

            Parent root = FXMLLoader.load(getClass().getResource("connexion-view.fxml"));
            Scene scene = new Scene(root);

            stage.setTitle("ChatDuTurfu!");
            stage.setScene(scene);
            stage.show();



            try (Socket socket = new Socket(serveurAdresse, port)) {
                String messageAccueil;
                System.out.println("Connexion établie avec le serveur sur le port " + port);

                // Étape 1: Création des flux d'entrée/sortie
                PrintWriter sortie = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader entrée = new BufferedReader(new InputStreamReader(socket.getInputStream()));


                // Étape 2: Réception du message d'accueil
                messageAccueil = entrée.readLine();
                System.out.println(messageAccueil);



                // Étape 3: Envoi du pseudo au serveur
                ConnexionController connexionController = new ConnexionController();
                String pseudo = connexionController.getPseudo();
                sortie.println(pseudo);






                while (true) {
                    messageAccueil = entrée.readLine();
                    System.out.println(messageAccueil);

                    if (messageAccueil.equals("Le pseudo est déjà utilisé. Veuillez choisir un autre pseudo :")) {
                        pseudo = connexionController.getPseudo();
                        sortie.println(pseudo);
                    } else {
                        break;
                    }
                }



                // Étape 4: Gestion des messages du client
                Thread lectureMessages = new Thread(() -> {
                    try {
                        String message;
                        while (!socket.isClosed() && (message = entrée.readLine()) != null) {
                            if (message.equals("sayonara")) {
                                socket.close();
                                System.out.println("Déconnexion effectuée avec succès!");
                            } else
                                System.out.println(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                lectureMessages.start();

                // Étape 5: Envoi des messages au serveur
                String message = "";
                while (!message.equals("exit") && !socket.isClosed()) {
                    sortie.println(message);
                }

            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}

