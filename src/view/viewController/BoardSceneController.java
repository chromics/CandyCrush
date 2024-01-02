package view.viewController;

import java.util.*;

import javafx.application.Platform;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Translate;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.geometry.Insets;
import java.net.URL;

import view.Main;
import model.Cell;
import model.piece.*;
import model.Board;
import model.BoardPoint;
import data.GameData;
import data.constant.Constant;
import controller.GameController;
import controller.SaveLoadController;

import javax.sound.sampled.*;

public class BoardSceneController implements Initializable {
    private final static Image PATCH_IMAGE = new Image(Constant.decorations.get("patch"));
    private final static Image SELECT_MARK_IMAGE = new Image(Constant.decorations.get("patchSelectMark"));
    private final static Image ICE_BLOCK_IMAGE = new Image(Constant.decorations.get("iceBlock"));
    private final static String INFINITY_SYMBOL = "\u221E";
    private static int animation_Duration_In_Millis = Constant.ANIMATION_DURATION.getNum();
    private static boolean wait_For_Animation = false;
    private static Scene scene;
    
    private boolean specialMode;
    private Pane[][] paneArray;
    private ImageView[][] fruitsViewArray;
    private ImageView[][] iceBlockViewArray;
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
    public static MusicController musicController;

    @FXML
    private GridPane boardView;
    @FXML
    private Label shuffleLabel;
    @FXML
    private Label currentScoreLabel;
    @FXML
    private Label movesLeftLabel;
    @FXML
    private Button swapButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button hintsButton;
    @FXML
    private Button shuffleButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button homeButton;
    @FXML
    private Label levelNumLabel;
    @FXML
    private static AnchorPane boardPane;
    @FXML
    private Label targetScoreLabel;
    // methodnya gabung sama set current score
    @FXML
    private Label iceBlockLabel;
    @FXML
    private Text iceBlockText;
    @FXML
    private ImageView catImageView;
    @FXML
    private ImageView textBoxView;
    @FXML
    private ImageView longBoxView;
    @FXML
    private Label catDialog;

    private Timer catDialogTimer = new Timer();

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

    public void initiateBoard() {
        grid = board.getGrid();
        paneArray = new Pane[board_Row_Size][board_Col_Size];
        fruitsViewArray = new ImageView[board_Row_Size][board_Col_Size];
        iceBlockViewArray = new ImageView[board_Row_Size][board_Col_Size];
        
        setPictureSize();        
        setBoardViewConstrain();

        //Initiate the Pane of each gridPane Cell
        for(int row = 0; row < board_Row_Size; row++){
            for(int col = 0; col < board_Col_Size; col++){
                setGridViewAt(row, col);
            }
        }
        
        initiatePiecesView();
    }

    public void setGridViewAt(int row, int col){
        if(grid[row][col].isPlayable()){
            ImageView patchView = new ImageView(PATCH_IMAGE);
            patchView.setFitWidth(pictureSize);
            patchView.setFitHeight(pictureSize); 
            
            fruitsViewArray[row][col] = new ImageView();
            fruitsViewArray[row][col].setFitWidth(pictureSize);
            fruitsViewArray[row][col].setFitHeight(pictureSize);

            iceBlockViewArray[row][col] = new ImageView();
            iceBlockViewArray[row][col].setFitWidth(pictureSize);
            iceBlockViewArray[row][col].setFitHeight(pictureSize);

            if (specialMode && grid[row][col].has_Ice_Block()) {
                iceBlockViewArray[row][col].setImage(ICE_BLOCK_IMAGE);
            }

            paneArray[row][col] = new Pane(patchView, iceBlockViewArray[row][col], fruitsViewArray[row][col]);
            BoardPoint currentPoint = new BoardPoint(row, col);
            paneArray[row][col].setOnMouseClicked(e -> buttonHandler(currentPoint));
        
            GridPane.setHalignment(paneArray[row][col], HPos.CENTER);
            GridPane.setHgrow(paneArray[row][col], Priority.ALWAYS);
            GridPane.setValignment(paneArray[row][col], VPos.CENTER);
            GridPane.setVgrow(paneArray[row][col], Priority.ALWAYS);
        
            this.boardView.add(paneArray[row][col], col, row);

            //Initiate the visibility area of each cell
            int visibleAreaWidth = pictureSize * 2;
            int visibleAreaHeight = (row + 1 - gameData.get_Highest_Playable_Cell_At_Col(col)) * pictureSize - 1;

            Rectangle visibleArea = new Rectangle(visibleAreaWidth, visibleAreaHeight);
            visibleArea.setFill(Color.BLUE);
            visibleArea.setLayoutX(-pictureSize);
            visibleArea.setLayoutY(-visibleAreaHeight + pictureSize);

            paneArray[row][col].setClip(visibleArea);
        }
    }

