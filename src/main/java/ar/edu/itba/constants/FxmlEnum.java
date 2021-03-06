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
    GREYHISTOGRAM("fxml/histograms/grey-histogram.fxml"),
    RGBHISTOGRAM("fxml/histograms/rgb-histogram.fxml"),
    HISTOGRAM("fxml/histograms/histogram.fxml"),
    MEAN("fxml/mask/mean.fxml"),
    MEDIAN("fxml/mask/median.fxml"),
    WEIGHTEDMEDIAN("fxml/mask/weightedmedian.fxml"),
    GAUSS("fxml/mask/gauss.fxml"),
    HIGHPASS("fxml/mask/highpass.fxml"),
    CONTRAST("fxml/punctualOperations/contrast.fxml"),
    SALT_AND_PEPPER("fxml/noise/salt_and_pepper.fxml"),
    PREWITT("fxml/border/prewitt.fxml"),
    DIRECTIONALPREWITT("fxml/border/directionalprewitt.fxml"),
    SOBEL("fxml/border/sobel.fxml"),
    DIRECTIONALSOBEL("fxml/border/directionalsobel.fxml"),
    ZEROLAPLACIAN("fxml/border/zerolaplacian.fxml"),
    LAPLACIAN("fxml/border/laplacian.fxml"),
    LOG("fxml/border/log.fxml"),
    GLOBAL_THRESHOLD("fxml/punctualOperations/global-threshold.fxml"),
    ANISOTROPIC("fxml/diffusion/anisotropic.fxml"),
    ISOTROPIC("fxml/diffusion/isotropic.fxml"),
    OTSU("fxml/punctualOperations/otsu-threshold.fxml"),
    CANNY("fxml/border/canny.fxml"),
    SUSAN("fxml/border/susan.fxml"),
    HOUGH("fxml/border/hough.fxml"),
    HARRIS("fxml/features/harris.fxml"),
    SIFT("fxml/features/sift.fxml"),
    COWS("fxml/cows/cow-counter.fxml");


    private String path;

    FxmlEnum(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}