package ar.edu.itba.controllers;

import ar.edu.itba.events.ImageLoaded;
import ar.edu.itba.events.SaveImage;
import ar.edu.itba.events.SetPixelActivated;
import ar.edu.itba.services.ImageService;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.io.*;

public class MenuController {
    public MenuBar menuBar;
    private FileChooser fileChooser;
    private EventBus eventBus;
    private ImageService imageService;

    @Inject
    public MenuController(final EventBus eventBus, final ImageService imageService) {
        this.eventBus = eventBus;
        this.imageService = imageService;
    }

    @FXML
    public void initialize() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.raw", "*.pgm", "*.ppm", "*.bmp")
        );
    }

    public void openFile(ActionEvent actionEvent) throws IOException {
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        imageService.loadImage(file);
        eventBus.post(new ImageLoaded());

    }

    public void setPixel(ActionEvent actionEvent) {
        eventBus.post(new SetPixelActivated());
    }

    public void saveFile(ActionEvent actionEvent) throws IOException{
        File file = fileChooser.showSaveDialog(menuBar.getScene().getWindow());

        if(file != null)
            ImageIO.write(imageService.getImage(), FilenameUtils.getExtension(file.getName()),file);
    }


}
