package ar.edu.itba.controllers;

import ar.edu.itba.Pixel;
import ar.edu.itba.events.ImageLoaded;
import ar.edu.itba.events.PixelSelected;
import ar.edu.itba.events.SaveImage;
import ar.edu.itba.events.SetPixelActivated;
import ar.edu.itba.services.ImageService;
import ar.edu.itba.services.impl.ImageServiceImpl;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EditorController {
    public ImageView before;
    public ImageView after;
    private boolean setPixel;
    private EventBus eventBus;
    private ImageService imageService;

    @Inject
    public EditorController(final EventBus eventBus, final ImageService imageService) {
        this.eventBus = eventBus;
        this.imageService = imageService;
    }

    @Subscribe
    public void loadImage(ImageLoaded imageLoaded) throws FileNotFoundException, IOException{
        //System.out.println("height: " + before.getFitHeight() + " width: " + after.getFitWidth());
        imageService.loadImage(imageLoaded);
        Image image = new Image(new FileInputStream(imageLoaded.getImg()));
        //Image image = new Image(imageLoaded.getImg());

        System.out.println("height: " + image.getHeight() + " width: " + image.getWidth());
        before.setImage(image);
        after.setImage(image);
    }

    @Subscribe
    public void saveImage(SaveImage save) throws IOException{
        System.out.printf("Saving image in %s", save.getImg().getCanonicalPath());
    }

    @Subscribe
    public void activateSetPixel(SetPixelActivated set) {
        this.setPixel = true;
    }

    public void imageClicked(MouseEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        System.out.println("x: " + x + " y: " + y);
        eventBus.post(new PixelSelected(this.imageService.selectPixel(x,y)));
    }
    public void mousePressed(MouseEvent event) {
        System.out.println("pressed");
    }
    public void mouseDragged(MouseEvent event) {
        System.out.println("dragged");
    }
    public void mouseReleased(MouseEvent event) {
        System.out.println("released");
    }

}
