import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ServeurChat {
    private ServerSocket serverSocket;
    private List<ClientThread> clients;

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
                ClientThread clientThread = new ClientThread(this, clientSocket);
                clients.add(clientThread);
                clientThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void diffuserMessage(String message, ClientThread expéditeur) {
        for (ClientThread client : clients) {
            if (/*client != expéditeur && */client.getPseudo() != null) {
                String ePseudo = expéditeur.getPseudo();
                client.envoyerMessage(message.startsWith(ePseudo) ? message : ePseudo + ": " + message);
            }
        }
    }

    public void supprimerClient(ClientThread client) {
        clients.remove(client);
    }

    public static void main(String[] args) {
        ServeurChat serveur = new ServeurChat(7777);
        serveur.démarrerServeur();
    }
}
