import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;

public class UserManager {

    private static Scanner sc = new Scanner(System.in);

    public static void addUser(User user, String cin) {
        System.out.print("Name : ");
        user.setName(sc.next());
        System.out.print("Age : ");
        user.setAge(sc.nextInt());
        user.setCin(cin);
    }

    public static void showUserInfo(User user) {
        System.out.println("============================================================================================");
        System.out.println("============================== Informations de l'utilisateur: ==============================");
        System.out.println("Nom: " + user.getName());
        System.out.println("Age: " + user.getAge());
        System.out.println("CIN: " + user.getCin());
        System.out.println("Données de consommation de carbone:");

        for (Consumption data : user.getConsumptionList()) {
            System.out.println(data);
        }

        System.out.println("Consommation totale de carbone : " + calculateTotalConsumption(user) + " Kg Co²");
        System.out.println("============================================================================================");
    }

    private static float calculateTotalConsumption(User user) {
        float totalConsumption = 0;
        for (Consumption data : user.getConsumptionList()) {
            totalConsumption += data.getConsumption();
        }
        return totalConsumption;
    }

    public static void addConsumption(User user) {
        System.out.println("=========== Ajouter une Consommation =========");

        System.out.print("Date Debut (yyyy-MM-dd) : ");
        LocalDate startDate = LocalDate.parse(sc.next());
        System.out.print("Date Fin (yyyy-MM-dd) : ");
        LocalDate endDate = LocalDate.parse(sc.next());
        System.out.print("Consommation (Kg/Co²) : ");
        float consumption = Float.parseFloat(sc.next());

        user.addConsumption(new Consumption(startDate, endDate, consumption));
    }

    public static void Daily(User user) {
        for (Consumption data : user.getConsumptionList()) {
            LocalDate startDate = data.getStartDate();
            LocalDate endDate = data.getEndDate();
            long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
            float dailyConsumptionRate = data.getConsumption() / totalDays;

            LocalDate currentDate = startDate;
            while (!currentDate.isAfter(endDate)) {
                System.out.println("Consommation du " + currentDate + ": " + dailyConsumptionRate + " Kg/Co²");
                currentDate = currentDate.plusDays(1);
            }
        }
    }

    public static void Weekly(User user) {
        System.out.println("\n=== Rapport Hebdomadaire ===");

        List<Consumption> consumptionList = user.getConsumptionList();
        Map<LocalDate, Float> weeklyTotals = new HashMap<>();

        LocalDate minDate = null;
        LocalDate maxDate = null;

        for (Consumption data : consumptionList) {
            LocalDate dataStart = data.getStartDate();
            LocalDate dataEnd = data.getEndDate();

            if (minDate == null || dataStart.isBefore(minDate)) {
                minDate = dataStart;
            }
            if (maxDate == null || dataEnd.isAfter(maxDate)) {
                maxDate = dataEnd;
            }
        }

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate weekStart = minDate.with(weekFields.dayOfWeek(), 1);

        while (weekStart.isBefore(maxDate)) {
            final LocalDate startOfWeek = weekStart;
            float weeklyTotal = 0;

            for (Consumption data : consumptionList) {
                LocalDate dataStart = data.getStartDate();
                LocalDate dataEnd = data.getEndDate();
                float consumption = data.getConsumption();

                LocalDate overlapStart = dataStart.isBefore(startOfWeek) ? startOfWeek : dataStart;
                LocalDate overlapEnd = dataEnd.isAfter(startOfWeek.plusDays(6)) ? startOfWeek.plusDays(6) : dataEnd;

                if (!overlapStart.isAfter(overlapEnd)) {
                    long overlapDays = ChronoUnit.DAYS.between(overlapStart, overlapEnd) + 1;
                    long totalDays = ChronoUnit.DAYS.between(dataStart, dataEnd) + 1;
                    float weeklyConsumption = (consumption * overlapDays) / totalDays;

                    weeklyTotal += weeklyConsumption;
                }
            }

            weeklyTotals.put(startOfWeek, weeklyTotal);

            weekStart = weekStart.plusWeeks(1);
        }

        for (Map.Entry<LocalDate, Float> entry : weeklyTotals.entrySet()) {
            LocalDate week = entry.getKey();
            float totalConsumption = entry.getValue();
            System.out.println("Semaine du " + week + ": " + totalConsumption + " Kg Co²");
        }
    }

    public static void Monthly(User user) {
        System.out.println("\n=== Rapport Mensuel ===");

        List<Consumption> consumptionList = user.getConsumptionList();
        Map<LocalDate, Float> monthlyTotals = new HashMap<>();

        for (Consumption data : consumptionList) {
            LocalDate dataStart = data.getStartDate();
            LocalDate dataEnd = data.getEndDate();
            float consumption = data.getConsumption();

            LocalDate monthStart = dataStart.withDayOfMonth(1);
            LocalDate monthEnd = monthStart.plusMonths(1).minusDays(1);

            while (!monthStart.isAfter(dataEnd)) {
                LocalDate overlapStart = monthStart.isBefore(dataStart) ? dataStart : monthStart;
                LocalDate overlapEnd = monthEnd.isAfter(dataEnd) ? dataEnd : monthEnd;

                if (!overlapStart.isAfter(overlapEnd)) {
                    long overlapDays = ChronoUnit.DAYS.between(overlapStart, overlapEnd) + 1;
                    long totalDays = ChronoUnit.DAYS.between(dataStart, dataEnd) + 1;
                    float monthlyConsumption = (consumption * overlapDays) / totalDays;

                    monthlyTotals.put(monthStart, monthlyTotals.getOrDefault(monthStart, 0f)
                            + monthlyConsumption);
                }

                monthStart = monthStart.plusMonths(1);
                monthEnd = monthEnd.plusMonths(1).minusDays(1);
            }
        }

        for (Map.Entry<LocalDate, Float> entry : monthlyTotals.entrySet()) {
            LocalDate month = entry.getKey();
            float totalConsumption = entry.getValue();
            System.out.println("Mois de " + month.getMonth() + " " + month.getYear() + ": " + totalConsumption + " Kg Co²");
        }
    }}
