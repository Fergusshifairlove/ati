package ar.edu.itba.services;

import ar.edu.itba.constants.FxmlEnum;
import javafx.scene.Parent;

public interface FxmlLoaderService {

    Parent load(FxmlEnum fxmlEnum, Object root);
}