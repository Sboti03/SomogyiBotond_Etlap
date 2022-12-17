package hu.petrik.etlap;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public abstract class Controller {

    protected void error(String headerText) {
        error(headerText, "");
    }

    protected void error(String headerText, String contentText) {
        alert(Alert.AlertType.ERROR, headerText, contentText);
    }

    protected void information(String headerText) {
        alert(Alert.AlertType.INFORMATION, headerText, "");
    }

    protected boolean confirmation(String header, String context) {
        boolean result = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, context,
                ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(header);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            result = true;
        }
        return result;
    }


    protected void warning(String headerText) {
        alert(Alert.AlertType.WARNING, headerText, "");
    }

    protected void warning(String headerText, String contextText) {
        alert(Alert.AlertType.WARNING, headerText, contextText);
    }

    protected void alert(Alert.AlertType alertType, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}