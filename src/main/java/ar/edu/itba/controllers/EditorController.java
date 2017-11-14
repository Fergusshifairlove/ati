package ar.edu.itba.controllers;

import ar.edu.itba.constants.NoiseType;
import ar.edu.itba.events.*;
import ar.edu.itba.models.*;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.apache.commons.collections.functors.NotNullPredicate;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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
    private boolean video = false;
    private ActiveContoursController activeContoursController;
    private List<ImageMatrix> images;
    private ExecutorService executorService;

    @Inject
    public EditorController(final EventBus eventBus, final ImageService imageService) {
        this.eventBus = eventBus;
        this.selected = false;
        this.imageService = imageService;
        this.selectionArea = new Rectangle(0,0,0,0);
        this.selectionArea.setFill(Color.TRANSPARENT);
        this.selectionArea.setStroke(Color.YELLOW);
        this.images = new LinkedList<>();
        this.executorService = Executors.newCachedThreadPool();
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
        this.video = false;
    }

    @Subscribe
    public void openImage(OpenImage openImage) throws IOException {
        this.imageBefore = imageService.loadImage(openImage.getImage());
        this.imageAfter = ImageMatrix.readImage(imageBefore.getImage(false));
        this.before.setImage(SwingFXUtils.toFXImage(imageBefore.getImage(false), null));
        this.draw.getChildren().clear();
        //this.selection.getChildren().add(this.selectionArea);
        this.video = false;
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
        if (! this.selected)
            return;
        ImageMatrix subimage = this.imageBefore.getSubImage((int) selectionArea.getX(),
                (int) selectionArea.getY(), (int) selectionArea.getWidth(), (int) selectionArea.getHeight());
        this.selectionArea.setWidth(0);
        this.selectionArea.setHeight(0);
        this.selected = false;
        this.selection.getChildren().remove(this.selectionArea);
        eventBus.post(new ImageModified(ImageMatrix.readImage(subimage.getImage(false))));
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
    }

    @Subscribe
    public void findObject(FindObjectInImage findObjectInImage) {
        if (this.video) {
            this.activeContoursController = new ActiveContoursController(this.images.get(0), selectionArea, this);
            for (ImageMatrix image: this.images) {
                this.activeContoursController.addFrame(image);
            }
            this.executorService.submit(this.activeContoursController);
            return;
        }
        ActiveContours activeContours = new ActiveContours(this.imageAfter,
                (int)selectionArea.getX(), (int) selectionArea.getY(),
                (int)(selectionArea.getX() + selectionArea.getWidth()),
                (int) (selectionArea.getY() + selectionArea.getHeight()));

        Pixel lin, lout;
        if (this.imageAfter.getType() == BufferedImage.TYPE_BYTE_GRAY) {
            lin = new GreyPixel(0, 0, 255);
            lout = new GreyPixel(0, 0, 0);
        }
        else {
            lin = new RGBPixel(0, 0, 255, 0, 0);
            lout = new RGBPixel(0, 0, 0, 0, 255);
        }
        if (this.video) {
            for (ImageMatrix image: this.images) {
                this.imageAfter = activeContours.findObject(image, 200, lin, lout);
            }
        }
        this.imageAfter = activeContours.findObject(this.imageAfter,200, lin, lout);

        this.eventBus.post(new ImageModified(this.imageAfter));
    }

    @Subscribe
    public void findObject(FindObjectInVideo findObjectInVideo){
        this.video = true;
        File directory = findObjectInVideo.getVideoDir();

        this.images = Arrays.stream(directory.listFiles((dir, name) -> name.endsWith(".jpg")))
                .map(img -> {
                    try {
                        return imageService.loadImage(img);
                    }
                    catch(IOException exception) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        this.imageBefore = this.images.get(0);
        this.before.setImage(SwingFXUtils.toFXImage(this.imageBefore.getImage(false), null));
        this.after.setImage(null);
        this.eventBus.post(new ImageLoaded(this.imageBefore));
    }

    public void setImage(ImageMatrix image) {
        this.imageAfter = image;
        this.after.setImage(SwingFXUtils.toFXImage(image.getImage(false), null));
    }

    @Subscribe
    public void openVideo(OpenVideo openVideo) throws IOException, JCodecException {
        this.video = true;

        File file = openVideo.getVideo();
        FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
        grab.seekToSecondPrecise(0);
        Picture picture;
        BufferedImage image;
        for (int i=0;i<100;i++) {
            picture = grab.getNativeFrame();
            System.out.println(picture.getWidth() + "x" + picture.getHeight() + " " + picture.getColor());
            //for JDK (jcodec-javase)
            image = AWTUtil.toBufferedImage(picture);
            this.images.add(ImageMatrix.readImage(image));
        }

        this.imageBefore = this.images.get(0);
        this.before.setImage(SwingFXUtils.toFXImage(this.imageBefore.getImage(false), null));
        this.after.setImage(null);
        this.eventBus.post(new ImageLoaded(this.imageBefore));
    }

}
