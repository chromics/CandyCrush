package view.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoadSceneController implements Initializable {
    private Stage stage;
    private Scene scene;
    // private List<gameFileInfo> gameInfoList = SaveLoadController.getFileList();
    @FXML
    private ListView<Button> loadListView;

    public void initialize(URL location, ResourceBundle resourceBundle) {
        // for (int i = 0; i < gameInfoList.size(); i++) {
        //     initializeLoadListView(gameInfoList.get(i).getName(), gameInfoList.get(i).getLevel());
        // }
    }

    public void backToStartScene(ActionEvent event) throws Exception {
        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/StartScene.fxml"));
        this.stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        this.scene = new Scene(startScene);
        stage.setScene(this.scene);
        stage.show();
    }

    public void initializeLoadListView(String fileName, Integer level) {
        // create new button for load game selection
        Button loadSelect = new Button();
        Font font = Font.font("HP Simplified Hans", FontWeight.NORMAL, 12);
        loadSelect.setText(fileName + level);
        loadSelect.setWrapText(true);
        loadSelect.setFont(font);

        // put the button in the listView
        ObservableList<Button> loadList = FXCollections.observableArrayList();
        loadList.add(loadSelect);
        this.loadListView.setItems(loadList);

    }
}

// extras:
// public void addFileName(String fileName) {
    //     loadFilesNameList.add(fileName);
    // }
    // public void addFileLevel(int level) {
    //     loadFilesLevelList.add(level);
    // }
    // public String getFileNameAtIndex(int index) {
    //     return loadFilesNameList.get(index);
    // }
    // public Integer getFileLevelAtIndex(int index) {
    //     return loadFilesLevelList.get(index);
    // }
    // public void initializeFileList() {
    //     // File saveFileDir = new File ("controller/saveFile");
    //     // // insert the save files directory path name ^^^
    //     // File[] files = saveFileDir.listFiles();

    //     // for (int i = 0; i < files.length; i++) {
    //     //     this.loadFilesNameList.add(files[i].getName());
    //     //     // this.loadFilesLevelList.add();
    //     //     // insert here method to get the levels of each file ^^^
    //     // }

    //     /*
    //      * List<gameFileInfo> gameInfoList = SaveLoadController.getFileList();
    //      * 
    //      * {Name, Level}
    //      * 
    //      * List<String[]> gameInfoList = 
    //      * 
    //      * for(GameInfoList gameInfo : gameInfoList){
    //      * generate button (gameinfo)
    //      * }
    //      */
    // }
