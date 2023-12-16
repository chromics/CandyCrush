package view.controller;

import java.io.File;
import java.io.FileWriter;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.components.Chessboard;
import model.components.Constant;
import model.components.Cell;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;
import java.net.URL;

public class BoardSceneController implements Initializable {
    private Stage stage;
    private Scene scene;
    private int saveFileNumber = 1;
    private boolean saveFileExists;
    private static final Chessboard board = new Chessboard();
    private static final Cell[][] grid = board.getGrid();
    private String saveData = "";

    @FXML
    GridPane boardView;

    public void initialize(URL location, ResourceBundle resourceBundle) {
        initiateBoard();
    }

    public void saveExit(ActionEvent event) throws Exception {
        // Alert
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle("Save & Exit");
        alert.setHeaderText("You're about to exit the current game!");
        alert.setContentText("Do you want to save?");

        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.YES)).setDefaultButton(true);

        // Save & Exit
        Optional <ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            // Save
            createSaveFile(event);
            // Test
            if (checkSaveFile()) {
                // Exit
                backToStartScene(event);
            }
        }
        // Exit
        else if (result.isPresent() && result.get() == ButtonType.NO) {
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
        // add feat: sortSaveFile --> method to sort and fill in empty save file numbers after deletion --> for the load game scene
        // add feat: deleteSaveFile --> method to delete save files --> for the load game scene
        // add feat: loadSaveFile --> for the load game scene
        String fileName = "src/view/saves/saveFile" + this.saveFileNumber + ".csv";
        
        File dir = new File("src/view/saves");
        if (dir.listFiles() != null) {
            for (int i = 0; i < dir.listFiles().length; i ++) {

                int checkNumLen = dir.listFiles()[i].getName().length() - 13;
                int numLen = fileName.length() - 28;
                String fileCheckNumber = dir.listFiles()[i].getName().substring(8, 9 + checkNumLen);
                String fileNumber = fileName.substring(23, 24 + numLen);

                if (fileNumber.equals(fileCheckNumber)) {
                    this.saveFileNumber++;
                    fileName = "src/view/saves/saveFile" + this.saveFileNumber + ".csv";
                    i = 0;
                }

            }
        }

        File file = new File(fileName);
        file.createNewFile();
        // maybe save files need to be stored in local?
        
        FileWriter writer = new FileWriter(fileName);
        writer.write(getSaveData());
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
    public String getSaveData() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                this.saveData += grid[i][j].getPiece().getName() + " ";
            }
        }
        return this.saveData;
    }

    public void initiateBoard() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                Image bigPatch = new Image(Constant.DECORATIONS.get("bigPatch"));
                ImageView bigPatchView = new ImageView(bigPatch);
                bigPatchView.setFitWidth(Constant.PICTURE_SIZE.getNum());
                bigPatchView.setFitHeight(Constant.PICTURE_SIZE.getNum());
                boardView.add(bigPatchView, j, i);

                if (grid[i][j].isPlayable()) {
                    Image patch = new Image(Constant.DECORATIONS.get("patch"));
                    ImageView patchView = new ImageView(patch);
                    patchView.setFitWidth(Constant.PICTURE_SIZE.getNum());
                    patchView.setFitHeight(Constant.PICTURE_SIZE.getNum());
                    this.boardView.add(patchView, j, i);
                    
                    Image fruit = new Image(grid[i][j].getPiece().getImagePath());
                    ImageView fruitView = new ImageView(fruit);
                    fruitView.setFitWidth(Constant.PICTURE_SIZE.getNum());
                    fruitView.setFitHeight(Constant.PICTURE_SIZE.getNum());
                    this.boardView.add(fruitView, j, i);
                }
            }
        }
        
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

// public Node getChildByRowColumn(GridPane gridPane, int row, int col) {
    //     // ty c0der from stackexchange
    //     for (Node node : gridPane.getChildren()){
    //         if (GridPane.getRowIndex(node) == null) continue ; //ignore Group 
    //         if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
    //             return node;
    //         }
    //     }
    //     return null;
    // } 
    // public void setImageViewByRowColumn(GridPane gridPane, int row, int col, Image image) {
    //     Node node = getChildByRowColumn(gridPane, row, col);
    //     if (node instanceof ImageView) {
    //         ((ImageView)node).setImage(image);;
    //     }
    //     else {
    //         gridPane.getChildren().remove(node);
    //         gridPane.add(new ImageView(image), col, row);
    //     }
    // }
