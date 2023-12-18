package view.controller;

import java.io.File;
import java.io.FileWriter;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Board;
import model.BoardPoint;
import data.constant.Constant;
import model.Cell;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.geometry.Insets;

import java.net.URL;

public class BoardSceneController implements Initializable {
    private Stage stage;
    private Scene scene;
    private int saveFileNumber = 1;
    private boolean saveFileExists;
    private static Cell[][] grid;
    private String saveData = "";
    private int shuffleCount = 3;
    private BoardPoint[] points = new BoardPoint[2];
    private int pointIndex = 0;
    private Board board;

    @FXML
    GridPane boardView;
    @FXML
    Label shuffleLabel;

    // INITIALIZE
    public void initialize(URL location, ResourceBundle resourceBundle) {
        initiateBoard();
        this.shuffleLabel.setText(Integer.toString(this.shuffleCount));
    }
    public void initiateBoard() {
        Board currentBoard = new Board();
        Cell[][] currentGrid = currentBoard.getGrid();
        grid = currentGrid;
        for (int i = 0; i < Constant.BOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.BOARD_COL_SIZE.getNum(); j++) {
                if (grid[i][j].isPlayable()) {
                    BoardPoint currentPoint = new BoardPoint(i, j);

                    Image patch = new Image(Constant.decorations.get("patch"));
                    ImageView patchView = new ImageView(patch);
                    patchView.setFitWidth(Constant.PICTURE_SIZE.getNum());
                    patchView.setFitHeight(Constant.PICTURE_SIZE.getNum());
                    // this.boardView.add(patchView, j, i);
                    
                    Image fruit = new Image(grid[i][j].getPiece().getImagePath());
                    ImageView fruitView = new ImageView(fruit);
                    fruitView.setFitWidth(Constant.PICTURE_SIZE.getNum());
                    fruitView.setFitHeight(Constant.PICTURE_SIZE.getNum());

                    // StackPane stackPane = new StackPane();
                    // stackPane.getChildren().addAll(patchView, fruitView);

                    // CheckBox fruitCheckBox = new CheckBox();
                    // fruitCheckBox.setBackground(null);
                    Button fruitButton = new Button();
                    // fruitButton.setBackground(null);

                    StackPane stackPane = new StackPane();
                    stackPane.getChildren().addAll(patchView, fruitView);

                    fruitButton.setOnAction(e -> buttonHandler(currentPoint));
                    fruitButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    fruitButton.setGraphic(stackPane);
                    fruitButton.maxWidth(Double.MAX_VALUE);
                    fruitButton.maxHeight(Double.MAX_VALUE);
                    fruitButton.setPadding(Insets.EMPTY);

                    GridPane.setHalignment(stackPane, HPos.CENTER);
                    GridPane.setHgrow(stackPane, Priority.ALWAYS);
                    GridPane.setValignment(stackPane, VPos.CENTER);
                    GridPane.setVgrow(stackPane, Priority.ALWAYS);

                    this.boardView.add(fruitButton, j, i);

                }
            }
        }

        this.board = currentBoard;
        
    }

    // SAVE & EXIT
    public void saveExit(ActionEvent event) throws Exception {
        // Alert
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle("Save & Exit");
        alert.setHeaderText("You're about to exit the current game!");
        alert.setContentText("Do you want to save?");
        alert.setX(725);
        alert.setY(45);

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
        for (int i = 0; i < Constant.BOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.BOARD_COL_SIZE.getNum(); j++) {
                this.saveData += grid[i][j].getPiece().getName() + " ";
            }
        }
        return this.saveData;
    }

    // SWAP
    public void swap(ActionEvent event) {
        System.out.println("swap() fired");
        System.out.println(this.points[0].getRow() + " " + this.points[0].getCol());
        System.out.println(this.points[1].getRow() + " " + this.points[1].getCol());
        this.board.swapPiece(this.points[0], this.points[1]);
        swapImage(this.points[0], this.points[1]);
        this.pointIndex = 0;
        // correct
    }
    public void swapImage(BoardPoint p1, BoardPoint p2) {
        // cannot swap after shuffle
        if (p1.getRow() == p2.getRow() - 1 || p1.getRow() == p2.getRow() + 1 || p1.getCol() == p2.getCol() - 1 || p1.getCol() == p2.getCol() + 1) {
            StackPane stackPane1 = (StackPane)((Button)getNodeByRowColumnIndex(p1.getRow(), p1.getCol(), boardView)).getGraphic();
            ImageView fruitImageViewP1 = (ImageView)stackPane1.getChildren().get(1);
            fruitImageViewP1.setFitWidth(Constant.PICTURE_SIZE.getNum());
            fruitImageViewP1.setFitHeight(Constant.PICTURE_SIZE.getNum());

            StackPane stackPane2 = (StackPane)((Button)getNodeByRowColumnIndex(p2.getRow(), p2.getCol(), boardView)).getGraphic();
            ImageView fruitImageViewP2 = (ImageView)stackPane2.getChildren().get(1);
            fruitImageViewP2.setFitWidth(Constant.PICTURE_SIZE.getNum());
            fruitImageViewP2.setFitHeight(Constant.PICTURE_SIZE.getNum());

            Image patch = new Image(Constant.decorations.get("patch"));
            ImageView patchView1 = new ImageView(patch);
            ImageView patchView2 = new ImageView(patch);
            patchView1.setFitWidth(Constant.PICTURE_SIZE.getNum());
            patchView1.setFitHeight(Constant.PICTURE_SIZE.getNum());
            patchView2.setFitWidth(Constant.PICTURE_SIZE.getNum());
            patchView2.setFitHeight(Constant.PICTURE_SIZE.getNum());

            StackPane newStackPane1 = new StackPane(patchView1, fruitImageViewP2);
            StackPane newStackPane2 = new StackPane(patchView2, fruitImageViewP1);

            ((Button)getNodeByRowColumnIndex(p1.getRow(), p1.getCol(), boardView)).setGraphic(newStackPane1);
            ((Button)getNodeByRowColumnIndex(p2.getRow(), p2.getCol(), boardView)).setGraphic(newStackPane2);
        }
        else {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Unable to swap.");
            errorAlert.setContentText("Please select adjacent fruits to swap!");
            errorAlert.show();
            errorAlert.setX(725);
            errorAlert.setY(45);
        }
        
    }
    public void buttonHandler(BoardPoint point) {
        if (this.pointIndex > 1) {
            this.points[0] = this.points[1];
            this.pointIndex = 1;
        }
        this.points[this.pointIndex] = point;
        this.pointIndex++;
        // correct
    }
    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
    
        for (Node node : childrens) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
    
        return result;
    }
    public void setNodeByRowColumnIndex (final int row, final int column, GridPane gridPane, Node node) {
        ObservableList<Node> childrens = gridPane.getChildren();
    
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            Node currentNode = childrens.get(i);
            if (GridPane.getRowIndex(currentNode) == row && GridPane.getColumnIndex(currentNode) == column) {
                gridPane.getChildren().set(i, node);
                break;
            }
        }
    }

    // NEXT STEP
    public void replant(ActionEvent event) {
        System.out.println("replant");
    }

    // SHUFFLE
    public void shuffle(ActionEvent event) {
        if (this.shuffleCount > 0) {
            initiateBoard();
            this.shuffleCount--;
            this.shuffleLabel.setText(Integer.toString(this.shuffleCount));
        }
        else {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Shuffle unavailable.");
            errorAlert.setContentText("You have used all of the shuffle props for this game!");
            errorAlert.show();
        }
    }

}
