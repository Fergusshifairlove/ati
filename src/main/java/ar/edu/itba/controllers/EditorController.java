package ar.edu.itba.controllers;

import ar.edu.itba.events.ImageLoaded;
import ar.edu.itba.events.SaveImage;
import com.google.common.eventbus.Subscribe;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class EditorController {
    public ImageView before;
    public ImageView after;

    @Subscribe
    public void showImage(ImageLoaded imageLoaded) {
        Image image = new Image(imageLoaded.getImg(),600,400,true,true);
        before.setImage(image);
        after.setImage(image);
    }

    @Subscribe
    public void saveImage(SaveImage save) throws IOException{
        System.out.printf("Saving image in %s", save.getImg().getCanonicalPath());
    }
    public void imageClicked(MouseEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
    }

}
