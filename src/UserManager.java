import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.Locale;

public class UserManager {

    private static final Scanner sc = new Scanner(System.in);

    public static void addUser(User user) {
        System.out.print("Nom: ");
        user.setName(sc.nextLine());

        System.out.print("Age: ");
        user.setAge(sc.nextInt());
        sc.nextLine();
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
        float totalConsumption = 0;
        for (CarbonData data : user.getCarbonDataList()) {
            totalConsumption += data.getCarbon();
        }
        return totalConsumption;
    }

    public static void analyzeConsumption(User user) {
        System.out.println("Analyse de la consommation de carbone:");
        System.out.println("Total : " + calculateTotalConsumption(user) + " Kg Co²");

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
        for (CarbonData data : user.getCarbonDataList()) {
            System.out.println(data.getDateStart() + ": " + data.getCarbon() + " Kg Co²");
        }
    }

    private static void generateWeeklyReport(User user) {
        System.out.println("\n=== Rapport Hebdomadaire ===");

        LocalDate minDate = null;
        LocalDate maxDate = null;

        for (CarbonData data : user.getCarbonDataList()) {
            LocalDate dataStart = data.getDateStart();
            LocalDate dataEnd = data.getDateEnd();

            if (minDate == null || dataStart.isBefore(minDate)) {
                minDate = dataStart;
            }
            if (maxDate == null || dataEnd.isAfter(maxDate)) {
                maxDate = dataEnd;
            }
        }

        if (minDate == null) {
            minDate = LocalDate.now();
        }
        if (maxDate == null) {
            maxDate = LocalDate.now();
        }

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate weekStart = minDate.with(weekFields.dayOfWeek(), 1);
        while (weekStart.isBefore(maxDate)) {
            final LocalDate startOfWeek = weekStart;
            float weeklyTotal = 0;

            for (CarbonData data : user.getCarbonDataList()) {
                LocalDate dataStart = data.getDateStart();
                LocalDate dataEnd = data.getDateEnd();

                LocalDate overlapStart = dataStart.isBefore(startOfWeek) ? startOfWeek : dataStart;
                LocalDate overlapEnd = dataEnd.isAfter(startOfWeek.plusDays(6)) ? startOfWeek.plusDays(6) : dataEnd;

                if (!overlapStart.isAfter(overlapEnd)) {
                    long overlapDays = ChronoUnit.DAYS.between(overlapStart, overlapEnd) + 1;
                    long totalDays = ChronoUnit.DAYS.between(dataStart, dataEnd) + 1;
                    float weeklyConsumption = (data.getCarbon() * overlapDays) / totalDays;
                    weeklyTotal += weeklyConsumption;
                }
            }

            System.out.println("Semaine du " + startOfWeek + ": " + weeklyTotal + " Kg Co²");
            weekStart = weekStart.plusWeeks(1);
        }
    }

    private static void generateMonthlyReport(User user) {
        System.out.println("\n=== Rapport Mensuel ===");

        LocalDate minDate = null;
        LocalDate maxDate = null;

        for (CarbonData data : user.getCarbonDataList()) {
            LocalDate dataStart = data.getDateStart();
            LocalDate dataEnd = data.getDateEnd();

            if (minDate == null || dataStart.isBefore(minDate)) {
                minDate = dataStart;
            }
            if (maxDate == null || dataEnd.isAfter(maxDate)) {
                maxDate = dataEnd;
            }
        }

        if (minDate == null) {
            minDate = LocalDate.now();
        }
        if (maxDate == null) {
            maxDate = LocalDate.now();
        }

        LocalDate monthStart = minDate.withDayOfMonth(1);
        while (monthStart.isBefore(maxDate)) {
            final LocalDate startOfMonth = monthStart;
            float monthlyTotal = 0;

            for (CarbonData data : user.getCarbonDataList()) {
                LocalDate dataStart = data.getDateStart();
                LocalDate dataEnd = data.getDateEnd();

                LocalDate overlapStart = dataStart.isBefore(startOfMonth) ? startOfMonth : dataStart;
                LocalDate overlapEnd = dataEnd.isAfter(startOfMonth.plusMonths(1).minusDays(1)) ? startOfMonth.plusMonths(1).minusDays(1) : dataEnd;

                if (!overlapStart.isAfter(overlapEnd)) {
                    long overlapDays = ChronoUnit.DAYS.between(overlapStart, overlapEnd) + 1;
                    long totalDays = ChronoUnit.DAYS.between(dataStart, dataEnd) + 1;
                    float monthlyConsumption = (data.getCarbon() * overlapDays) / totalDays;
                    monthlyTotal += monthlyConsumption;
                }
            }

            System.out.println("Mois de " + startOfMonth.getMonth() + " " + startOfMonth.getYear() + ": " + monthlyTotal + " Kg Co²");
            monthStart = monthStart.plusMonths(1);
        }
    }
}
