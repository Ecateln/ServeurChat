import java.io.*;
import java.net.*;

public class ClientChat {
    public static void main(String[] args) {
        String serveurAdresse = "localhost";
        int port = 7777;

        try {
            try (Socket socket = new Socket(serveurAdresse, port)) {
                // Étape 1: Création des flux d'entrée/sortie
                PrintWriter sortie = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader entrée = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));

                // Étape 2: Lecture du message d'accueil du serveur
                String messageAccueil = entrée.readLine();
                System.out.println(messageAccueil);

                // Étape 3: Envoi du pseudo au serveur
                String pseudo = clavier.readLine();
                sortie.println(pseudo);

                while (true) {
                    messageAccueil = entrée.readLine();
                    System.out.println(messageAccueil);

                    if (messageAccueil.equals("Le pseudo est déjà utilisé. Veuillez choisir un autre pseudo :")) {
                        pseudo = clavier.readLine();
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
                while (!message.equals("bye") && (message = clavier.readLine()) != null) {
                    sortie.println(message);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
