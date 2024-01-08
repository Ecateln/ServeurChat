package chat.com.chat;

import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
    private ServeurChat serveur;
    private Socket clientSocket;
    private PrintWriter sortie;
    public BufferedReader entrée;
    private String pseudo = null;

    public ServerThread(ServeurChat serveur, Socket clientSocket) {
        this.serveur = serveur;
        this.clientSocket = clientSocket;
        try {
            sortie = new PrintWriter(clientSocket.getOutputStream(), true);
            entrée = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String p) {
        this.pseudo = p;
    }

    public void run() {
        try {
            // Étape 1: Envoi du message d'accueil au client
            // sortie.println("Bienvenue sur le serveur de chat. En attente du pseudo...");

            // Étape 2: Réception du pseudo du client
            String enteredPseudo = entrée.readLine();
            while (serveur.pseudoUtilisé(enteredPseudo)) {
                sortie.println("Le pseudo est déjà utilisé. Veuillez choisir un autre pseudo :");
                enteredPseudo = entrée.readLine();
            }

            if (enteredPseudo == null) return;

            sortie.println("OK");
            this.setPseudo(enteredPseudo);

            System.out.println("Nouvel utilisateur: " + this.getPseudo());
            serveur.diffuserRejoindreSalon(this);

            // Étape 3: Gestion des messages du client
            String message;
            while ((message = entrée.readLine()) != null) {
                if (message.equals("bye")) {
                    break; // Quitte la boucle si le message est "bye"
                }

                message = message.trim();
                if (message.isEmpty()) continue;
                serveur.diffuserMessage(this.getPseudo() + ": " + message);
            }

            // Étape 4: Gestion de la déconnexion du client
            serveur.supprimerClient(this);
            serveur.diffuserMessage(pseudo + " a quitté le salon.");
            System.out.println(pseudo + " a quitté le salon.");

            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void envoyerMessage(String message) {
        sortie.println(message);
    }
}
