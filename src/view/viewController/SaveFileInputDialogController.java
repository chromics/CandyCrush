package view.viewController;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

import controller.SaveLoadController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    private static TextField saveFileName;

    private static Stage dialog;
    private static String source;

    // @Override
    // public void initialize(URL url, ResourceBundle resourceBundle) {
    //     saveFileName =  new TextField();
    //     // saveFileName.setText("SaveFile");
    // }
    public static String getText() {
        return saveFileName.getSelectedText();
    }

    public static void generateSaveFileNameTextField(String sourceClass) throws Exception {
        source = sourceClass;
        saveFileName = new TextField();

        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Main.stage);
        dialog.setResizable(false);
        dialog.setX(Main.stage.getX() - 150);
        dialog.setY(Main.stage.getY() + 150);

        Parent saveFileInputDialog = FXMLLoader.load(SaveFileInputDialogController.class.getResource("/view/fxml/SaveFileInputDialog.fxml"));
        Scene scene = new Scene(saveFileInputDialog);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
    
    public void save() throws Exception {

        String fileName = SaveFileInputDialogController.saveFileName.getText();
        System.out.println("Input Filed Name : " + fileName);
        if (fileName == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("No content detected.");
            alert.setContentText("Please input a save file name into the text field.");
            alert.setX(Main.stage.getX() + 625);
            alert.setY(Main.stage.getY() - 55);
            alert.showAndWait();

            System.out.println("null input detected");
        }

        // saveFileName.textProperty().addListener((observable, oldValue, newValue) -> {
        //     // Restricting prohibited symbols
        //     if (newValue.matches("[^#*]+")) {
        //         saveFileName.setText(newValue.replaceAll("[^#*]+", ""));
        //     }

        //     // Limiting number of characters
        //     if (saveFileName.getText().length() > MAXINPUTLENGTH) {
        //         String limitedText = saveFileName.getText().substring(0, MAXINPUTLENGTH);
        //         saveFileName.setText(limitedText);
        //     }

        //     System.out.println("character error detected.");
        // });

        SaveLoadController.saveGame(BoardSceneController.getGameData(), fileName);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success!");
        alert.setHeaderText("Save successful!");
        alert.setContentText("Your file has been successfully saved.");
        alert.setX(Main.stage.getX() + 625);
        alert.setY(Main.stage.getY() - 55);
        alert.showAndWait();

        System.out.println("save fired!");
        dialog.close();
        if (source.equals("mainStage")) {
            System.out.println("Close Main Stage");
            Main.stage.close();
        }
        else if (source.equals("homeButton")) {
            System.out.println("Back To Start Screen");
            backToStartScene();
        }
    }

    public void cancel() {
        dialog.close();
    }

    public void backToStartScene() throws Exception {
        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/StartScene.fxml"));
        BoardSceneController.setScene(new Scene(startScene));
        Main.stage.setScene(BoardSceneController.getScene());
        Main.stage.show();
    }

}
