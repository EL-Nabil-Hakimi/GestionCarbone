import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserManager {

    private static final Scanner sc = new Scanner(System.in);

    public static void addUser(User user) {
        System.out.print("Nom: ");
        user.setName(sc.nextLine());

        System.out.print("Age: ");
        user.setAge(sc.nextInt());
        sc.nextLine(); // Consume newline
    }

    public static void showUserInfo(User user) {
        System.out.println("============================================================================================");
        System.out.println("============================== Informations de l'utilisateur: ==============================");
        System.out.println("Nom: " + user.getName());
        System.out.println("Age: " + user.getAge());
        System.out.println("CIN: " + user.getCIN());
        System.out.println("Données de consommation de carbone:");
        for (CarbonData data : user.getCarbonDataList()) {
            System.out.println(data);
        }
        System.out.println("Consommation totale de carbone : " + calculateTotalConsumption(user) + " Kg Co²");
        System.out.println("============================================================================================");
    }

    public static void addDataCarbon(User user, int count) {
        for (int i = 0; i < count; i++) {
            try {
                System.out.print("Date début (AAAA-MM-JJ): ");
                LocalDate startDate = LocalDate.parse(sc.nextLine());

                System.out.print("Date fin (AAAA-MM-JJ): ");
                LocalDate endDate = LocalDate.parse(sc.nextLine());

                System.out.print("Consommation de carbone (en Kg Co²): ");
                float carbon = Float.parseFloat(sc.nextLine());

                user.addDataCarbon(new CarbonData(startDate, endDate, carbon));
                System.out.println("Données de carbone ajoutées avec succès!");

            } catch (Exception e) {
                System.out.println("Erreur de saisie. Veuillez entrer les données dans le format correct.");
            }
        }
    }

    public static float calculateTotalConsumption(User user) {
        return (float) user.getCarbonDataList().stream().mapToDouble(CarbonData::getCarbon).sum();
    }

    public static void analyzeConsumption(User user) {
        System.out.println("Analyse de la consommation de carbone:");
        System.out.println("Total : " + calculateTotalConsumption(user) + " Kg Co²");

        System.out.println("Consommation moyenne quotidienne : " + calculateAverageConsumption(user, ChronoUnit.DAYS) + " Kg Co²");
        System.out.println("Consommation moyenne hebdomadaire : " + calculateAverageConsumption(user, ChronoUnit.WEEKS) + " Kg Co²");
        System.out.println("Consommation moyenne mensuelle : " + calculateAverageConsumption(user, ChronoUnit.MONTHS) + " Kg Co²");

        generateDailyReport(user);
        generateWeeklyReport(user);
        generateMonthlyReport(user);
    }

    private static float calculateAverageConsumption(User user, ChronoUnit unit) {
        float totalConsumption = 0;
        long totalUnits = 0;

        for (CarbonData data : user.getCarbonDataList()) {
            long period = unit.between(data.getDateStart(), data.getDateEnd()) + 1;
            totalConsumption += data.getCarbon();

            if (unit == ChronoUnit.DAYS) {
                totalUnits += period;
            } else if (unit == ChronoUnit.WEEKS) {
                totalUnits += (period / 7);
            } else if (unit == ChronoUnit.MONTHS) {
                totalUnits += (period / 30);
            }
        }

        return totalUnits > 0 ? totalConsumption / totalUnits : 0;
    }

    private static void generateDailyReport(User user) {
        System.out.println("\n=== Rapport Quotidien ===");
        user.getCarbonDataList().forEach(data -> {
            System.out.println(data.getDateStart() + ": " + data.getCarbon() + " Kg Co²");
        });
    }

    private static void generateWeeklyReport(User user) {
        System.out.println("\n=== Rapport Hebdomadaire ===");
        user.getCarbonDataList().stream()
                .collect(Collectors.groupingBy(data -> {
                    LocalDate startOfWeek = data.getDateStart().with(java.time.DayOfWeek.MONDAY);
                    return startOfWeek;
                }))
                .forEach((weekStart, weekData) -> {
                    float weeklyTotal = (float) weekData.stream().mapToDouble(CarbonData::getCarbon).sum();
                    System.out.println("Semaine du " + weekStart + ": " + weeklyTotal + " Kg Co²");
                });
    }

    private static void generateMonthlyReport(User user) {
        System.out.println("\n=== Rapport Mensuel ===");
        user.getCarbonDataList().stream()
                .collect(Collectors.groupingBy(data -> data.getDateStart().withDayOfMonth(1)))
                .forEach((monthStart, monthData) -> {
                    float monthlyTotal = (float) monthData.stream().mapToDouble(CarbonData::getCarbon).sum();
                    System.out.println("Mois de " + monthStart.getMonth() + " " + monthStart.getYear() + ": " + monthlyTotal + " Kg Co²");
                });
    }
}
