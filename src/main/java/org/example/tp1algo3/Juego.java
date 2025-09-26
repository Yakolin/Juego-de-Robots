package org.example.tp1algo3;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.tp1algo3.modelo.LogicaJuegoRobots;
import org.example.tp1algo3.vista.Vista;

public class Juego extends Application {
    @Override
    public void start(Stage stage) {
        LogicaJuegoRobots logicaJuegoRobots = new LogicaJuegoRobots();
        Vista vista = new Vista(stage, logicaJuegoRobots);
        vista.vistaIniciar();
    }

    public static void main(String[] args) {
        launch();
    }
}
