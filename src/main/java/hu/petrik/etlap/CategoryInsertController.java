package hu.petrik.etlap;

import hu.petrik.etlap.database.MealCategory;
import hu.petrik.etlap.database.MenuDB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        String categoryName = categoryInputField.getText().trim().toLowerCase();
        if (categoryName.isEmpty()) {
            warning("A mező kitöltése kötelező!");
            return;
        }
        MealCategory category = new MealCategory(categoryName);
        try {
            menuDB.insertCategory(category);
            Stage stage = (Stage) categoryInputField.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                error("Ilyen kategória már létezik");
                return;
            }
            error("Nem sikerült felvenni a kategóriát", String.valueOf(e.getErrorCode()));
        }
    }
}
