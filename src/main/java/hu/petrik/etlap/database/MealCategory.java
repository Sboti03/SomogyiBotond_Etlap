package hu.petrik.etlap.database;

public class MealCategory {

    private int id;
    private String nev;

    public MealCategory(int id, String nev) {
        this.id = id;
        this.nev = nev;
    }

    public MealCategory(String nev) {
        this.nev = nev;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getId() {
        return id;
    }

    public String getNev() {
        return nev;
    }

    @Override
    public String toString() {
        return getNev();
    }
}