    public void initiatePiecesView(){
        grid = board.getGrid();

        for (int row = 0; row < gameData.getBoard_Row_Size(); row++) {
            for (int col = 0; col < gameData.getBoard_Col_Size(); col++) {
                if (grid[row][col].containPiece()) {
                    setPieceImageAt(grid[row][col].getPiece().getName(), new BoardPoint(row, col));
                }
            }
        }
    }

    public void setPictureSize () {

        int board_Max_Size = Math.max(board_Row_Size, board_Col_Size);

        pictureSize = Constant.pictureSizeList.get(board_Max_Size);
        System.out.println("Picture Size : " + pictureSize);
    }

    public void setBoardViewConstrain () {

        int board_Max_Size = Math.max(board_Row_Size, board_Col_Size);
        for (int i = 0; i < board_Max_Size; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
            columnConstraints.setMinWidth(10.0);
            columnConstraints.setPrefWidth(100.0);
            boardView.getColumnConstraints().add(columnConstraints);

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(10.0);
            rowConstraints.setPrefHeight(30.0);
            rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
            boardView.getRowConstraints().add(rowConstraints);
        }

        if (board_Row_Size < board_Col_Size) {
            double changeY = (double)(board_Col_Size - board_Row_Size) / 2 * pictureSize;

            boardView.setLayoutY(boardView.getLayoutY() + changeY);
        }
        else if (board_Row_Size > board_Col_Size) {
            double changeX = (double)(board_Row_Size - board_Col_Size) / 2 * pictureSize;
    
            boardView.setLayoutX(boardView.getLayoutX() + changeX);
        }
    }

    public void set_Game_Info(GameController gameController){
        System.out.println("Set_Board_Scene_Controller_Game_Info");
        this.gameController = gameController;
        gameData = gameController.getGameData();
        
        board = gameData.getBoard();
        System.out.println("\nPassed Game Board : \n" + gameData.getBoard().txtBoard());
        board_Row_Size = gameData.getBoard_Row_Size();
        board_Col_Size = gameData.getBoard_Col_Size();
        specialMode = gameData.getSpecialMode();

        levelNumLabel.setText(Integer.toString(gameData.getLevelNum()));
        currentScoreLabel.setText(Integer.toString(gameData.getScore()) + " / " + gameData.getTargetScore());
        movesLeftLabel.setText(Integer.toString(gameData.getRemainingStep()));

        if (gameData.getRemainingShuffle() >= 0){
            shuffleLabel.setText(Integer.toString(gameData.getRemainingShuffle()));
        }
        else {
            shuffleLabel.setText(INFINITY_SYMBOL);
        }

        if (! specialMode) {
            iceBlockLabel.setVisible(false);
            iceBlockText.setVisible(false);
        }
        else {
            iceBlockLabel.setText(gameData.getIceBlockDestroyed() + " / " + gameData.getTotalIceBlock());
        }
    }

