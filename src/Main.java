import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HashMap<String, User> users = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("__________________GreenPulse__________________");
            System.out.println("**********************************************\n");
            System.out.println("Calcul de la consommation de carbone");
            System.out.println("Votre choix ?");
            System.out.print(
                    "    -> 1. Saisir vos informations \n" +
                            "    -> 2. Afficher les infos utilisateurs\n" +
                            "    -> 3. Saisir les données de carbone \n" +
                            "    -> 4. Analyse de la consommation\n" +
                            "    -> 0. Quitter\n");
            System.out.println("______________________________________________");
            System.out.println("**********************************************");
            System.out.print("Choix ? : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choix) {
                case 1:
                    System.out.print("Entrez votre CIN : ");
                    String CIN = scanner.nextLine();
                    if (!users.containsKey(CIN)) {
                        User user = new User(CIN);
                        user.addInfo();
                        users.put(CIN, user);
                        System.out.println("Utilisateur ajouté avec succès!");
                    } else {
                        System.out.println("Utilisateur avec ce CIN existe déjà!");
                    }
                    break;
                case 2:
                    System.out.println("Liste des utilisateurs:");
                    for (User user : users.values()) {
                        user.showInfo();
                    }
                    break;
                case 3:
                    System.out.print("Entrez le CIN pour saisir les données de carbone: ");
                    CIN = scanner.nextLine();
                    User userForData = users.get(CIN);
                    if (userForData != null) {
                        System.out.print("Combien d'enregistrements de consommation souhaitez-vous saisir? : ");
                        int count = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        userForData.addDataCarbon(count);
                    } else {
                        System.out.println("Utilisateur introuvable.");
                    }
                    break;
                case 4:
                    System.out.print("Entrez le CIN pour analyser la consommation: ");
                    CIN = scanner.nextLine();
                    User userForAnalysis = users.get(CIN);
                    if (userForAnalysis != null) {
                        userForAnalysis.analyzeConsumption();
                    } else {
                        System.out.println("Utilisateur introuvable.");
                    }
                    break;
                case 0:
                    System.out.println("<---- Au revoir :) ---->");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
                    break;
            }
        }
    }
}
