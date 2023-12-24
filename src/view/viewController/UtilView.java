package view.viewController;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UtilView {
    public static void generateAlert(String header, String content){
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.show();
        errorAlert.setX(725);
        errorAlert.setY(45);
    }
}
