import java.util.ArrayList;
import java.util.List;

public class User {
    private String cin;
    private String name;
    private int age;
    private List<Consumption> consumptionList;

    public User(String cin, String name, int age) {
        this.cin = cin;
        this.name = name;
        this.age = age;
        this.consumptionList = new ArrayList<>();
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Consumption> getConsumptionList() {
        return consumptionList;
    }


    public void addConsumption(Consumption consumption ) {
        if (this.consumptionList == null) {
            this.consumptionList = new ArrayList<>();
        }
        this.consumptionList.add(consumption);
    }
}
