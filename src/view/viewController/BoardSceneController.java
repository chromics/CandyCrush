package view.viewController;

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
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Board;
import model.BoardPoint;
import data.constant.Constant;
import model.Cell;
import javafx.scene.image.*;
import javafx.geometry.Insets;
import java.net.URL;

import model.piece.*;
import data.GameData;
import controller.GameController;
import controller.SaveLoadController;
import view.Main;

import javax.sound.sampled.*;

public class BoardSceneController implements Initializable {
    private final static Image PATCHIMAGE = new Image(Constant.decorations.get("patch"));
    private final static Image SELECTMARKIMAGE = new Image(Constant.decorations.get("patchSelectMark"));
    private static Scene scene;
    
    private Pane[][] paneArray;
    private int saveFileNumber = 1;
    private boolean saveFileExists;
    private String saveData = "";
    private BoardPoint selectedPoint1;
    private BoardPoint selectedPoint2;
    private Board board;
    private Cell[][] grid;
    private GameController gameController;
    private static GameData gameData;
    private int pictureSize;
    private int board_Row_Size;
    private int board_Col_Size;
    private AudioClip sFX;
    private Clip music;

    @FXML
    private GridPane boardView;
    @FXML
    private Label shuffleLabel;
    @FXML
    private Label currentScoreLabel;
    @FXML
    private Label movesLeftLabel;
    @FXML
    private Button hintsButton;
    // method: provideHint()
    // dunno how to do this yet need to learn css ¯\_(ツ)_/¯
    @FXML
    private Label levelNumLabel;
    @FXML
    private static AnchorPane boardPane;

    private static AudioClip buttonClick;

    // INITIALIZE
    public void initialize(URL location, ResourceBundle resourceBundle) {
        selectedPoint1 = null;
        selectedPoint2 = null;        
    }
    public static AnchorPane getBoardPane() {
        return boardPane;
    }
    public static Scene getScene() {
        return scene;
    }
    public static void setScene(Scene newScene) {
        scene = newScene;
    }
    public static GameData getGameData() {
        return gameData;
    }

    private void initializeBackgroundMusic() {

    }
    public void initiateBoard() {
        grid = board.getGrid();
        paneArray = new Pane[board_Row_Size][board_Col_Size];

        // for (int row = 0; row < board_Row_Size; row++) {
        //     RowConstraints rowConst = new RowConstraints(gameData.getBoard_Row_Size());
        //     boardView.getRowConstraints().add(rowConst);
        // }
        // for (int col = 0; col < board_Col_Size; col++) {
        //     ColumnConstraints colConst = new ColumnConstraints(gameData.getBoard_Col_Size());
        //     boardView.getColumnConstraints().add(colConst);
        // }

        setPictureSize(gameData.getBoard_Row_Size(), gameData.getBoard_Col_Size());        

        for(int row = 0; row < board_Row_Size; row++){
            for(int col = 0; col < board_Col_Size; col++){
                setGridViewAt(row, col);
            }
        }
        // initiate boardView with getBoard_Row_Size() & col
        initiatePiecesView();
    }

    public void setGridViewAt(int row, int col){
        if(grid[row][col].isPlayable()){
            ImageView patchView = new ImageView(PATCHIMAGE);
            patchView.setFitWidth(pictureSize);
            patchView.setFitHeight(pictureSize); 
            
            paneArray[row][col] = new Pane(patchView);

            Button fruitButton = new Button();
            BoardPoint currentPoint = new BoardPoint(row, col);
            fruitButton.setOnAction(e -> buttonHandler(currentPoint));
            fruitButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            fruitButton.setGraphic(paneArray[row][col]);
            fruitButton.maxWidth(Double.MAX_VALUE);
            fruitButton.maxHeight(Double.MAX_VALUE);
            fruitButton.setPadding(Insets.EMPTY);
        
            GridPane.setHalignment(paneArray[row][col], HPos.CENTER);
            GridPane.setHgrow(paneArray[row][col], Priority.ALWAYS);
            GridPane.setValignment(paneArray[row][col], VPos.CENTER);
            GridPane.setVgrow(paneArray[row][col], Priority.ALWAYS);
        
            this.boardView.add(fruitButton, col, row);
        }
    }