    //-----------------------------------------------------------------------------------------------
    // Update Image Methods
    //-----------------------------------------------------------------------------------------------
    public void setPieceImageAt (String pieceName, BoardPoint point) {

        if (board.is_Cell_Playable(point)) {
            fruitsViewArray[point.getRow()][point.getCol()].setImage(Constant.piecesImageHashMap.get(pieceName));
        }
    }
    public void setIceBlockAt (BoardPoint point) {    
        
        if (board.is_Cell_Playable(point)) {
            iceBlockViewArray[point.getRow()][point.getCol()].setImage(ICE_BLOCK_IMAGE);
        }
    }
    public void removeIceBlockAt (BoardPoint point) {
        SFXController.initializePlay("SFX/iceBreakSFX.wav");
        SFXController.play();

        if (board.is_Cell_Playable(point)) {
            iceBlockViewArray[point.getRow()][point.getCol()].setImage(null);
        }
    }
    public void swapImage (BoardPoint point1, BoardPoint point2) {
        // insert audioclip        
        translateAnimation(point1, point2);
        translateAnimation(point2, point1);

        // setPieceImageAt(pieceName1, point1);
        // setPieceImageAt(pieceName2, point2);
    }
    public void fallImage (BoardPoint src, BoardPoint dest) {
        translateAnimation(src, dest);
    }
    public void removeImage(BoardPoint point){
        setPieceImageAt(null, point);
    }
    public void addSelectMarkImage(BoardPoint point){
        ImageView selectMarkView = new ImageView(SELECT_MARK_IMAGE);
        selectMarkView.setFitWidth(pictureSize);
        selectMarkView.setFitHeight(pictureSize); 
        paneArray[point.getRow()][point.getCol()].getChildren().add(selectMarkView);
    }
    public void removeSelectMarkImage(BoardPoint point){
        int selectMarkIndex = paneArray[point.getRow()][point.getCol()].getChildren().size() - 1;
        paneArray[point.getRow()][point.getCol()].getChildren().remove(selectMarkIndex);
    }
    public void translateAnimation (BoardPoint src, BoardPoint dest) {
        System.out.println("Animation played from : " + src.toString() + " to " + dest.toString());

        int distanceX = board.getDistanceX(src, dest);
        int distanceY = board.getDistanceY(src, dest);

        System.out.println("Distance X : " + distanceX);
        System.out.println("Distance Y : " + distanceY);

        String pieceName = null;
        if (grid[dest.getRow()][dest.getCol()].getPiece() != null) {

            pieceName = grid[dest.getRow()][dest.getCol()].getPiece().getName();
        }

        String finalPieceName = pieceName;

        ImageView tempView = new ImageView(Constant.piecesImageHashMap.get(pieceName));
        tempView.setFitWidth(pictureSize);
        tempView.setFitHeight(pictureSize);

        if (distanceX > 0 || distanceY > 0) {
            paneArray[dest.getRow()][dest.getCol()].getChildren().add(2, tempView);

            tempView.setLayoutX(tempView.getLayoutX() - (pictureSize * distanceX));
            tempView.setLayoutY(tempView.getLayoutY() - (pictureSize * distanceY));
        }
        else {
            paneArray[src.getRow()][src.getCol()].getChildren().add(2, tempView);
        }
        if (board.is_Within_Boundary(src)) {
            setPieceImageAt(null, src);
        }
        
        int duration_Factor = Math.max(Math.abs(distanceX), Math.abs(distanceY));

        Duration animation_Duration = Duration.millis(animation_Duration_In_Millis * duration_Factor);

        TranslateTransition swapTransition  = new TranslateTransition(animation_Duration, tempView);
        swapTransition.setToX(board.getDistanceX(src, dest) * pictureSize);
        swapTransition.setToY(board.getDistanceY(src, dest) * pictureSize);

        swapTransition.setOnFinished(event -> {
            if (distanceX > 0 || distanceY > 0) {
                paneArray[dest.getRow()][dest.getCol()].getChildren().remove(2);
            }
            else {
                paneArray[src.getRow()][src.getCol()].getChildren().remove(2);
            }
            setPieceImageAt(finalPieceName, dest);
            System.out.println("Transition Finished");
        });
                
        swapTransition.play();
    }
    //===============================================================================================
    

