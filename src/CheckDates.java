import java.time.LocalDate;
import java.util.List;

public class CheckDates {
    public static boolean isAvailableOn(LocalDate startDate , LocalDate endDate ,List<Consumption> dates)
    {
            for(Consumption date : dates)
            {
                for(LocalDate sdate = date.getStartDate() ; sdate.isBefore(date.getEndDate().plusDays(1)); sdate = sdate.plusDays(1))
                {
                    if (sdate.equals(startDate) || sdate.equals(endDate)) {
                        return false;
                    }
                    }


            }

            return true ;
    }
}