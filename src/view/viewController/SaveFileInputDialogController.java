package view.viewController;

import javafx.fxml.FXML;

import java.awt.*;

public class SaveFileInputDialogController {
    @FXML
    TextField saveFileName = new TextField();

    public void save() {
        saveFileName.getSelectedText();
    }
}
