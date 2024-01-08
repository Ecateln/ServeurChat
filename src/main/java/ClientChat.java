package chat.com.chat;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;



public class ClientChat {

    ChatController clientController;
    PrintWriter sortie;
    BufferedReader entrée;

    ServeurChat serveur;

    public void sendMessage(String message) throws IOException {
        // System.out.println(pseudo + ": " + message);
        this.sortie.println(message);
    }

    public void close(Socket socket) throws IOException {
        socket.close();
    }

    public boolean launchThread() throws IOException {
        Socket socket = new Socket("localhost", 7777);

        BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.sortie = new PrintWriter(socket.getOutputStream(), true);

        this.sortie.println(clientController.getPseudo()); // envoi du pseudo
        if (!entree.readLine().equals("OK")) return false;

        Thread lectureMessages = new Thread(() -> {
            try {
                String message;
                while (!socket.isClosed() && (message = entree.readLine()) != null) {
                    if (message.equals("sayonara")) {
                        socket.close();
                        System.out.println("Déconnexion effectuée avec succès!");
                    } else
                        clientController.ajouterMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        lectureMessages.start();
        return true;
    }

    public void setClientController(ChatController controller) {
        this.clientController = controller;
    }

    public void setPseudo(String pseudo) {
        this.clientController.setPseudo(pseudo);
    }


}

