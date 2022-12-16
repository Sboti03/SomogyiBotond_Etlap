package hu.petrik.etlap.database;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDB {

    private final Connection conn;

    public static String DB_DRIVER = "mysql";
    public static String DB_HOST = "localhost";
    public static String DB_PORT = "3306";
    public static String DB_USERNAME = "root";
    public static String DB_PASSWORD = "";
    public static String DB_NAME = "etlapdb";


    public MenuDB() throws SQLException {
        String url = String.format("jdbc:%s://%s:%s/%s", DB_DRIVER, DB_HOST, DB_PORT, DB_NAME);
        conn = DriverManager.getConnection(url, DB_USERNAME, DB_PASSWORD);
    }

    public List<Meal> getMenu() throws SQLException {
        List<Meal> menu = new ArrayList<>();

        String sql = "SELECT etlap.id, etlap.nev, etlap.leiras, etlap.ar, kategoria.nev AS kategoria " +
                "FROM `etlap` INNER JOIN kategoria ON etlap.kategoria_id=kategoria.id;";

        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);

        while (result.next()) {
            menu.add(getAMeal(result));
        }
        return menu;

    }

    public Meal getMealById(int id) throws SQLException {
        String sql = "SELECT * FROM etlap WHERE id = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return getAMeal(resultSet);
    }

    private Meal getAMeal(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String name = result.getString("nev");
        String description = result.getString("leiras");
        int price = result.getInt("ar");
            String category = result.getString("kategoria");
        return new Meal(id,name, new MealCategory(category), description,price);
    }

    public List<MealCategory> getCategories() throws SQLException {
        String sql = "SELECT id, nev FROM kategoria";

        Statement statement = conn.createStatement();
        ResultSet results = statement.executeQuery(sql);

        List<MealCategory> categories = new ArrayList<>();
        while (results.next()) {
            categories.add(new MealCategory(
                    results.getInt("id"),
                    results.getString("nev")
            ));
        }
        return categories;
    }

    public boolean insertMeal(Meal meal) throws SQLException {
        String sql = "INSERT INTO etlap (nev, leiras, ar, kategoria_id) VALUES (?, ?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, meal.getName());
        statement.setString(2, meal.getDescription());
        statement.setInt(3, meal.getPrice());
        statement.setInt(4, meal.getCategory().getId());
        return statement.executeUpdate() >= 1;
    }

    public boolean deleteMeal(int id) throws SQLException {
        String sql = "DELETE FROM etlap WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        return statement.executeUpdate() >= 1;
    }

    public boolean increasePriceWithMoney(int id, int increment) throws SQLException {
        String sql = "UPDATE etlap SET ar = ar + ?";
        PreparedStatement statement = null;
        if (id == -1) {
            statement = conn.prepareStatement(sql);
            statement.setInt(1, increment);
        } else {
            sql += " WHERE id = ?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, increment);
            statement.setInt(2, id);
        }
        return statement.executeUpdate() >= 1;
    }

    public boolean increasePriceWithPercentage(int id, int rawPercentage) throws SQLException {
        double percentage = (double) rawPercentage / 100 + 1;
        String sql = "UPDATE etlap SET ar = ar * ?";
        PreparedStatement statement = null;
        if (id == -1) {
            statement = conn.prepareStatement(sql);
            statement.setDouble(1, percentage);
        } else {
            sql += " WHERE id = ?";
            statement = conn.prepareStatement(sql);
            statement.setDouble(1, percentage);
            statement.setInt(2, id);
        }
        return statement.executeUpdate() >= 1;

    }

}
