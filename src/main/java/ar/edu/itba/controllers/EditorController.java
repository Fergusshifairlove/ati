package ar.edu.itba.controllers;

import ar.edu.itba.constants.NoiseType;
import ar.edu.itba.events.*;
import ar.edu.itba.models.GreyImageMatrix;
import ar.edu.itba.models.Hough;
import ar.edu.itba.models.ImageMatrix;
import ar.edu.itba.models.masks.Filter;
import ar.edu.itba.models.shapes.Shape;
import ar.edu.itba.models.thresholding.ThresholdFinder;
import ar.edu.itba.services.ImageService;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class EditorController {
    public ImageView before;
    public ImageView after;
    public Pane draw;
    public Pane selection;
    private ImageMatrix imageBefore;
    private ImageMatrix imageAfter;
    private EventBus eventBus;
    private ImageService imageService;
    private Rectangle selectionArea;
    private boolean selected;

    @Inject
    public EditorController(final EventBus eventBus, final ImageService imageService) {
        this.eventBus = eventBus;
        this.selected = false;
        this.imageService = imageService;
        this.selectionArea = new Rectangle(0,0,0,0);
        this.selectionArea.setFill(Color.TRANSPARENT);
        this.selectionArea.setStroke(Color.YELLOW);
        //this.selection.getChildren().add(this.selectionArea);
    }

    @Subscribe
    public void loadImage(ImageModified imageModified) throws IOException {
        System.out.println("IMAGE MODIFIED");
        this.imageAfter = imageModified.getModified();
        Image image = SwingFXUtils.toFXImage(this.imageAfter.getImage(false), null);
        System.out.println("height: " + image.getHeight() + " width: " + image.getWidth());
        after.setImage(image);
        this.draw.getChildren().clear();
        this.draw.getChildren().add(this.after);
    }

    @Subscribe
    public void openImage(OpenImage openImage) throws IOException {
        this.imageBefore = imageService.loadImage(openImage.getImage());
        this.imageAfter = ImageMatrix.readImage(imageBefore.getImage(false));
        this.before.setImage(SwingFXUtils.toFXImage(imageBefore.getImage(false), null));
        this.draw.getChildren().clear();
        //this.selection.getChildren().add(this.selectionArea);
        this.eventBus.post(new ImageLoaded(this.imageBefore));
    }

    @Subscribe
    public void saveImage(SaveImage save) throws IOException {
        System.out.printf("Saving image in %s", save.getImg().getCanonicalPath());
    }

    @Subscribe
    public void modifyPixel(PixelModified pixelModified) {
        this.imageAfter.setPixel(pixelModified.getPixel());
        this.eventBus.post(new ImageModified(this.imageAfter));
    }

    @Subscribe
    public void applyPunctualOperator(ApplyPunctualOperation operation) {
        this.imageAfter.applyPunctualOperation(operation.getOperator());
        eventBus.post(new ImageModified(this.imageAfter));
    }

    @Subscribe
    public void equalize(EqualizeImage equalizeImage) {
        this.imageAfter.equalize();
        eventBus.post(new ImageModified(this.imageAfter));
    }

    @Subscribe
    public void confirm(OperationsConfirmed operationsConfirmed) {
        this.imageBefore = this.imageAfter;
        this.before.setImage(SwingFXUtils.toFXImage(this.imageAfter.getImage(false), null));
        this.after.setImage(null);
    }

    @Subscribe
    public void reset(ResetImage resetImage) {
        this.imageAfter = ImageMatrix.readImage(this.imageBefore.getImage(false));
        eventBus.post(new ImageModified(this.imageAfter));
    }

    @Subscribe
    public void applyNoise(ApplyNoise noise) {
        if (this.imageBefore == null) {
            this.imageAfter = GreyImageMatrix.getNoiseImage(100, 100, noise.getGenerator(), noise.getNoiseType(), noise.getPercentage());

        } else {
            this.imageAfter.applyNoise(noise.getNoiseType(), noise.getGenerator(), noise.getPercentage());
            if (noise.getNoiseType() == NoiseType.MULTIPLICATIVE)
                this.imageAfter.compress();
        }
        eventBus.post(new ImageModified(ImageMatrix.readImage(imageAfter.getImage(false))));

    }

    @Subscribe
    public void applyFilter(Filter filter) {
        this.imageAfter.applyFilterOperation(filter::filterImage);
        eventBus.post(new ImageModified(this.imageAfter));
    }

    @Subscribe
    public void applyThresholding(ThresholdFinder f) {
        for (Integer band : this.imageAfter.getBands()) {
            Double threshold = f.findThreshold(imageAfter.getIterableBand(band));
            imageAfter.applyPunctualOperation(band, p -> p > threshold ? 255 : 0);
        }
        eventBus.post(new ImageModified(this.imageAfter));
    }

    public void imageClicked(MouseEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        System.out.println("x: " + x + " y: " + y);
        String id = ((Pane) event.getSource()).getChildren().get(0).getId();
        if (id.equals("before")) {
            eventBus.post(new PixelSelected(this.imageBefore.getPixelColor(x, y)));
        } else if (id.equals("after")) {
            eventBus.post(new PixelSelected(this.imageAfter.getPixelColor(x, y)));
        }

    }

    @Subscribe
    public void diffuseImage(DiffuseImage diffuse) {
        this.imageAfter.applyFilterOperation(band -> diffuse.getDiffusion().difuse(band, diffuse.getTimes()));
        eventBus.post(new ImageModified(this.imageAfter));
    }

    @Subscribe
    public void drawShapes(Hough hough) {
        Bounds bounds = new BoundingBox(0,0,this.imageAfter.getWidth(), this.imageAfter.getHeight());
        hough.generateParametricSpace(this.imageAfter.getWidth(), this.imageAfter.getHeight());
        for (Integer b : imageAfter.getBands()) {
            for (Shape shape : hough.findShapes(this.imageAfter.getBand(b))) {
                System.out.println("shape: " + shape);
                draw.getChildren().add(shape.toFxShape(bounds));
            }
        }
    }

    @Subscribe
    public void cutImage(CutImage cutImage) {
        this.imageAfter = this.imageBefore.getSubImage((int) selectionArea.getX(),
                (int) selectionArea.getY(), (int) selectionArea.getWidth(), (int) selectionArea.getHeight());
        eventBus.post(new ImageModified(this.imageAfter));
    }

    public void mousePressed(MouseEvent event) {
        System.out.println("pressed");
        System.out.println("x: " + event.getX() + " y:" + event.getY());
        this.selection.getChildren().remove(this.selectionArea);
        this.selected = false;

    }

    public void mouseDragged(MouseEvent event) {
        System.out.println("dragged");
        System.out.println("x: " + event.getX() + " y:" + event.getY());
        System.out.println(this.selected);
        if (! this.selected) {
            this.selectionArea.setX(event.getX());
            this.selectionArea.setY(event.getY());
            this.selectionArea.setHeight(0);
            this.selectionArea.setWidth(0);
            this.selected = true;
            this.selection.getChildren().add(this.selectionArea);
            return;
        }
        if (this.selectionArea.getX() < event.getX())
            this.selectionArea.setWidth(event.getX() - this.selectionArea.getX());
        else {
            double x = this.selectionArea.getX();
            this.selectionArea.setX(event.getX());
            this.selectionArea.setWidth((x + this.selectionArea.getWidth() - event.getX()));
        }


        if (this.selectionArea.getY() < event.getY())
            this.selectionArea.setHeight(event.getY() - this.selectionArea.getY());
        else {
            double y = this.selectionArea.getY();
            this.selectionArea.setY(event.getY());
            this.selectionArea.setHeight((y + this.selectionArea.getHeight() - event.getY()));
        }
    }

    public void mouseReleased(MouseEvent event) {
        System.out.println("released");
        System.out.println("x: " + event.getX() + " y:" + event.getY());
        if(this.selected && !this.selection.getChildren().contains(this.selectionArea))
            this.selection.getChildren().add(this.selectionArea);
        this.selected = false;
    }

}
