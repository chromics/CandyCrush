package view.viewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import view.Main;

public class LoadSceneController {
//    private Stage stage;
    private Scene scene;
    
    @FXML
    private ListView<Button> loadListView;

    public void backToStartScene(ActionEvent event) throws Exception {
        Parent startScene = FXMLLoader.load(getClass().getResource("/view/fxml/StartScene.fxml"));
        Main.stage = (Stage)(((Node)event.getSource()).getScene().getWindow());
        this.scene = new Scene(startScene);
        Main.stage.setScene(this.scene);
        Main.stage.show();
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
        loadListView.setItems(loadList);

    }
}