    // SWAP
    public void swap(ActionEvent event) {
        if(gameData.anyMatch()){

            catDialog("longBox","Press \"Next\" first to remove matches!", 505, 140);
            setCatTimer("longBox",1500);  
                      
            resetSelectedPoint();
        }
        else if(gameData.hasnotFall()){

            catDialog("longBox","Press \"Next\" first to drop pieces!", 505, 140);
            setCatTimer("longBox",1500);

            resetSelectedPoint();
        }
        else if(selectedPoint1 != null && selectedPoint2 != null){
            new Thread(() -> gameController.swapPieceOnBoard(selectedPoint1, selectedPoint2)).start();
        }
        else{
            catDialog("longBox","Please select at least two points!", 505, 140);
            setCatTimer("longBox",1500);
        }
    }
    
    
    // NEXT STEP
    public void next(ActionEvent event) {
        resetSelectedPoint();

        if(gameData.anyMatch()){
            new Thread(() -> gameController.removeMatches()).start();
            SFXController.initializePlay("SFX/matchEliminateSFX.wav");
            SFXController.play();

            gameController.removeMatches();
        }
        else if(gameData.hasnotFall()){
            new Thread(() -> gameController.fall()).start();
            SFXController.initializePlay("SFX/fallSFX.wav");
            SFXController.play();
        }
        else{
            // UtilView.generateErrorAlert("No effect", "Create match first");
            catDialog("longBox","Create match first!", 505, 140);
            setCatTimer("longBox",1500);
        }
    }

    // Hints
    public void provideHint (ActionEvent event) {
        SFXController.initializePlay("SFX/buttonClickSFX.wav");
        SFXController.play();

        if (gameData.anyMatch()) {
            catDialog("longBox","Press \"Next\" first to remove matches !", 505, 140);
            setCatTimer("longBox",1500);
        }
        else if (gameData.hasnotFall()) {
            catDialog("longBox","Press \"Next\" first to drop Pieces !", 505, 140);
            setCatTimer("longBox",1500);
        }
        else if (gameData.anyHint()) {
            BoardPoint[] hintPosition = gameData.getHint();
            int remainingHints = gameData.getRemainingHints();
    
            resetSelectedPoint();
    
            selectedPoint1 = hintPosition[0];
            selectedPoint2 = hintPosition[1];
            addSelectMarkImage(selectedPoint1);
            addSelectMarkImage(selectedPoint2);

            remainingHints--;
            gameData.setRemainingHints(remainingHints);
        }
        else {
            catDialog("longBox","No Hint !", 505, 140);
            setCatTimer("longBox",1500);
        }
    }
    
    // SHUFFLE
    public void shuffle (ActionEvent event) {
        SFXController.initializePlay("SFX/swapSFX.wav");
        SFXController.play();

        if (gameData.anyShuffle()) {
            int shuffleLeft = gameData.getRemainingShuffle();

            resetSelectedPoint();
            gameData.resetMatchData();
            gameData.resetFallData();

            gameController.initBoard();
            System.out.println("Controller Board : \n" + board.txtBoard());
            initiatePiecesView();
            
            shuffleLeft--; //Decrease remaining Shuffle
            
            gameData.setRemainingShuffle(shuffleLeft);

            if (shuffleLeft >= 0) {
                shuffleLabel.setText(Integer.toString(shuffleLeft));
            }
            else {
                shuffleLabel.setText(INFINITY_SYMBOL);
            }
        }
        else {
            catDialog("longBox","All shuffle props have been used !", 505, 140);
            setCatTimer("longBox",1500);
        }
    }
    
    public void settings(ActionEvent event) throws Exception {
        SFXController.initializePlay("SFX/buttonClickSFX.wav");
        SFXController.play();

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Main.stage);
        dialog.setResizable(false);
        dialog.setX(Main.stage.getX() - 120);
        dialog.setY(Main.stage.getY() + 160);
        
        Image dialogIcon = new Image("data/constant/image/settingsIcon.png");
        dialog.getIcons().add(dialogIcon);
        dialog.setTitle("Game Settings");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/InGameSettingScene.fxml"));
        Parent boardScene = loader.load();
        
        InGameSettingSceneController settingsView = loader.getController();
        settingsView.setGameController(gameController);

