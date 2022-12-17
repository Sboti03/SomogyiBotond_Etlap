package hu.petrik.etlap;

import hu.petrik.etlap.database.Meal;
import hu.petrik.etlap.database.MealCategory;
import hu.petrik.etlap.database.MenuDB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class MealInsertController extends Controller {
    @javafx.fxml.FXML
    private TextField nameInput;
    @javafx.fxml.FXML
    private TextField descriptionInput;
    @javafx.fxml.FXML
    private ChoiceBox<MealCategory> categoryChoiceBox;
    @javafx.fxml.FXML
    private Spinner<Integer> priceSpinner;

    private MenuDB menuDB;

    @FXML
    private void initialize() {
        priceSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1, 1));
        Platform.runLater(() -> {
            try {
                menuDB = new MenuDB();
                List<MealCategory> categories = menuDB.getCategories();
                for (MealCategory category : categories) {
                    categoryChoiceBox.getItems().add(category);
                }
                categoryChoiceBox.setValue(categories.get(0));
            } catch (SQLException e) {
                error("Nem sikert csatlakozni az adatbázishoz", e.getMessage());
            }
        });
    }

    public void insertBtnClick(ActionEvent actionEvent) {
        if (nameInput.getText().isEmpty() ||
                descriptionInput.getText().isEmpty() ||
                categoryChoiceBox.getSelectionModel().isEmpty()) {
            error("Minden mező kitöltése kötelező");
            return;
        }

        String name = nameInput.getText().trim();
        String description = descriptionInput.getText().trim();
        int price = priceSpinner.getValueFactory().getValue();
        MealCategory category = categoryChoiceBox.getSelectionModel().getSelectedItem();
        Meal meal = new Meal(name, category, description, price);
        try {
            if (menuDB.insertMeal(meal)) {
                information("Sikeres felvétel!");
                nameInput.setText("");
                descriptionInput.setText("");
                priceSpinner.getValueFactory().setValue(0);
                categoryChoiceBox.setValue(categoryChoiceBox.getItems().get(0));
            } else {
                error("Sikertelen felvétel", "ERROR 404");
            }
        } catch (SQLException e) {
            error("Nem sikert hozzá adni", e.getMessage());
        }
    }
}
