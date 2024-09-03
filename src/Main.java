import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        HashMap<String, User> users = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        User currentUser = null;

        while (true) {
            printMenu();
            System.out.print("Choix : ");
            int choix = sc.nextInt();
            sc.nextLine(); // Consume newline character

            switch (choix) {
                case 1:
                    System.out.print("CIN : ");
                    String cin = sc.nextLine();
                    User newUser = new User(cin, "", 0);
                    UserManager.addUser(newUser, cin);
                    users.put(cin, newUser);
                    currentUser = newUser;
                    break;

                case 2:
                    if (currentUser != null) {
                        UserManager.showUserInfo(currentUser);
                    } else {
                        System.out.println("Aucun utilisateur sélectionné.");
                    }
                    break;

                case 3:
                    for (User data : users.values()) {
                        UserManager.showUserInfo(data);
                    }
                    break;

                case 4:
                    if (currentUser != null) {
                        UserManager.addConsumption(currentUser);
                    } else {
                        System.out.println("Aucun utilisateur sélectionné.");
                    }
                    break;

                case 5:
                    System.out.println("=============== Rapport quotidiennement ===============");
                    if (currentUser != null) {
                        UserManager.Daily(currentUser);
                    } else {
                        System.out.println("Aucun utilisateur sélectionné.");
                    }
                    break;

                case 6:
                    System.out.println("=============== Rapport hebdomadairement ===============");
                    if (currentUser != null) {
                        UserManager.Weekly(currentUser);
                    } else {
                        System.out.println("Aucun utilisateur sélectionné.");
                    }
                    break;

                case 7:
                    System.out.println("=============== Rapport mensuellement ===============");
                    if (currentUser != null) {
                        UserManager.Monthly(currentUser);
                    } else {
                        System.out.println("Aucun utilisateur sélectionné.");
                    }
                    break;

                case 8:
                    System.out.print("Entrez le CIN pour supprimer l'utilisateur: ");
                    String cinToRemove = sc.nextLine();
                    if (users.remove(cinToRemove) != null) {
                        System.out.println("Utilisateur supprimé avec succès :) ");
                        if (currentUser != null && currentUser.getCin().equals(cinToRemove)) {
                            currentUser = null;
                        }
                    } else {
                        System.out.println("Utilisateur introuvable.");
                    }
                    break;

                case 9:
                    System.out.print("Entrez le CIN pour changer l'utilisateur: ");
                    String cinToChange = sc.nextLine();
                    User selectedUser = users.get(cinToChange);
                    if (selectedUser != null) {
                        currentUser = selectedUser;
                        System.out.println("Utilisateur changé avec succès.");
                    } else {
                        System.out.println("Utilisateur introuvable.");
                    }
                    break;

                case 0:
                    System.out.println("Exiting program.");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("======== Menu Principal ========");
        System.out.println("1. Ajouter un utilisateur");
        System.out.println("2. Afficher les informations de l'utilisateur sélectionné");
        System.out.println("3. Afficher les informations de tous les utilisateurs");
        System.out.println("4. Ajouter une consommation");
        System.out.println("5. Rapport quotidien");
        System.out.println("6. Rapport hebdomadaire");
        System.out.println("7. Rapport mensuel");
        System.out.println("8. Supprimer un utilisateur");
        System.out.println("9. Changer d'utilisateur");
        System.out.println("0. Quitter");
        System.out.println("================================");
    }
}
