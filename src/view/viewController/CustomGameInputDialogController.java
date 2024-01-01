package view.viewController;

import controller.GameController;
import data.constant.GameMode;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
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

public class CustomGameInputDialogController implements Initializable {
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
    @FXML
    private Pagination templatePagination;
    @FXML
    private AnchorPane pane;
    @FXML
    private Button selectMapButton;
    @FXML
    private MenuButton musicMenuButton;
    @FXML
    private MenuItem track1;
    @FXML
    private MenuItem track2;
    @FXML
    private MenuItem track3;
    @FXML
    private Label trackLabel;

    private int gridWidth;
    private int gridHeight;
    private int movesCount;
    private int shuffleCount;
    private int targetScore;
    private Clip BGM;
    private GameMode gameMode;
    // private Template template;

    private File[] filesJpg;

    public void initialize(URL location, ResourceBundle resourceBundle) {
        initializeTemplateImages();
        templatePagination.setDisable(true);
        selectMapButton.setDisable(true);

        track1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeTrack(event);
            }
        });
        track2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeTrack(event);
            }
        });
        track3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeTrack(event);
            }
        });
    }

    public void generateMap() {
        if (gridSizeTextField.getText() == null) {
            UtilView.generateErrorAlert("Error!", "Custom grid size field left empty!");
        }
        else if (gridSizeTextField != null) {
            String gridSize = gridSizeTextField.getText();
            int gridSizeNum = Integer.parseInt(gridSize.replaceAll("[^0-9]", ""));
            gridWidth = Integer.parseInt(Integer.toString(gridSizeNum).substring(0, 1));
            gridHeight = Integer.parseInt(Integer.toString(gridSizeNum).substring(1, 2));
        }

        if (movesCountTextField.getText() == null) {
            UtilView.generateErrorAlert("Error!", "Moves count field left empty!");
        }
        else if (movesCountTextField.getText() != null) {
            String movesCountStr = movesCountTextField.getText();
            movesCount = Integer.parseInt(movesCountStr);
        }

        if (shuffleCountTextField.getText() == null) {
            UtilView.generateErrorAlert("Error!", "Moves count field left empty!");
        }
        else if (shuffleCountTextField.getText() != null) {
            String shuffleCountStr = shuffleCountTextField.getText();
            shuffleCount = Integer.parseInt(shuffleCountStr);
        }

        if (targetScoreTextField.getText() == null) {
            UtilView.generateErrorAlert("Error!", "Target score field left empty!");
        }
        else if (targetScoreTextField.getText() != null) {
            String targetScoreStr = targetScoreTextField.getText();
            targetScore = Integer.parseInt(targetScoreStr);
        }

        if (specialItemCheckbox.isSelected()) {
            gameMode = GameMode.Special_Game_Mode;
        }

        if (customMapCheckbox.isSelected()) {
//            StartSceneController.newTemplatedGame();
        }
        else {
//            StartSceneController.newCustomGame();
        }

    }

    public void toggleTemplateMap() {
        if (customMapCheckbox.isSelected()) {
            gridSizeTextField.setDisable(true);
            templatePagination.setDisable(false);
            selectMapButton.setDisable(false);
        } else if (!customMapCheckbox.isSelected()) {
            gridSizeTextField.setDisable(false);
            templatePagination.setDisable(true);
            selectMapButton.setDisable(true);
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
        System.out.println("template selected!");
    }

    public void changeTrack(ActionEvent event) {
        MenuItem sourceItem = (MenuItem) (event.getSource());
        String menuLabel = sourceItem.getText();
        trackLabel.setText("[ " + menuLabel + " ]");
    }

}