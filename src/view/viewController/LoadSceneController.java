package view.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import java.util.Map;
import java.util.HashMap;

import view.Main;
import data.GameData;
import controller.GameController;
import controller.SaveLoadController;
import controller.SaveLoadControllerSer;

public class LoadSceneController implements Initializable {
    private Scene scene;
    // private Button selectButton;
    private Button selectedButton;
    private String selectedGameFileName;
    private Map<String, String> fileNameList;
    private ObservableList<HBox> loadList;
    
    @FXML
    private ListView<HBox> loadListView;

    public void initialize(URL location, ResourceBundle resourceBundle) {
        try {
            initializeLoadListView();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void backToStartScene(ActionEvent event) throws Exception {
        SFXController.initializePlay("SFX/buttonClickSFX.wav");
        SFXController.play();

        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/StartScene.fxml"));
        Main.stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        this.scene = new Scene(startScene);
        Main.stage.setScene(this.scene);
        Main.stage.show();
    }

    public void initializeLoadListView() throws NullPointerException, Exception {
        // create new button for load game selection
        // List<GameFileInfo> gameFileList =  SaveLoadControllerSer.load_Game_File_List();
        //! No Saved File Message to user
        fileNameList = SaveLoadController.load_File_Name_List();
        loadList = FXCollections.observableArrayList();

        System.out.println("File Name List : ");
        for(String fileName : fileNameList.keySet()){
            System.out.println(fileName);
            System.out.println(fileNameList.get(fileName));
            System.out.println();
        }
        System.out.println();

        for(String fileName : fileNameList.keySet()){

            Button selectFile = new Button();
            selectFile.setOnAction(e -> {
                selectGameFile(selectFile, fileName);
            });
            
            Font font = Font.font("HP Simplified Hans", FontWeight.NORMAL, 12);
            selectFile.setText(fileNameList.get(fileName));
            selectFile.setWrapText(true);
            selectFile.setFont(font);

            selectFile.setAlignment(Pos.CENTER_LEFT);
            selectFile.setMaxWidth(Double.MAX_VALUE);
            selectFile.setMaxHeight(Double.MAX_VALUE);
//            selectFile.setPadding(Insets.EMPTY);

            HBox hBox = new HBox();
            HBox.setHgrow(selectFile, Priority.ALWAYS);
            hBox.getChildren().add(selectFile);
//            hBox.setMinWidth(1000);

            loadList.add(hBox);

            SaveLoadController.loadGame(fileName);
        }
        loadListView.setItems(loadList);
    }

    public void loadFile(ActionEvent event) throws Exception {
        SFXController.initializePlay("SFX/buttonClickSFX.wav");
        SFXController.play();

        if(selectedGameFileName != null){
            System.out.println("Load File : ");
            System.out.println(selectedGameFileName);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/BoardScene.fxml"));
            Parent boardScene = loader.load();
            
            GameData loadedGameData = SaveLoadController.loadGame(selectedGameFileName);

            Main.setBoardSceneController(loader.getController());
            Main.loadGame(loadedGameData);
        
            Main.stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
            scene = new Scene(boardScene);
            Main.stage.setScene(this.scene);
            Main.stage.show();

            //! deleteFile(event);
            StartSceneController.stopMusic();
            StartSceneController.stopWind();

            VolumeController.setBoardSceneMusicVolume(70);
            BoardSceneController.initMusic();

        } 
        else {
            UtilView.generateErrorAlert("No Effect", "Please select a file to be loaded");
        }
    }
    
    public void deleteFile(ActionEvent event) throws Exception {
        SFXController.initializePlay("SFX/buttonClickSFX.wav");
        SFXController.play();

        if(selectedGameFileName != null){
            System.out.print("Delete File : ");
            System.out.println(selectedGameFileName);

            loadList.remove(selectedButton);
            fileNameList.remove(selectedGameFileName);
            SaveLoadController.overwrite_File_Name_List(fileNameList);
            SaveLoadController.remove_Game_File(selectedGameFileName);

        } else {
            UtilView.generateErrorAlert("No Effect", "Please select a file to be deleted");
        }
    }

    public void selectGameFile(Button selectedButton, String selectedGameFileName) {
        SFXController.initializePlay("SFX/buttonClickSFX.wav");
        SFXController.play();

        this.selectedButton = selectedButton;
        this.selectedGameFileName = selectedGameFileName;
        System.out.println("Selected File Name : " + selectedGameFileName);
    }
}
