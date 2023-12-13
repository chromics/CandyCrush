package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // ROOT NODE
        Parent root = FXMLLoader.load(getClass().getResource("fxml/startscene.fxml"));

        // STAGE
        // a) Icon
        Image icon = new Image("static/testicon.png");
        primaryStage.getIcons().add(icon);

        // b) Title
        primaryStage.setTitle("Happy Match");
        primaryStage.setResizable(false);

        // Set Scene
        primaryStage.setScene(new Scene(root));
        // Show
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

// Line line = new Line();
// line.setStroke(Color.BLUE);
// line.setStartX(50);
// line.setStartY(50);
// line.setEndX(100);
// line.setEndY(50);
// line.setStrokeWidth(5);
// line.setRotate(45);
// line.setOpacity(0.5);

// root.getChildren().add(line);

// Scene Arg --> , 400, 300, Color.WHEAT

// // SCENE
// // a) Text
// Text text = new Text();
// text.setText("Lucida Console");
// text.setX(50);
// text.setY(50);
// text.setFont(Font.font("Lucida Console", 50));
// text.setFill(Color.GRAY);
// root.getChildren().add(text);
// // b) Image
// Image image = new Image("static/testicon.png");
// ImageView imageView = new ImageView(image);
// imageView.setX(0);
// imageView.setY(0);
// root.getChildren().add(imageView);

// import javafx.scene.paint.Color;
// import javafx.scene.text.Font;
// import javafx.scene.text.Text;
// import javafx.scene.shape.*;;
// import javafx.scene.Group;