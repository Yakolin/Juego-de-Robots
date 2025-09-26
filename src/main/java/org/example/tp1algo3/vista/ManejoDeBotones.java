package org.example.tp1algo3.vista;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.tp1algo3.modelo.LogicaJuegoRobots;

public class ManejoDeBotones {
    private final LogicaJuegoRobots logicaJuegoRobots;
    private final Button botonAgrandar;
    private final Button botonTeletransporAlertorio;
    private final Button botonTeletransporSeguro;
    private final Button botonQuedarseInmovil;

    private Text buttonText;

    public ManejoDeBotones(LogicaJuegoRobots logicaJuegoRobots) {
        this.logicaJuegoRobots = logicaJuegoRobots;
        this.botonTeletransporAlertorio = new Button("teletransportarse alertoriamente");
        this.botonTeletransporSeguro = new Button();
        this.botonQuedarseInmovil = new Button("quedarse inmovil");
        this.botonAgrandar = new Button("Setear tablero");
    }

    public void agregarBotones(HBox botonesPane) {
        botonesPane.getChildren().add(botonAgrandar);
        botonesPane.getChildren().add(botonQuedarseInmovil);
        botonesPane.getChildren().add(botonTeletransporAlertorio);
        botonesPane.getChildren().add(botonTeletransporSeguro);
        this.buttonText = new Text(String.format("teletransportarse seguro\ncantidad: %d", logicaJuegoRobots.getJugador().getCantidadTeletransport()));
        botonTeletransporSeguro.setGraphic(buttonText);
    }

    /**
     * POST:maneja los eventos que se relacionen a cada boton seleccionado.
     ***/
    public void eventosBotones(Stage stage, GridPane sueloPane, VistaTablero vistaTablero) {

        botonTeletransporAlertorio.setOnAction(_ -> {
            logicaJuegoRobots.teletransposeAlertoriamente();
            vistaTablero.actualizarSuelo(sueloPane, stage);
        });

        botonQuedarseInmovil.setOnAction(_ -> {
            logicaJuegoRobots.movilizarNPC();
            logicaJuegoRobots.detectarColisiones();
            vistaTablero.actualizarSuelo(sueloPane, stage);
        });

        botonTeletransporSeguro.setOnAction(_ -> {
            if (logicaJuegoRobots.getJugador().getCantidadTeletransport() == 0) {
                vistaTablero.mostrarAdvertencia("ya no te quedan teletranspor seguros");
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("cambiar pos");
                var InputPane = new VBox();
                InputPane.getChildren().add(new Label("Fila"));
                InputPane.getChildren().add(new Label("Columna"));
                TextField fil = new TextField();
                TextField col = new TextField();
                InputPane.getChildren().add(fil);
                InputPane.getChildren().add(col);
                alert.getDialogPane().setContent(InputPane);
                alert.showAndWait().ifPresent(accion -> {
                    if (accion == ButtonType.OK) {
                        String nuevaFila = fil.getText();
                        String nuevaCol = col.getText();
                        logicaJuegoRobots.teletransporSeguro(nuevaFila, nuevaCol);
                        logicaJuegoRobots.movilizarNPC();
                    }
                });
                vistaTablero.actualizarSuelo(sueloPane, stage);
                logicaJuegoRobots.getJugador().setCantidadTeletransport(logicaJuegoRobots.getJugador().getCantidadTeletransport() - 1);
                buttonText.setText(String.format("teletransportarse seguro\ncantidad: %d", logicaJuegoRobots.getJugador().getCantidadTeletransport()));

            }
        });
        botonAgrandar.setOnAction(_ -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cambiar tamaño del tablero");
            var InputPane = new VBox();
            InputPane.getChildren().add(new Label("cantidad de Filas"));
            InputPane.getChildren().add(new Label("cantidad de Columnas"));
            InputPane.getChildren().add(vistaTablero.getFilas());
            InputPane.getChildren().add(vistaTablero.getColumnas());
            alert.getDialogPane().setContent(InputPane);
            alert.showAndWait().ifPresent(accion -> {
                if (accion == ButtonType.OK) {
                    int filasInt = Integer.parseInt(vistaTablero.getFilas().getText());
                    int columnasInt = Integer.parseInt(vistaTablero.getColumnas().getText());
                    logicaJuegoRobots.getTablero().SetFilas(filasInt);
                    logicaJuegoRobots.getTablero().SetColumnas(columnasInt);

                    int nuevoTamanioFila = filasInt * vistaTablero.tamanioImagenes;
                    int nuevoTamanioCol = columnasInt * vistaTablero.tamanioImagenes;
                    sueloPane.setMaxWidth(nuevoTamanioFila);
                    sueloPane.setMaxHeight(nuevoTamanioCol);
                    stage.setWidth(logicaJuegoRobots.getTablero().GetFilas() * 35);
                    stage.setHeight(logicaJuegoRobots.getTablero().GetColumnas() * 35);
                    logicaJuegoRobots.resetearJuego();
                    vistaTablero.actualizarSuelo(sueloPane, stage);
                }
            });
        });


    }

}