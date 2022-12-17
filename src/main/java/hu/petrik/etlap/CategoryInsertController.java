package hu.petrik.etlap;

import hu.petrik.etlap.database.MealCategory;
import hu.petrik.etlap.database.MenuDB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class CategoryInsertController extends Controller {
    public TextField categoryInputField;
    private MenuDB menuDB;


    @FXML
    private void initialize() {
        Platform.runLater(()-> {
            try {
                menuDB = new MenuDB();
            } catch (SQLException e) {
                error("Nem sikerült csatlakozi az adatbázishoz", e.getMessage());
            }
        });
    }

    public void insertCategoryClick(ActionEvent actionEvent) {
        String categoryName = categoryInputField.getText().trim();
        if (categoryName.isEmpty()) {
            warning("A mező kitöltése kötelező!");
            return;
        }
        MealCategory category = new MealCategory(categoryName);
        try {
            menuDB.insertCategory(category);
        } catch (SQLException e) {
            error("Nem sikerült felvenni a kategóriát", e.getMessage());
        }
    }
}
