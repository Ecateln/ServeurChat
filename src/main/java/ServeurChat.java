package chat.com.chat;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ServeurChat {
    public Socket socket;
    private ServerSocket serverSocket;
    List<ServerThread> clients;

    public ServeurChat(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clients = new ArrayList<>();
            System.out.println("Serveur en attente de connexions sur le port " + port + "...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void démarrerServeur() {
        if (serverSocket == null) {
            System.out.println("Le serveur n'a pas pu être démarré. Vérifiez les erreurs précédentes.");
            return;
        }
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                // no ip logger for you :( // System.out.println("Nouvelle connexion établie avec " + clientSocket.getInetAddress().getHostName());

                // Création d'un thread pour gérer le client
                ServerThread ServerThread = new ServerThread(this, clientSocket);
                clients.add(ServerThread);
                ServerThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void diffuserMessage(String message) {
        for (ServerThread client : clients) {
            if (/* client != expéditeur && */client.getPseudo() != null) {
                client.envoyerMessage(message);
            }
        }
    }

    public void supprimerClient(ServerThread client) {
        client.envoyerMessage("sayonara");
        clients.remove(client);
    }

    public void diffuserRejoindreSalon(ServerThread expéditeur) {
        diffuserMessage(expéditeur.getPseudo() + " a rejoint le salon!");
    }

    public boolean pseudoUtilisé(String pseudo) throws IOException {
        for (ServerThread client : clients) {
            if (client.getPseudo() != null && client.getPseudo().equals(pseudo)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        ServeurChat serveur = new ServeurChat(7777);
        serveur.démarrerServeur();
    }

}

