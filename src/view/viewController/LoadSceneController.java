package view.viewController;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import java.util.List;

import view.Main;
import data.GameData;
import data.GameFileInfo;
import data.constant.GameMode;
import data.constant.Level;
import controller.GameController;
import controller.SaveLoadController;

public class LoadSceneController implements Initializable {
    private Scene scene;
    // private Button selectButton;
    private GameFileInfo selectedGameFile;
    
    @FXML
    private ListView<Button> loadListView;

    public void initialize(URL location, ResourceBundle resourceBundle) {
        try {
            initializeLoadListView();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void backToStartScene(ActionEvent event) throws Exception {
        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/StartScene.fxml"));
        Main.stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        this.scene = new Scene(startScene);
        Main.stage.setScene(this.scene);
        Main.stage.show();
    }

    public void initializeLoadListView() throws NullPointerException, Exception {
        // create new button for load game selection
        List<GameFileInfo> gameFileList =  SaveLoadController.load_Game_File_List();
        ObservableList<Button> loadList = FXCollections.observableArrayList();

        for(GameFileInfo gameFile : gameFileList){
            Button selectFile = new Button();
            selectFile.setOnAction(e -> {
                try {
                    selectGameFile(gameFile);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            
            Font font = Font.font("HP Simplified Hans", FontWeight.NORMAL, 12);
            selectFile.setText(gameFile.getFileNameDisplay());
            selectFile.setWrapText(true);
            selectFile.setFont(font);

            selectFile.maxWidth(Double.MAX_VALUE);
            selectFile.maxHeight(Double.MAX_VALUE);
            selectFile.setPadding(Insets.EMPTY);
        }

        // // put the button in the listView
        loadListView.setItems(loadList);
    }

    public void loadFile(ActionEvent event) throws Exception {
        if(selectedGameFile != null){
            System.out.println("Load File : ");
            System.out.println(selectedGameFile.getFileNameDisplay());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/BoardScene.fxml"));
            Parent boardScene = loader.load();
            
            BoardSceneController view = loader.getController();
            GameController gameController = new GameController(view, selectedGameFile.getGameData());
        
            Main.stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
            scene = new Scene(boardScene);
            Main.stage.setScene(this.scene);
            Main.stage.show();
        } 
        else {
            UtilView.generateAlert("No Effect", "Please select a file to be loaded");
        }
    }
    
    public void deleteFile(ActionEvent event) throws Exception {
        if(selectedGameFile != null){
            System.out.println("Delete File");
            System.out.println(selectedGameFile.getFileNameDisplay());
    
        } else {
            UtilView.generateAlert("No Effect", "Please select a file to be loaded");
        }
    }

    public void selectGameFile(GameFileInfo selectedGameFile) throws Exception {
        this.selectedGameFile = selectedGameFile;
    }
}