    public void initiatePiecesView(){
        for (int row = 0; row < gameData.getBoard_Row_Size(); row++) {
            for (int col = 0; col < gameData.getBoard_Col_Size(); col++) {
                if (grid[row][col].isPlayable()) {
                    setPieceImageAt(grid[row][col].getPiece().getImagePath(), new BoardPoint(row, col));
                }
            }
        }
    }

    public void setPictureSize(int board_Row_Size, int board_Col_Size){
        int board_Max_Size = Math.max(board_Row_Size, board_Col_Size);

        pictureSize = Constant.pictureSizeList.get(board_Max_Size);
        System.out.println("Picture Size : " + pictureSize);
    }

    public void set_Game_Info(GameController gameController){

        this.gameController = gameController;
        gameData = this.gameController.getGameData();
        
        board = gameData.getBoard();
        board_Row_Size = gameData.getBoard_Row_Size();
        board_Col_Size = gameData.getBoard_Col_Size();

        shuffleLabel.setText(Integer.toString(gameData.getRemainingShuffle()));
        currentScoreLabel.setText(Integer.toString(gameData.getScore()));
        movesLeftLabel.setText(Integer.toString(gameData.getRemainingStep()));
    }

    //-----------------------------------------------------------------------------------------------
    // Update Image Methods
    //-----------------------------------------------------------------------------------------------
    public void setPieceImageAt(String imagePath, BoardPoint point){
        int row = point.getRow();
        int col = point.getCol();
        
        if(paneArray[row][col].getChildren().size() > 1){
            System.out.println("Remove Fruit Image at : " + point.toString());
            paneArray[row][col].getChildren().remove(1);
        }

        if(imagePath != null){
            Image fruit = new Image(imagePath);
            ImageView fruitView = new ImageView(fruit);
            fruitView.setFitWidth(pictureSize);
            fruitView.setFitHeight(pictureSize);
            paneArray[row][col].getChildren().add(1,fruitView);
        }
    }
    public void swapImage(BoardPoint point1, BoardPoint point2) {
        // insert audioclip
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
    public void addSelectMarkImage(BoardPoint point){
        ImageView selectMarkView = new ImageView(SELECTMARKIMAGE);
        selectMarkView.setFitWidth(pictureSize);
        selectMarkView.setFitHeight(pictureSize); 
        paneArray[point.getRow()][point.getCol()].getChildren().add(selectMarkView);
    }
    public void removeSelectMarkImage(BoardPoint point){
        int selectMarkIndex = paneArray[point.getRow()][point.getCol()].getChildren().size() - 1;
        paneArray[point.getRow()][point.getCol()].getChildren().remove(selectMarkIndex);
    }
    //===============================================================================================
    

    //-----------------------------------------------------------------------------------------------
    // Swap Methods
    //-----------------------------------------------------------------------------------------------
    
    //===============================================================================================
    // SWAP
    public void swap(ActionEvent event) {
        if(gameData.anyMatch()){
            UtilView.generateErrorAlert("Unable to swap.", "Press \"Next\" first to remove matches!");
            resetSelectedPoint();
        }
        else if(gameData.hasnotFall()){
            UtilView.generateErrorAlert("Unable to swap", "Press \"Next\" first to generate new pieces!");
            resetSelectedPoint();
        }
        else if(selectedPoint1 != null && selectedPoint2 != null){
            gameController.swapPieceOnBoard(selectedPoint1, selectedPoint2);
        }
        else{
            UtilView.generateErrorAlert("Unable to swap", "Please select at least two points!");
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
            UtilView.generateErrorAlert("No effect", "Create match first");
        }

        selectedPoint1 = null;
        selectedPoint2 = null;
    }

    // Hints
    public void provideHint (ActionEvent event) {
        if(gameData.anyHint()){
            BoardPoint[] hintPosition = gameData.getHint();
    
            resetSelectedPoint();
    
            selectedPoint1 = hintPosition[0];
            selectedPoint2 = hintPosition[1];
            addSelectMarkImage(selectedPoint1);
            addSelectMarkImage(selectedPoint2);
        }
    }
    
    // SHUFFLE
    public void shuffle(ActionEvent event) {
        int shuffleLeft = gameData.getRemainingShuffle();
        if (shuffleLeft > 0) {
            gameController.initBoard();
            initiatePiecesView();
            
            shuffleLeft--; //Decrease Shuffle Left
            
            gameData.resetMatchData();
            gameData.resetFallData();
            gameData.setRemainingShuffle(shuffleLeft);
            shuffleLabel.setText(Integer.toString(shuffleLeft));
        }
        else {
            UtilView.generateErrorAlert("Shuffle unavailable", "You have used all of the shuffle props for this game!");
        }
    }
    
    public void settings(ActionEvent event) throws Exception {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Main.stage);
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
    
    public void addScore() {
        currentScoreLabel.setText(Integer.toString(gameData.getScore()));
    }
    public void deductMovesLeft() {
        movesLeftLabel.setText(Integer.toString(gameData.getRemainingStep()));
    }
    
    public void saveExit(ActionEvent event) throws Exception {
        buttonClick.play();
        System.out.println("\nSave & Exit from BoardScene\n");
        // Alert
        System.out.println("SaveExitAlert");
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
            System.out.println("Save Action");
            SaveFileInputDialogController.generateSaveFileNameTextField("homeButton");
        }
        else if (result.isPresent() && result.get() == ButtonType.NO){
            // Exit
            backToStartScene(event);
        }
    }

