package ar.edu.itba.constants;

public enum FxmlEnum {
    MAIN("fxml/main.fxml"),
    EDITOR("fxml/editor.fxml"),
    MENU("fxml/menu.fxml");

    private String path;

    FxmlEnum(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}