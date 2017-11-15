package ar.edu.itba.controllers;

import ar.edu.itba.events.ImageModified;
import ar.edu.itba.models.*;
import com.google.common.eventbus.EventBus;
import javafx.scene.shape.Rectangle;

import java.awt.image.BufferedImage;
import java.util.concurrent.*;

/**
 * Created by Luis on 3/11/2017.
 */
public class ActiveContoursController implements Runnable{
    private final BlockingDeque<ImageMatrix> frames;
    private ActiveContours controller;
    private EditorController editorController;
    private Pixel lIn, lOut;
    private boolean done;

    public ActiveContoursController(EditorController editorController) {
        this.frames = new LinkedBlockingDeque<>();
        this.editorController = editorController;
    }

    public void initialize(ImageMatrix initialFrame, Rectangle initialObjectSelection) {
        this.frames.addFirst(initialFrame);

        if (initialFrame.getType() == BufferedImage.TYPE_BYTE_GRAY) {
            this.lIn = new GreyPixel(0, 0, 255);
            this.lOut = new GreyPixel(0, 0, 0);
        } else {
            this.lIn = new RGBPixel(0, 0, 255, 0, 0);
            this.lOut = new RGBPixel(0, 0, 0, 0, 255);
        }

        this.controller = new ActiveContours(initialFrame,
                (int)initialObjectSelection.getX(), (int)initialObjectSelection.getY(),
                (int)(initialObjectSelection.getX() + initialObjectSelection.getWidth()),
                (int)(initialObjectSelection.getY() + initialObjectSelection.getHeight()));

        this.done = false;
    }

    @Override
    public void run() {
        ImageMatrix currentFrame, modified;
        long time;
        while (! frames.isEmpty()) {
            currentFrame = frames.poll();
            time = System.currentTimeMillis();
            modified = controller.findObject(currentFrame, 50, lIn, lOut);
            time = System.currentTimeMillis() - time;
            System.out.println("time:" + 1.0*time/1000 + "s");
            this.editorController.setImage(modified);
        }
        System.out.println("DONE");
    }

    public boolean addFrame(ImageMatrix imageMatrix) {
         return this.frames.offerLast(imageMatrix);
    }

}
