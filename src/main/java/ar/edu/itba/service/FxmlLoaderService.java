package ar.edu.itba.service;

import ar.edu.itba.constants.FxmlEnum;
import javafx.scene.Parent;

public interface FxmlLoaderService {

    Parent load(FxmlEnum fxmlEnum, Object root);
}