package ar.edu.itba.constants;

public enum FxmlEnum {
    MAIN("fxml/main.fxml"),
    EDITOR("fxml/editor.fxml"),
    MENU("fxml/menu.fxml"),
    DATA("fxml/image-data.fxml"),
    RGB("fxml/rgb.fxml"),
    GREY("fxml/grey.fxml");

    private String path;

    FxmlEnum(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}