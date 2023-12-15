import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ServeurChat {
    private ServerSocket serverSocket;
    private List<ServerThread> clients;

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
                System.out.println("Nouvelle connexion établie avec " + clientSocket.getInetAddress().getHostName());

                // Création d'un thread pour gérer le client
                ServerThread ServerThread = new ServerThread(this, clientSocket);
                clients.add(ServerThread);
                ServerThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void diffuserMessage(String message, ServerThread expéditeur) {
        for (ServerThread client : clients) {
            if (/*client != expéditeur && */client.getPseudo() != null) {
                String ePseudo = expéditeur.getPseudo();
                client.envoyerMessage(message.startsWith(ePseudo) ? message : ePseudo + ": " + message);
            }
        }
    }

    public void supprimerClient(ServerThread client) {
        client.envoyerMessage("sayonara");
        clients.remove(client);
    }

    public static void main(String[] args) {
        ServeurChat serveur = new ServeurChat(7777);
        serveur.démarrerServeur();
    }
}
