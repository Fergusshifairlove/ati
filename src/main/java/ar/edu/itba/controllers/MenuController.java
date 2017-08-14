package ar.edu.itba.controllers;

import ar.edu.itba.events.ImageLoaded;
import ar.edu.itba.events.SaveImage;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;

import java.io.*;

public class MenuController {
    public MenuBar menuBar;
    private FileChooser fileChooser;
    private EventBus eventBus;

    @Inject
    public MenuController(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @FXML
    public void initialize() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
    }

    public void openFile(ActionEvent actionEvent) throws FileNotFoundException {
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        eventBus.post(new ImageLoaded(new FileInputStream(file)));
    }

    public void saveFile(ActionEvent actionEvent) {
        File file = fileChooser.showSaveDialog(menuBar.getScene().getWindow());

        if(file != null)
            eventBus.post(new SaveImage(file));
    }
    private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter = null;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {

        }

    }

}
