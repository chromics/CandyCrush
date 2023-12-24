package view.viewController;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.lang.reflect.Method;

import view.Main;
import data.GameData;
import data.GameFileInfo;

public class SaveFileInputDialogController {
    private final static int MAXINPUTLENGTH = 15;

    @FXML
    static TextField saveFileName;

    public static void generateSaveFileNameTextField(Class<?> invoker) throws Exception {
        
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Main.stage);
        dialog.setResizable(false);
        dialog.setX(Main.stage.getX() + 675);
        dialog.setY(Main.stage.getY() + 265);

        
        saveFileName = new TextField();
        saveFileName.textProperty().addListener((observable, oldValue, newValue) -> {
            // Restricting prohibited symbols
            if (newValue.matches("[^#*]+")) {
                saveFileName.setText(newValue.replaceAll("[^#*]+", ""));
            }
            
            // Limiting number of characters
            if (saveFileName.getText().length() > MAXINPUTLENGTH) {
                String limitedText = saveFileName.getText().substring(0, MAXINPUTLENGTH);
                saveFileName.setText(limitedText);
            }
        });

        Parent saveFileInputDialog = FXMLLoader.load(SaveFileInputDialogController.class.getResource("/view/fxml/InGameSettingScene.fxml"));
        Scene scene = new Scene(saveFileInputDialog);
        dialog.setScene(scene);
        dialog.show();
    }
    
    public void save() {
        String fileName = saveFileName.getSelectedText();
    }
}
