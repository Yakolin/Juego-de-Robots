package org.example.tp1algo3.vista;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tp1algo3.modelo.LogicaJuegoRobots;
import org.example.tp1algo3.modelo.RobotLento;

import java.util.Objects;
import java.util.TreeMap;

public class VistaTablero {
    private final LogicaJuegoRobots logicaJuegoRobots;
    private final Image sueloPNG;
    private final Image jugadorJPG;
    private final Image robot1;
    private final Image robot2;
    private final Image fuego;
    private final TextField filas;
    private final TextField columnas;
    private final TreeMap<String, Integer> puntosArbol;
    public final int tamanioImagenes = 25;

    public VistaTablero(LogicaJuegoRobots logicaJuegoRobots) {
        this.logicaJuegoRobots = logicaJuegoRobots;
        this.filas = new TextField();
        this.columnas = new TextField();
        this.sueloPNG = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Floor.png")));
        this.jugadorJPG = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/jugador.png")));
        this.robot1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/robot1.png")));
        this.robot2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/robot2.png")));
        this.fuego = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/fuego.png")));
        this.puntosArbol = new TreeMap<>();
    }

    /**
     * PRE:recive sueloPane es un objeto GridPane válido,stage es un objeto Stage válido.
     * POST:Actualiza el título de la ventana stage para mostrar el nivel actual y los puntos del jugador.
     * Actualiza la representación visual del suelo en el GridPane con imágenes correspondientes a las casillas.
     * Posiciona al jugador en el GridPane en función de su posición actual en el modelo.
     * Posiciona a los enemigos en el GridPane en función de sus posiciones actuales en el modelo.
     **/
    public void actualizarSuelo(GridPane sueloPane, Stage stage) {

        stage.setTitle(String.format("Robots Nivel: %d Puntos: %d", logicaJuegoRobots.nivelActual(), logicaJuegoRobots.getJugador().getPuntos()));
        estadoJuego();
        sueloPane.getChildren().clear();
        if (sueloPNG == null) return;
        for (int i = 0; i < logicaJuegoRobots.getTablero().GetFilas(); i++) {
            for (int j = 0; j < logicaJuegoRobots.getTablero().GetColumnas(); j++) {
                ImageView sueloView = new ImageView(sueloPNG);
                sueloView.setFitHeight(tamanioImagenes);
                sueloView.setFitWidth(tamanioImagenes);
                sueloPane.add(sueloView, j, i);
            }
        }
        posicionarJugador(sueloPane);
        posicionarEnemigos(sueloPane);
    }

    private void posicionarJugador(GridPane sueloPane) {
        ImageView personaje = new ImageView(jugadorJPG);
        personaje.setFitHeight(tamanioImagenes);
        personaje.setFitWidth(tamanioImagenes);
        sueloPane.add(personaje, logicaJuegoRobots.getJugador().getCol(), logicaJuegoRobots.getJugador().getFil());
    }

    private void posicionarEnemigos(GridPane sueloPane) {
        for (int i = 0; i < logicaJuegoRobots.getCantidad_robots(); i++) {
            ImageView enemigo;
            if (logicaJuegoRobots.getRobots().get(i).isMuerto()) {
                enemigo = new ImageView(fuego);
            } else if (logicaJuegoRobots.getRobots().get(i) instanceof RobotLento) {
                enemigo = new ImageView(robot2);
            } else {
                enemigo = new ImageView(robot1);
            }
            enemigo.setFitHeight(tamanioImagenes);
            enemigo.setFitWidth(tamanioImagenes);
            sueloPane.add(enemigo, logicaJuegoRobots.getRobots().get(i).getCoordenada().getCol(), logicaJuegoRobots.getRobots().get(i).getCoordenada().getFil());
        }
    }

    public void mostrarMuerte() {
        String nombre = pedirNombre();
        mostrarTablaPuntos(nombre);
        logicaJuegoRobots.resetearJuego();
    }

    private void mostrarTablaPuntos(String nombre) {
        puntosArbol.put(String.format("%s : %d", nombre, logicaJuegoRobots.getJugador().getPuntos()), logicaJuegoRobots.getJugador().getPuntos());
        var AlertaTabla = new Alert(Alert.AlertType.INFORMATION);
        var VpaneTabla = new VBox();
        AlertaTabla.setHeaderText(null);
        for (String usuario : puntosArbol.keySet()) {
            Label label = new Label(usuario);
            VpaneTabla.getChildren().add(label);
        }
        AlertaTabla.getDialogPane().setContent(VpaneTabla);
        AlertaTabla.setHeaderText("TABLA DE PUNTOS");
        AlertaTabla.showAndWait();
    }

    /**
     * PRE:
     * POST:Muestra un mensaje inofrmando que murio el jugador y muestra los puntos.
     **/
    private String pedirNombre() {
        TextField nombreField = new TextField();
        Label puntosLabel = new Label(String.format("Puntos: %d", logicaJuegoRobots.getJugador().getPuntos()));
        Label nombreLabel = new Label("Nombre :");
        var Vpane = new VBox();
        var AlertaPedir = new Alert(Alert.AlertType.INFORMATION);
        Vpane.getChildren().add(nombreLabel);
        Vpane.getChildren().add(nombreField);
        Vpane.getChildren().add(puntosLabel);
        AlertaPedir.setHeaderText("Moriste :(");
        AlertaPedir.setTitle("INGRESA TU NOMBRE");
        AlertaPedir.getDialogPane().setContent(Vpane);
        AlertaPedir.showAndWait();
        return nombreField.getText();
    }

    /**
     * PRE:
     * POST:Muestra una alerta informando al usuario que ha pasado al siguiente nivel.
     **/
    public void mostrarPasoNivel() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Estado");
        alert.setHeaderText(null);
        alert.setContentText(String.format("PASASTE AL NIVEL : %d", logicaJuegoRobots.nivelActual()));
        alert.showAndWait();
    }

    /**
     * PRE:recibe texto que es una cadena que representa el mensaje de advertencia.
     * POST:Muestra una alerta de advertencia con el mensaje especificado.
     **/
    public void mostrarAdvertencia(String texto) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("teletransport");
        alert.setHeaderText(null);
        alert.setContentText(texto);
        alert.show();
    }

    public TextField getFilas() {
        return filas;
    }

    public TextField getColumnas() {
        return columnas;
    }

    /**
     * PRE:
     * POST:Si el jugador está muerto, muestra la pantalla de muerte y reinicia el juego.
     * Si el jugador ha pasado al siguiente nivel, avanza al siguiente nivel y muestra una alerta informando al usuario.
     **/
    private void estadoJuego() {
        if (logicaJuegoRobots.estadoJuego()) mostrarMuerte();
        if (logicaJuegoRobots.detectarPasoNivel()) {
            logicaJuegoRobots.pasarNivel();
            mostrarPasoNivel();
        }
    }
}