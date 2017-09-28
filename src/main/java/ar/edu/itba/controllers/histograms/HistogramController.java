package ar.edu.itba.controllers.histograms;

import ar.edu.itba.events.ImageLoaded;
import ar.edu.itba.events.ShowHistogram;
import ar.edu.itba.views.histograms.GreyHistogramView;
import ar.edu.itba.views.histograms.RGBHistogramView;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.awt.image.BufferedImage;

import static ar.edu.itba.controllers.histograms.HistogramController.ImageType.*;

public class HistogramController {
    public VBox histograms;
    private EventBus eventBus;
    private ImageType imageType = NONE;
    private RGBHistogramView rgbView;
    private GreyHistogramView greyView;
    protected enum ImageType {
        GREY,RGB,NONE,UNKNOWN;
    }
    @Inject
    public HistogramController(EventBus eventBus) {
        this.eventBus = eventBus;
        this.greyView = new GreyHistogramView();
        this.rgbView = new RGBHistogramView();
    }

    @Subscribe
    public void openImage(ImageLoaded imageLoaded) {
        ImageType loadedType = getImageType(imageLoaded.getImage().getType());

        if (this.imageType != RGB && loadedType == RGB) {
            histograms.getChildren().clear();
            histograms.getChildren().add(rgbView);

        }
        else if (this.imageType != GREY && loadedType == GREY) {
            histograms.getChildren().clear();
            histograms.getChildren().add(greyView);
        }

        this.eventBus.post(new ShowHistogram(imageLoaded.getImage()));
    }

    private ImageType getImageType(int t) {
        if (t == BufferedImage.TYPE_INT_RGB || t == BufferedImage.TYPE_3BYTE_BGR)
            return RGB;
        else if (t == BufferedImage.TYPE_BYTE_GRAY)
            return GREY;
        return UNKNOWN;
    }
}
