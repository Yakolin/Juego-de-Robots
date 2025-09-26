package org.example.tp1algo3.vista;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tp1algo3.modelo.LogicaJuegoRobots;


public class Vista {
    public static final int ANCHO_INICIAL_TABLA = 375;
    public static final int LARGO_INICIAL_TABLA = 375;
    private final HBox botonesPane;
    private final ManejoDeBotones manejoDeBotones;
    private final GridPane sueloPane;
    private final VistaTablero vistaTablero;
    private final ManejoControles manejoControles;
    private final Scene escenario;
    private final Stage stage;

    public Vista(Stage stage, LogicaJuegoRobots logicaJuegoRobots) {
        this.stage = stage;
        this.vistaTablero = new VistaTablero(logicaJuegoRobots);

        var Vpane = new VBox();
        this.sueloPane = new GridPane();
        sueloPane.setMaxWidth(ANCHO_INICIAL_TABLA);
        sueloPane.setMaxHeight(LARGO_INICIAL_TABLA);
        this.botonesPane = new HBox();
        this.manejoDeBotones = new ManejoDeBotones(logicaJuegoRobots);
        this.manejoControles = new ManejoControles(logicaJuegoRobots);
        Vpane.getChildren().add(sueloPane);
        Vpane.getChildren().add(botonesPane);
        this.escenario = new Scene(Vpane, 550, 550);
        stage.setScene(escenario);
        stage.show();
    }

    public void vistaIniciar() {
        vistaTablero.actualizarSuelo(sueloPane, stage);
        manejoDeBotones.agregarBotones(botonesPane);
        manejoControles.controles(stage, escenario, vistaTablero, sueloPane);
        manejoDeBotones.eventosBotones(stage, sueloPane, vistaTablero);
    }
}

