package view.viewController;

import controller.GameController;
import data.constant.GameMode;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import view.Main;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.scene.image.ImageView;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.MenuItem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import data.constant.MapTemplate;

public class CustomGameInputDialogController implements Initializable {
//    @FXML
//    private TextField gridSizeTextField;
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
    @FXML
    private Pagination templatePagination;
    @FXML
    private AnchorPane pane;
    @FXML
    private Button selectMapButton;
    @FXML
    private MenuButton widthMenuButton;
    @FXML
    private MenuButton heightMenuButton;
    @FXML
    private MenuItem w1;
    @FXML
    private MenuItem w2;
    @FXML
    private MenuItem w3;
    @FXML
    private MenuItem w4;
    @FXML
    private MenuItem h1;
    @FXML
    private MenuItem h2;
    @FXML
    private MenuItem h3;
    @FXML
    private MenuItem h4;

    private int gridWidth;
    private int gridHeight;
    private int movesCount;
    private int shuffleCount;
    private int targetScore;
    private Clip BGM;
    private GameMode gameMode;
    private MapTemplate mapTemplate = MapTemplate.RECTANGULAR;
    private static Stage stage;

    private File[] filesJpg;

    public void initialize(URL location, ResourceBundle resourceBundle) {
        initializeTemplateImages();
        templatePagination.setDisable(true);


        w1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeGridWidthSize(event);
            }
        });
        w2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeGridWidthSize(event);
            }
        });
        w3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeGridWidthSize(event);
            }
        });
        w4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeGridWidthSize(event);
            }
        });

        h1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeGridHeightSize(event);
            }
        });
        h2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeGridHeightSize(event);
            }
        });
        h3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeGridHeightSize(event);
            }
        });
        h4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeGridHeightSize(event);
            }
        });

    }

    public static void setStage (Stage dialogStage) {
        stage = dialogStage;
    }

    public void generateMap() {
//        if (gridSizeTextField.getText() == null) {
//            UtilView.generateErrorAlert("Error!", "Custom grid size field left empty!");
//        }
//        else if (gridSizeTextField != null) {
//            String gridSize = gridSizeTextField.getText();
//            int gridSizeNum = Integer.parseInt(gridSize.replaceAll("[^0-9]", ""));
//            gridWidth = Integer.parseInt(Integer.toString(gridSizeNum).substring(0, 1));
//            gridHeight = Integer.parseInt(Integer.toString(gridSizeNum).substring(1, 2));
//        }

        //Select Map
        int index = templatePagination.getCurrentPageIndex();
        if (index == 0) {
            mapTemplate = MapTemplate.OCTAGON;
        }
        if (index == 1) {
            mapTemplate = MapTemplate.T;
        }
        if (index == 2) {
            mapTemplate = MapTemplate.Y;
        }

        if (movesCountTextField.getText() == null) {
            UtilView.generateErrorAlert("Error!", "Moves count field left empty!");
        }
        if (!isInteger(movesCountTextField.getText())) {
            UtilView.generateErrorAlert("Error!", "Please fill in integer in moves count field.");
        }
        else if (movesCountTextField.getText() != null && isInteger(movesCountTextField.getText())) {
            String movesCountStr = movesCountTextField.getText();
            movesCount = Integer.parseInt(movesCountStr);
        }

        if (shuffleCountTextField.getText() == null) {
            UtilView.generateErrorAlert("Error!", "Shuffle count field left empty!");
        }
        if (!isInteger(shuffleCountTextField.getText())) {
            UtilView.generateErrorAlert("Error!", "Please fill in integer in shuffle count field.");
        }
        else if (shuffleCountTextField.getText() != null && isInteger(shuffleCountTextField.getText())) {
            String shuffleCountStr = shuffleCountTextField.getText();
            shuffleCount = Integer.parseInt(shuffleCountStr);
        }

        if (targetScoreTextField.getText() == null) {
            UtilView.generateErrorAlert("Error!", "Target score field left empty!");
        }
        if (!isInteger(targetScoreTextField.getText())) {
            UtilView.generateErrorAlert("Error!", "Please fill in integer in target score field.");
        }
        else if (targetScoreTextField.getText() != null && isInteger(targetScoreTextField.getText())) {
            String targetScoreStr = targetScoreTextField.getText();
            targetScore = Integer.parseInt(targetScoreStr);
        }

        if (specialItemCheckbox.isSelected()) {
            gameMode = GameMode.Special_Game_Mode;
        }
        
        if (! specialItemCheckbox.isSelected()) {
            gameMode = GameMode.Normal_Game_Mode;
        }

        if (customMapCheckbox.isSelected()) {
//            StartSceneController.newTemplatedGame();
        }
        else {
            gridWidth = Integer.parseInt(widthMenuButton.getText());
            gridHeight = Integer.parseInt(heightMenuButton.getText());
//            StartSceneController.newCustomGame();
        }

        System.out.println("Custom Game : ");
        System.out.println("Board Row Size : " + gridHeight);
        System.out.println("Board Col Size : " + gridWidth);
        System.out.println("Init Step : " + movesCount);
        System.out.println("Init Shuffle : " + shuffleCount);
        System.out.println("Target Score : " + targetScore);
        System.out.println("GameMode : " + gameMode);

        if (movesCount > 0 && targetScore > 0 && gameMode != null) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/BoardScene.fxml"));
                Parent boardScene = loader.load();
                
                Main.setBoardSceneController(loader.getController());
                if (mapTemplate == MapTemplate.RECTANGULAR) {
                    Main.customGame(gameMode, gridHeight, gridWidth, movesCount, shuffleCount, targetScore, mapTemplate);
                }
                else {
                    Main.customGame(gameMode, 10, 10, movesCount, shuffleCount, targetScore, mapTemplate);
                } 
        
                Scene scene = new Scene(boardScene);
                Main.stage.setScene(scene);
                Main.stage.show();
                
                // gameObjective(event, dialogURL);
    
                StartSceneController.musicController.stopMusic();
                StartSceneController.windController.stopMusic();
    
                VolumeController.setBoardSceneMusicVolume(70);
                BoardSceneController.initMusic();

                stage.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }   
        else {
            UtilView.generateErrorAlert("Error!", "Please insert valid positive integer in Moves Count and Score section!");
        }     
    }
    public void toggleTemplateMap() {
        if (customMapCheckbox.isSelected()) {
            widthMenuButton.setDisable(true);
            heightMenuButton.setDisable(true);
            templatePagination.setDisable(false);

            mapTemplate = MapTemplate.RECTANGULAR;
        } else if (!customMapCheckbox.isSelected()) {
            widthMenuButton.setDisable(false);
            heightMenuButton.setDisable(false);
            templatePagination.setDisable(true);

            selectTemplate();
        }
    }

    public void initializeTemplateImages() {
        templatePagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex);
            }
        });
    }

    public VBox createPage(int index) {
        try {
            File dir = new File(CustomGameInputDialogController.class.getResource("templateImages").toURI());
            filesJpg = dir.listFiles();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ImageView imageView = new ImageView();
        File file = filesJpg[index];

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageView.setImage(image);
            imageView.setFitHeight(80);
            imageView.setFitWidth(80);

            imageView.setSmooth(true);
            imageView.setCache(true);

        } catch (IOException e) {
            System.out.println(e);
        }

        VBox pageBox = new VBox();
        pageBox.setAlignment(Pos.CENTER);
        pageBox.getChildren().add(imageView);

        return pageBox;
    }

    public void selectTemplate() {
        
    }

    public void changeGridWidthSize(ActionEvent event) {
        MenuItem sourceItem = (MenuItem) (event.getSource());
        String menuLabel = sourceItem.getText();
        widthMenuButton.setText(menuLabel);
    }
    public void changeGridHeightSize(ActionEvent event) {
        MenuItem sourceItem = (MenuItem) (event.getSource());
        String menuLabel = sourceItem.getText();
        heightMenuButton.setText(menuLabel);
    }

    public static boolean isInteger(String s) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
//            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }

}
