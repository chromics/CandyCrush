package view.viewController;

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
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Board;
import model.BoardPoint;
import data.constant.Constant;
import model.Cell;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import java.net.URL;

import model.Cell;
import model.Board;
import model.piece.*;
import model.BoardPoint;
import data.GameData;
import data.constant.Constant;
import controller.GameController;
import view.Main;

public class BoardSceneController implements Initializable {
    private Stage stage;
    private Scene scene;
    private int saveFileNumber = 1;
    private boolean saveFileExists;
    private String saveData = "";
    private BoardPoint selectedPoint1;
    private BoardPoint selectedPoint2;
    private Board board;
    private GameController gameController;
    private GameData gameData;

    @FXML
    GridPane boardView;
    @FXML
    Label shuffleLabel;
    @FXML
    Label currentScoreLabel;
    @FXML
    Label movesLeftLabel;
    @FXML
    Button hintsButton;
    // method: provideHint()
    // dunno how to do this yet need to learn css ¯\_(ツ)_/¯
    @FXML
    Label levelNumLabel;

    // INITIALIZE
    public void initialize(URL location, ResourceBundle resourceBundle) {
        selectedPoint1 = null;
        selectedPoint2 = null;        
    }
    
    public void initiateBoard(Cell[][] grid) {        
        for (int row = 0; row < gameData.getBoard_Row_Size(); row++) {
            for (int col = 0; col < gameData.getBoard_Col_Size(); col++) {
                if (grid[row][col].isPlayable()) {
                    setPieceImageAt(grid[row][col].getPiece().getImagePath(), new BoardPoint(row, col));
                }
            }
        }
    }

    public void set_Game_Info(GameController gameController){

        this.gameController = gameController;
        gameData = this.gameController.getGameData();
        
        board = gameData.getBoard();

        shuffleLabel.setText(Integer.toString(gameData.getShuffleLeft()));
        currentScoreLabel.setText(Integer.toString(gameData.getScore()));
        movesLeftLabel.setText(Integer.toString(gameData.getStepLeft()));
    }

    //-----------------------------------------------------------------------------------------------
    // Update Image Methods
    //-----------------------------------------------------------------------------------------------
    public void setPieceImageAt(String imagePath, BoardPoint point){
        int row = point.getRow();
        int col = point.getCol();

        StackPane stackPane = new StackPane();
    
        Image patch = new Image(Constant.decorations.get("patch"));
        ImageView patchView = new ImageView(patch);
        patchView.setFitWidth(Constant.PICTURE_SIZE.getNum());
        patchView.setFitHeight(Constant.PICTURE_SIZE.getNum());
        stackPane.getChildren().add(patchView);
        
        if(imagePath != null){
            Image fruit = new Image(imagePath);
            ImageView fruitView = new ImageView(fruit);
            fruitView.setFitWidth(Constant.PICTURE_SIZE.getNum());
            fruitView.setFitHeight(Constant.PICTURE_SIZE.getNum());
            stackPane.getChildren().add(fruitView);
        }
        
        Button fruitButton = new Button();
        BoardPoint currentPoint = new BoardPoint(row, col);
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
    
        this.boardView.add(fruitButton, col, row);
    }
    public void swapImage(BoardPoint point1, BoardPoint point2) {
        Piece piece1 = board.getPieceAt(point1);
        Piece piece2 = board.getPieceAt(point2);
        String imagePath1 = (piece1 == null)? null : piece1.getImagePath();
        String imagePath2 = (piece2 == null)? null : piece2.getImagePath();
        setPieceImageAt(imagePath1, point1);
        setPieceImageAt(imagePath2, point2);        
    }
    public void removeImage(BoardPoint point){
        setPieceImageAt(null, point);
    }
    //===============================================================================================
    
    // SWAP
    public void swap(ActionEvent event) {
        if(gameData.anyMatch()){
            generateAlert("Unable to swap.", "Press \"Next\" first to remove matches!");
            resetSelectedPoint();
        }
        else if(gameData.hasnotFall()){
            generateAlert("Unable to swap", "Press \"Next\" first to generate new pieces!");
            resetSelectedPoint();
        }
        else if(selectedPoint1 != null && selectedPoint2 != null){
            gameController.swapPieceOnBoard(selectedPoint1, selectedPoint2);
            resetSelectedPoint();
        }
        else{
            generateAlert("Unable to swap", "Please select at least two points!");
        }
    }
    
    
    // NEXT STEP
    public void next(ActionEvent event) {
        if(gameData.anyMatch()){
            gameController.removeMatches();
        }
        else if(gameData.hasnotFall()){
            gameController.fall();
        }
        else{
            generateAlert("No effect", "Create match first");
        }
    }
    