        Scene scene = new Scene(boardScene);
        dialog.setScene(scene);
        dialog.show();
    }
    
    public void addScore() {
        currentScoreLabel.setText(Integer.toString(gameData.getScore()) + " / " + Integer.toString(gameData.getTargetScore()));
    }
    public void deductMovesLeft() {
        movesLeftLabel.setText(Integer.toString(gameData.getRemainingStep()));
    }
    public void update_Ice_Block_Destroyed() {
        iceBlockLabel.setText(gameData.getIceBlockDestroyed() + " / " + gameData.getTotalIceBlock());
    }

    
    //-----------------------------------------------------------------------------------------------
    // Button Activation & Deactivation
    //-----------------------------------------------------------------------------------------------
    
    // Activate Swap & Next Button
    public void activate_Swap_Next_Button () {
        swapButton.setDisable(false);
        nextButton.setDisable(false);
    }
    
    // Activate user ability to interact with board
    public void activate_Board_Select_Point () {
        // Activate Button Handler
        wait_For_Animation = false;
    }
    
    // Activate Shuffle & Hint Button
    public void activate_Shuffle_Hint_Button () {
        shuffleButton.setDisable(false);
        hintsButton.setDisable(false);
    }
    
    // Activate Home & Settings Button
    public void activate_Home_Settings_Button () {
        homeButton.setDisable(false);
        settingsButton.setDisable(false);
    }

    public void activate_All_Buttons () {
        activate_Home_Settings_Button();
        activate_Shuffle_Hint_Button();
        activate_Swap_Next_Button();
        activate_Board_Select_Point();
    }

    // Dectivate Swap & Next Button
    public void deactivate_Swap_Next_Button () {
        swapButton.setDisable(true);
        nextButton.setDisable(true);
    }
    
    // Dectivate user ability to interact with board
    public void deactivate_Board_Select_Point () {
        // Deactivate Button Handler
        wait_For_Animation = true;
    }
    
    // Dectivate Shuffle & Hint Button
    public void deactivate_Shuffle_Hint_Button () {
        shuffleButton.setDisable(true);
        hintsButton.setDisable(true);
    }
    
    // Dectivate Home & Settings Button
    public void deactivate_Home_Settings_Button () {
        homeButton.setDisable(true);
        settingsButton.setDisable(true);
    }

    public void deactivate_All_Buttons () {
        deactivate_Home_Settings_Button();
        deactivate_Shuffle_Hint_Button();
        deactivate_Swap_Next_Button();
        deactivate_Board_Select_Point();
    }
    //===============================================================================================
    

    public void saveExit(ActionEvent event) throws Exception {
        SFXController.initializePlay("SFX/buttonClickSFX.wav");
        SFXController.play();

        System.out.println("\nSave & Exit from BoardScene\n");
        // Alert
        System.out.println("Save Exit Alert");
        Alert alert = new Alert(AlertType.CONFIRMATION);
        
        Image dialogIcon = new Image(Constant.catHashMap.get("sadCat"));
        ImageView dialogView = new ImageView(dialogIcon);
        dialogView.setFitHeight(80);
        dialogView.setFitWidth(100);
        alert.getDialogPane().setGraphic(dialogView);

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
            backToStartScene();
        }
    }

    public void saveGame(String fileName){
        SaveLoadController.save_Game(gameData, fileName);
    }

    public static void backToStartScene() throws Exception {
        Parent startScene = FXMLLoader.load(BoardSceneController.class.getResource("/view/fxml/StartScene.fxml"));
        scene = new Scene(startScene);
        Main.stage.setScene(scene);
        Main.stage.show();
        BoardSceneController.stopMusic();
        StartSceneController.initMusic();
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
        SFXController.initializePlay("SFX/selectSFX.wav");
        SFXController.play();

        if(board.any_piece(point) && ! wait_For_Animation){

            if (selectedPoint1 != null) {
                if (selectedPoint2 == null){
                    if(board.calculateDistance(selectedPoint1, point) == 1){
                        addSelectMarkImage(point);
                        selectedPoint2 = point;

                        if (gameData.getAutomaticMode()) {
                            new Thread(() -> gameController.swapPieceOnBoard(selectedPoint1, selectedPoint2)).start();
                        }
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

    // normal win/lose scenarios
    public void win() throws Exception {
        SFXController.initializePlay("SFX/victorySFX.wav");
        SFXController.play();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/WinScene.fxml"));
        Parent startScene = loader.load();
        WinSceneController controller = loader.getController();

        scene = new Scene(startScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }
    public void lose() throws Exception {
        SFXController.initializePlay("SFX/loseSFX.wav");
        SFXController.play();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/LoseScene.fxml"));
        Parent startScene = loader.load();
        LoseSceneController controller = loader.getController();

        scene = new Scene(startScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }
    public void finalWin() throws Exception {
        SFXController.initializePlay("SFX/victorySFX.wav");
        SFXController.play();

        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/FinalWinScene.fxml"));
        scene = new Scene(startScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }

    // special win/lose scenarios
    public void specialWin() throws Exception {
        SFXController.initializePlay("SFX/victorySFX.wav");
        SFXController.play();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/SpecialWinScene.fxml"));
        Parent startScene = loader.load();
        WinSceneController controller = loader.getController();

        scene = new Scene(startScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }
    public void specialLose() throws Exception {
        SFXController.initializePlay("SFX/loseSFX.wav");
        SFXController.play();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/SpecialLoseScene.fxml"));
        Parent startScene = loader.load();
        LoseSceneController controller = loader.getController();

        scene = new Scene(startScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }
    public void specialFinalWin() throws Exception {
        SFXController.initializePlay("SFX/victorySFX.wav");
        SFXController.play();

        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/FinalSpecialWinScene.fxml"));
        scene = new Scene(startScene);
        Main.stage.setScene(scene);
        Main.stage.show();
    }
    public void catDialog(String boxType, String message, double dialogX, double dialogY) {
        SFXController.initializePlay("SFX/meowSFX.wav");
        SFXController.setInitVolume(0.2F);
        SFXController.play();

        Image meowCat = new Image("/data/constant/image/meowCat.png");
        catImageView.setImage(meowCat);

        if (boxType.equals("longBox")) {
            Image textBox = new Image("/data/constant/image/longBox.png");
            longBoxView.setImage(textBox);
            longBoxView.setLayoutX(482);
            longBoxView.setLayoutY(123);
            catDialog.setMaxWidth(220);
        }
        else if (boxType.equals("shortBox")) {
            Image textBox = new Image("/data/constant/image/textBox.png");
            textBoxView.setImage(textBox);
            textBoxView.setLayoutX(602);
            textBoxView.setLayoutY(131);
            catDialog.setMaxWidth(60);
        }

        catDialog.setLayoutX(dialogX);
        catDialog.setLayoutY(dialogY);
        catDialog.setText(message);
    }

    public void catDefaultState(String boxType) {
        Image defaultCat = new Image("/data/constant/image/defaultCat.png");
        catImageView.setImage(defaultCat);

        if (boxType.equals("longBox")) {
            longBoxView.setImage(null);
            Platform.runLater(() -> {
                catDialog.setText(null);
            });
        }
        else if (boxType.equals("shortBox")) {
            textBoxView.setImage(null);
            Platform.runLater(() -> {
                catDialog.setText(null);
            });
        }

    }
    public void setCatTimer(String boxType, long delay) {
        TimerTask task = new TimerTask()
        {
            public void run()
            {
                catDefaultState(boxType);
            }

        };
        catDialogTimer.schedule(task,delay);
    }
    public static void initMusic() throws Exception {
        musicController = new MusicController(VolumeController.getBoardSceneMusicVolume());
        musicController.initMusicController("/data/constant/audio/colors.wav"); 
        musicController.playMusic();
    }
    public static void stopMusic() {
        musicController.stopMusic();
    }

    public static void initSpecialMusic() throws Exception {
        musicController = new MusicController(VolumeController.getBoardSceneMusicVolume());
        musicController.initMusicController("/data/constant/audio/christmas.wav");
        musicController.playMusic();
    }

}