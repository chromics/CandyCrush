package view.controller;

import java.io.File;
import java.io.FileWriter;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class BoardSceneController {
    @FXML
    private Stage stage;
    private Scene scene;
    private int saveFileNumber = 1;
    private boolean saveFileExists;

    public void saveExit(ActionEvent event) throws Exception {
        // Alert
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle("Save & Exit");
        alert.setHeaderText("You're about to exit the current game!");
        alert.setContentText("Do you want to save?");

        ButtonType YES = new ButtonType("Yes");
        ButtonType NO = new ButtonType("No");
        ButtonType CANCEL = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(YES, NO, CANCEL);

        // Save & Exit
        Optional <ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == YES) {
            // Save
            createSaveFile(event);
            // Test
            if (checkSaveFile()) {
                // Exit
                backToStartScene(event);
            }
        }
        // Exit
        else if (result.isPresent() && result.get() == NO) {
            backToStartScene(event);
        }

    }
    public void backToStartScene(ActionEvent event) throws Exception {
        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/StartScene.fxml"));
        this.stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        this.scene = new Scene(startScene);
        stage.setScene(this.scene);
        stage.show();
    }
    public void createSaveFile(ActionEvent event) throws Exception {
        // issues: does not update saveFileNumber nor does it create a new file after the first
        // add feat: sortSaveFile --> method to sort and fill in empty save file numbers after deletion --> for the load game scene
        // add feat: deleteSaveFile --> method to delete save files --> for the load game scene
        // add feat: loadSaveFile --> for the load game scene
        String fileName = "src/view/saves/saveFile" + this.saveFileNumber + ".txt";
        
        File dir = new File("src/view/saves");
        if (dir.listFiles() != null) {
            for (int i = 0; i < dir.listFiles().length; i ++) {

                int checkNumLen = dir.listFiles()[i].getName().length() - 13;
                int numLen = fileName.length() - 28;
                String fileCheckNumber = dir.listFiles()[i].getName().substring(8, 9 + checkNumLen);
                String fileNumber = fileName.substring(23, 24 + numLen);

                System.out.println("file no." + i + " : " + dir.listFiles()[i].getName());
                System.out.println("fileName: " + fileName);
                System.out.println("checkNumLen: " + checkNumLen);
                System.out.println("numLen: " + numLen);
                System.out.println("fileCheckNumber: " + fileCheckNumber);
                System.out.println("fileNumber: " + fileNumber);

                if (fileNumber.equals(fileCheckNumber)) {
                    this.saveFileNumber++;
                    fileName = "src/view/saves/saveFile" + this.saveFileNumber + ".txt";
                    i = 0;
                }

            }
        }

        System.out.println("fileName after loop: " + fileName);

        File file = new File(fileName);
        file.createNewFile();
        // maybe save files need to be stored in local?
        
        FileWriter writer = new FileWriter(fileName);
        writer.write("*insert save file data*");
        writer.close();

        if (!file.exists()) {
            this.saveFileExists = false;

            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Error!");
            errorAlert.setContentText("Save failed. Please retry.");
            errorAlert.show();
        }
        else {
            this.saveFileExists = true;

            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setHeaderText("Success!");
            successAlert.setContentText("Your current game has been saved.");
            successAlert.show();
        }

        this.saveFileNumber++;

    }
    public boolean checkSaveFile() {
        return saveFileExists;
    }

    public void swap(ActionEvent event) {
        System.out.println("swap");
    }

    public void replant(ActionEvent event) {
        System.out.println("replant");
    }

    public void shuffle(ActionEvent event) {
        System.out.println("shuffle");
    }

}