    // SHUFFLE
    public void shuffle(ActionEvent event) {
        int shuffleLeft = gameData.getShuffleLeft();
        if (shuffleLeft > 0) {
            gameController.initBoard();
            initiateBoard(board.getGrid());
            
            shuffleLeft--; //Decrease Shuffle Left
            
            gameData.setShuffleLeft(shuffleLeft);
            shuffleLabel.setText(Integer.toString(shuffleLeft));
        }
        else {
            generateAlert("Shuffle unavailable", "You have used all of the shuffle props for this game!");
        }
    }
    
    public void settings(ActionEvent event) throws Exception {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(this.stage);
        dialog.setResizable(false);
        dialog.setX(Main.stage.getX() + 675);
        dialog.setY(Main.stage.getY() + 265);
        
        Image dialogIcon = new Image("data/constant/image/settingsIcon.png");
        dialog.getIcons().add(dialogIcon);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/InGameSettingScene.fxml"));
        Parent boardScene = loader.load();

        InGameSettingSceneController settingsView = loader.getController();
        settingsView.setGameController(gameController);

        Scene scene = new Scene(boardScene);
        dialog.setScene(scene);
        dialog.show();
    }
    
    public void provideHint(ActionEvent event) {
        System.out.println("hint");
    }
    public void addScore() {
        currentScoreLabel.setText(Integer.toString(gameData.getScore()));
    }
    public void deductMovesLeft() {
        movesLeftLabel.setText(Integer.toString(gameData.getStepLeft()));
    }
    
//    //Win
//    public void winAlert() throws Exception {
//        // Alert
//        Alert alert = new Alert(AlertType.CONFIRMATION);
//
//        alert.setTitle("Level Cleared");
//        alert.setHeaderText("Congratulations! You Won The Game");
//        alert.setContentText("Proceed to next level?");
//        alert.setX(725);
//        alert.setY(45);
//
//        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
//        ((Button) alert.getDialogPane().lookupButton(ButtonType.YES)).setDefaultButton(true);
//
//        // Proceed to next level?
//        Optional <ButtonType> result = alert.showAndWait();
//
//        if (result.isPresent() && result.get() == ButtonType.YES) {
//            gameController.nextLevel();
//        }
//        // Exit
//        else if (result.isPresent() && result.get() == ButtonType.NO) {
//            // backToStartScene(event);
//        }
//
//    }
    // SAVE & EXIT
    public void saveExit(ActionEvent event) throws Exception {
        // Alert
        Alert alert = new Alert(AlertType.CONFIRMATION);
        
        alert.setTitle("Save & Exit");
        alert.setHeaderText("You're about to exit the current game!");
        alert.setContentText("Do you want to save?");
        alert.setX(Main.stage.getX() + 625);
        alert.setY(Main.stage.getY() - 55);
        
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
        for (int i = 0; i < gameData.getBoard_Row_Size(); i++) {
            for (int j = 0; j < gameData.getBoard_Col_Size(); j++) {
                //                this.saveData += grid[i][j].getPiece().getName() + " ";
            }
        }
        return this.saveData;
    }
    
    public void resetSelectedPoint(){
        selectedPoint1 = null;
        selectedPoint2 = null;
    }
    
    public void generateAlert(String header, String content){
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.show();
        errorAlert.setX(Main.stage.getX() + 625);
        errorAlert.setY(Main.stage.getY() - 55);
    }
    
    public void buttonHandler(BoardPoint point) {
        if(selectedPoint1 != null){
            if(selectedPoint2 == null && board.calculateDistance(selectedPoint1, point) == 1){
                selectedPoint2 = point;
            }
            else{
                selectedPoint1 = point;
                selectedPoint2 = null;
            }
        }
        else{
            selectedPoint1 = point;
        }
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
}