    public void saveGame(String fileName){
        SaveLoadController.saveGame(gameData, fileName);
    }

    public void backToStartScene(ActionEvent event) throws Exception {
        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/StartScene.fxml"));
        Main.stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        scene = new Scene(startScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }
    
    public void resetSelectedPoint(){
        if (selectedPoint1 != null) {
            removeSelectMarkImage(selectedPoint1);
        }
        if (selectedPoint2 != null) {
            removeSelectMarkImage(selectedPoint2);
        }

        selectedPoint1 = null;
        selectedPoint2 = null;
    }
    
    public void buttonHandler(BoardPoint point) {
        if(board.any_piece(point)){

            if (selectedPoint1 != null) {
                if (selectedPoint2 == null){
                    if(board.calculateDistance(selectedPoint1, point) == 1){
                        addSelectMarkImage(point);
                        selectedPoint2 = point;
                    }
                    else {
                        removeSelectMarkImage(selectedPoint1);
                        addSelectMarkImage(point);
                        selectedPoint1 = point;
                    }
                } 
                else if (selectedPoint2 != null) {
                    if (board.calculateDistance(selectedPoint1, point) == 1){
                        removeSelectMarkImage(selectedPoint2);
                        addSelectMarkImage(point);
                        selectedPoint2 = point;
                    }
                    else if (board.calculateDistance(selectedPoint2, point) == 1){
                        removeSelectMarkImage(selectedPoint1);
                        addSelectMarkImage(point);
                        selectedPoint1 = selectedPoint2;
                        selectedPoint2 = point;
                    }
                    else{
                        removeSelectMarkImage(selectedPoint1);
                        removeSelectMarkImage(selectedPoint2);
                        addSelectMarkImage(point);
                        selectedPoint1 = point;
                        selectedPoint2 = null;
                    }
                }
            }
            else {
                addSelectMarkImage(point);
                selectedPoint1 = point;
            }
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

    // normal win/lose scenarios
    public void win() throws Exception {
        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/WinScene.fxml"));
        scene = new Scene(startScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }
    public void lose() throws Exception {
        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/LoseScene.fxml"));
        scene = new Scene(startScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }
    public void finalWin(ActionEvent event) throws Exception {
        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/FinalWinScene.fxml"));
        scene = new Scene(startScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }

    // special win/lose scenarios
    public void specialWin() throws Exception {
        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/SpecialWinScene.fxml"));
        scene = new Scene(startScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }
    public void specialLose() throws Exception {
        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/SpecialLoseScene.fxml"));
        scene = new Scene(startScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }
    public void specialFinalWin() throws Exception {
        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/FinalSpecialWinScene.fxml"));
        scene = new Scene(startScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }

}
