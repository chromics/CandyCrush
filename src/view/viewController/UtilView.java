package view.viewController;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import view.Main;

public class UtilView {
    public static void generateErrorAlert(String header, String content){
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.show();
        errorAlert.setX(Main.stage.getX() + 625);
        errorAlert.setY(Main.stage.getY() - 55);
    }

    public static void generateConfirmationAlert(String header, String content){
        
    }
}
