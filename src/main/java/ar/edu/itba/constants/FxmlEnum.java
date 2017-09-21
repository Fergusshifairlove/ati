package ar.edu.itba.constants;

public enum FxmlEnum {
    MAIN("fxml/main.fxml"),
    EDITOR("fxml/editor.fxml"),
    MENU("fxml/menu.fxml"),
    DATA("fxml/image-data.fxml"),
    RGB("fxml/rgb.fxml"),
    GREY("fxml/grey.fxml"),
    OPERATIONS("fxml/operations.fxml"),
    THRESHOLD("fxml/punctualOperations/threshold.fxml"),
    NEGATIVE("fxml/punctualOperations/negative.fxml"),
    GAMMA("fxml/punctualOperations/gamma.fxml"),
    GAUSSIAN("fxml/noise/gaussian.fxml"),
    EXPONENTIAL("fxml/noise/exponential.fxml"),
    RAYLEIGH("fxml/noise/rayleigh.fxml"),
    HISTOGRAM("fxml/histogram.fxml"),
    MEAN("fxml/mask/mean.fxml"),
    MEDIAN("fxml/mask/median.fxml"),
    WEIGHTEDMEDIAN("fxml/mask/weightedmedian.fxml"),
    GAUSS("fxml/mask/gauss.fxml"),
    HIGHPASS("fxml/mask/highpass.fxml"),
    CONTRAST("fxml/punctualOperations/contrast.fxml"),
    SALT_AND_PEPPER("fxml/noise/salt_and_pepper.fxml"),
    PREWITT("fxml/border/prewitt.fxml"),
    SOBEL("fxml/border/sobel.fxml"),
    LAPLACIAN("fxml/border/laplacian.fxml"),
    LOG("fxml/border/log.fxml");


    private String path;

    FxmlEnum(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}