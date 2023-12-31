package view.viewController;

import controller.GameController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import view.Main;

public class CustomGameInputDialogController {
    @FXML
    private TextField gridSizeTextField;
    @FXML
    private TextField movesCountTextField;
    @FXML
    private TextField shuffleCountTextField;
    @FXML
    private TextField targetScoreTextField;
    @FXML
    private CheckBox customMapCheckbox;
    @FXML
    private CheckBox specialItemCheckbox;

    public void generateMap() {
        if (gridSizeTextField.getText() == null) {
            UtilView.generateErrorAlert("Error!", "Custom grid size field left empty!");
        }
        else if (gridSizeTextField != null) {
            gridSizeTextField.getText();
        }

        if (movesCountTextField.getText() == null) {
            UtilView.generateErrorAlert("Error!", "Moves count field left empty!");
        }
        else if (movesCountTextField.getText() != null) {
            movesCountTextField.getText();
        }

        if (shuffleCountTextField.getText() == null) {
            UtilView.generateErrorAlert("Error!", "Moves count field left empty!");
        }
        else if (shuffleCountTextField.getText() != null) {
            shuffleCountTextField.getText();
        }

        if (targetScoreTextField.getText() == null) {
            UtilView.generateErrorAlert("Error!", "Target score field left empty!");
        }
        else if (targetScoreTextField.getText() != null) {
            targetScoreTextField.getText();
        }


    }

    public void newGame() {
//        FXMLLoader loader = new FXMLLoader("/view/fxml/BoardScene.fxml");
//        Parent boardScene = loader.load();
//
//        boardSceneController = loader.getController();
//        GameController gameController = new GameController(currentGameMode, levelIndex, boardSceneController);
//
//        scene = new Scene(boardScene);
//        Main.stage.setScene(scene);
//        Main.stage.show();
//        gameObjective(event, dialogURL);
//
//        MusicController.stopMusic(music);
//        MusicController.stopMusic(wind);
    }

}
