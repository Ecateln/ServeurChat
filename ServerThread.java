import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
    private ServeurChat serveur;
    private Socket clientSocket;
    private PrintWriter sortie;
    private BufferedReader entrée;
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
            sortie.println("Bienvenue sur le serveur de chat. Veuillez entrer votre pseudo :");

            // Étape 2: Réception du pseudo du client
            this.setPseudo(entrée.readLine());
            System.out.println("Nouvel utilisateur: " + this.getPseudo());
            serveur.diffuserMessage(this.getPseudo() + " a rejoint le salon.", this);

            // Étape 3: Gestion des messages du client
            String message;
            while ((message = entrée.readLine()) != null) {
                if (message.equals("bye")) {
                    break; // Quitte la boucle si le message est "bye"
                }
                serveur.diffuserMessage(message, this);
            }

            // Étape 4: Gestion de la déconnexion du client
            serveur.supprimerClient(this);
            serveur.diffuserMessage(pseudo + " a quitté le salon.", this);
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
