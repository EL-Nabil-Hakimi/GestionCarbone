import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private int age;
    private String CIN;
    private List<CarbonData> carbonDataList;

    // Constructor
    public User(String CIN) {
        this.CIN = CIN;
        this.carbonDataList = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public List<CarbonData> getCarbonDataList() {
        return carbonDataList;
    }

    public void setCarbonDataList(List<CarbonData> carbonDataList) {
        this.carbonDataList = carbonDataList;
    }

    // Methods
    public void addInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void addDataCarbon(CarbonData carbonData) {
        this.carbonDataList.add(carbonData);
    }
}
