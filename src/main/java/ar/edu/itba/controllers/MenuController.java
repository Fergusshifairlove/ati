package ar.edu.itba.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MenuController {
    public MenuBar menuBar;
    private FileChooser fileChooser;

    @FXML
    public void initialize() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
    }

    public void openFile(ActionEvent actionEvent) {
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(menuBar.getScene().getWindow());

    }

    public void saveFile(ActionEvent actionEvent) {
        File file = fileChooser.showSaveDialog(menuBar.getScene().getWindow());

        if(file != null){
            SaveFile("test", file);
        }
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
