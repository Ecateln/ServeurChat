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



    public void sendMessage(Socket socket, String message, PrintWriter sortie, String pseudo) throws IOException {
        System.out.println(pseudo + " : " + message);
        sortie.println(pseudo + " : " + message);
    }

    public void receiveMessage(Socket socket, BufferedReader entree) throws IOException {

        String message = entree.readLine();
        System.out.println(message);
    }

    public void close(Socket socket) throws IOException {
        socket.close();
    }

    public void launchThread() throws IOException {

        Socket socket = new Socket("localhost", 7777);

        BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        sortie = new PrintWriter(socket.getOutputStream(), true);


        Thread lectureMessages = new Thread(() -> {
            try {
                String message;
                while (!socket.isClosed() && (message = entree.readLine()) != null) {
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
    }


    public void setClientController(ChatController controller) {
        this.clientController = controller;
    }

    public void setPseudo(String pseudo) {
        this.clientController.setPseudo(pseudo);
    }


}

