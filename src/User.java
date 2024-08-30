import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    protected String name;
    protected int age;
    protected String CIN;
    protected List<CarbonData> carbonDataList; // List to store multiple entries of carbon data

    public User(String CIN) {
        this.CIN = CIN;
        this.carbonDataList = new ArrayList<>();
    }

    public void addInfo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nom: ");
        this.name = sc.nextLine();

        System.out.print("Age: ");
        this.age = sc.nextInt();
        sc.nextLine(); // Consume newline
    }

    public void showInfo() {
        System.out.println("Informations de l'utilisateur:");
        System.out.println("Nom: " + name);
        System.out.println("Age: " + age);
        System.out.println("CIN: " + CIN);
        System.out.println("Données de consommation de carbone:");
        for (CarbonData data : carbonDataList) {
            System.out.println(data);
        }
        System.out.println("Consommation totale de carbone : " + calculateTotalConsumption() + " tonnes");
    }

    public void addDataCarbon(int count) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < count; i++) {
            try {
                System.out.print("Date début (AAAA-MM-JJ): ");
                String startDateStr = sc.nextLine();
                LocalDate startDate = LocalDate.parse(startDateStr);

                System.out.print("Date fin (AAAA-MM-JJ): ");
                String endDateStr = sc.nextLine();
                LocalDate endDate = LocalDate.parse(endDateStr);

                System.out.print("Consommation de carbone (en tonnes): ");
                float carbon = Float.parseFloat(sc.nextLine());

                carbonDataList.add(new CarbonData(startDate, endDate, carbon));
                System.out.println("Données de carbone ajoutées avec succès!");

            } catch (Exception e) {
                System.out.println("Erreur de saisie. Veuillez entrer les données dans le format correct.");
            }
        }
    }

    public float calculateTotalConsumption() {
        float total = 0;
        for (CarbonData data : carbonDataList) {
            total += data.getCarbon();
        }
        return total;
    }

    public void analyzeConsumption() {
        System.out.println("Analyse de la consommation de carbone:");
        System.out.println("Consommation quotidienne:");
        System.out.println("Total : " + calculateTotalConsumption() + " tonnes");

        float dailyConsumption = calculateConsumptionForPeriod(ChronoUnit.DAYS);
        System.out.println("Consommation moyenne quotidienne : " + dailyConsumption + " tonnes");

        float weeklyConsumption = calculateConsumptionForPeriod(ChronoUnit.WEEKS);
        System.out.println("Consommation moyenne hebdomadaire : " + weeklyConsumption + " tonnes");

        float monthlyConsumption = calculateConsumptionForPeriod(ChronoUnit.MONTHS);
        System.out.println("Consommation moyenne mensuelle : " + monthlyConsumption + " tonnes");
    }

    private float calculateConsumptionForPeriod(ChronoUnit unit) {
        float totalConsumption = 0;
        long totalPeriod = 0;

        for (CarbonData data : carbonDataList) {
            long period = unit.between(data.getDateStart(), data.getDateEnd()) + 1;
            totalConsumption += data.getCarbon();
            totalPeriod += period;
        }

        if (totalPeriod > 0) {
            return totalConsumption / totalPeriod;
        } else {
            return 0;
        }
    }
}

