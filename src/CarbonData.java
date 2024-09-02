import java.time.LocalDate;

public class CarbonData {
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private float carbon;

    public CarbonData(LocalDate dateStart, LocalDate dateEnd, float carbon) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.carbon = carbon;
    }

    // Getters and Setters
    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public float getCarbon() {
        return carbon;
    }

    public void setCarbon(float carbon) {
        this.carbon = carbon;
    }

    @Override
    public String toString() {
        return "Date début: " + dateStart + ", Date fin: " + dateEnd + ", Consommation de carbone: " + carbon + " Kg Co²";
    }
}
