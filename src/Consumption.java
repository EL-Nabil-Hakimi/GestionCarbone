import java.time.LocalDate;

public class Consumption {
    private LocalDate StartDate ;
    private LocalDate EndDate ;
    private float Consumption ;

    public Consumption(LocalDate startDate, LocalDate endDate, float consumption) {
        StartDate = startDate;
        EndDate = endDate;
        Consumption = consumption;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public void setStartDate(LocalDate startDate) {
        StartDate = startDate;
    }

    public LocalDate getEndDate() {
        return EndDate;
    }

    public void setEndDate(LocalDate endDate) {
        EndDate = endDate;
    }

    public float getConsumption() {
        return Consumption;
    }

    public void setConsumption(float consumption) {
        Consumption = consumption;
    }

    @Override
    public String toString() {
        return "Consumption{" +
                "StartDate=" + StartDate +
                ", EndDate=" + EndDate +
                ", Consumption=" + Consumption +
                '}';
    }
}
