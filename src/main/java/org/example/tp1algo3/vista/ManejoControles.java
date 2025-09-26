package org.example.tp1algo3.vista;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.tp1algo3.modelo.LogicaJuegoRobots;
import org.example.tp1algo3.modelo.Pc;

public class ManejoControles {
    public enum ResultadoMovimiento {
        VALIDO, INVALIDO_LETRA_VACIA, INVALIDO_LETRA_LARGA, INVALIDO_CARACTER_DIGITO, FUERA_DEL_LIMITE, NO_VALIDA
    }
    private final LogicaJuegoRobots logicaJuegoRobots;

    public ManejoControles(LogicaJuegoRobots logicaJuegoRobots) {
        this.logicaJuegoRobots = logicaJuegoRobots;
    }

    private ResultadoMovimiento validarMovimiento(String letra) {

        if (letra.isEmpty()) return ResultadoMovimiento.INVALIDO_LETRA_VACIA;
        if (letra.length() > 1) return ResultadoMovimiento.INVALIDO_LETRA_LARGA;

        char caracter = letra.charAt(0);
        if (Character.isDigit(caracter)) return ResultadoMovimiento.INVALIDO_CARACTER_DIGITO;
        char caracterMinuscula = Character.toLowerCase(caracter);
        int fil = logicaJuegoRobots.getJugador().getFil();
        int col = logicaJuegoRobots.getJugador().getCol();
        int filTablero = logicaJuegoRobots.getTablero().getFilas() - 1;
        int colTablero = logicaJuegoRobots.getTablero().getColumnas() - 1;

        if (caracterMinuscula == 'w' && fil == 0) return ResultadoMovimiento.FUERA_DEL_LIMITE;
        if (caracterMinuscula == 's' && fil == filTablero) return ResultadoMovimiento.FUERA_DEL_LIMITE;
        if (caracterMinuscula == 'd' && col == colTablero) return ResultadoMovimiento.FUERA_DEL_LIMITE;
        if (caracterMinuscula == 'a' && col == 0) return ResultadoMovimiento.FUERA_DEL_LIMITE;

        if (caracterMinuscula == 'q' && (fil == 0 || col == 0)) return ResultadoMovimiento.FUERA_DEL_LIMITE;
        if (caracterMinuscula == 'e' && (fil == 0 || col == colTablero)) return ResultadoMovimiento.FUERA_DEL_LIMITE;
        if (caracterMinuscula == 'x' && (fil == filTablero || col == colTablero))
            return ResultadoMovimiento.FUERA_DEL_LIMITE;
        if (caracterMinuscula == 'z' && (fil == filTablero || col == 0)) return ResultadoMovimiento.FUERA_DEL_LIMITE;

        if (caracterMinuscula != 'w' && caracterMinuscula != 's' && caracterMinuscula != 'd' && caracterMinuscula != 'a' && caracterMinuscula != 'q' && caracterMinuscula != 'e' && caracterMinuscula != 'x' && caracterMinuscula != 'z') {
            return ResultadoMovimiento.NO_VALIDA;
        }
        return ResultadoMovimiento.VALIDO;
    }

    /**
     * PRE:
     * POST:Registra eventos de teclado y clic del mouse en la escena y el GridPane.
     * Al presionar una tecla, Verifica si el movimiento es válido,Si el movimiento no es válido, muestra un mensaje de advertencia adecuado en la vista (vistaTablero).
     * Al hacer clic en el GridPane,Calcula la fila y la columna correspondientes al punto donde se hizo clic.
     * Realiza un movimiento válido del jugador utilizando el método movimientoValido del modelo.
     * y en ambos casos: Mueve los NPCs utilizando el método movilizarNPC del modelo, Detecta colisiones utilizando el método DetectarColisiones del modelo.
     * Actualiza el suelo en la vista.
     **/
    public void controles(Stage stage, Scene escenario, VistaTablero vistaTablero, GridPane sueloPane) {

        escenario.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            var resultado = validarMovimiento(keyEvent.getText());
            if (resultado != ResultadoMovimiento.VALIDO) {
                if (resultado == ResultadoMovimiento.INVALIDO_LETRA_VACIA) {
                    vistaTablero.mostrarAdvertencia("presione una tecla valida");
                } else if ((resultado == ResultadoMovimiento.INVALIDO_CARACTER_DIGITO)) {
                    vistaTablero.mostrarAdvertencia("presione una direccion, no un numero");
                } else if (resultado == ResultadoMovimiento.INVALIDO_LETRA_LARGA || resultado == ResultadoMovimiento.NO_VALIDA) {
                    vistaTablero.mostrarAdvertencia("precione una tecla valida");
                } else if (resultado == ResultadoMovimiento.FUERA_DEL_LIMITE) {
                    vistaTablero.mostrarAdvertencia("NO PODES SALIR DE LOS LIMITES DEL TABLERO");
                }

            } else {
                switch (keyEvent.getText().toLowerCase()) {
                    case "w":
                        logicaJuegoRobots.movimientoValidos(Pc.Movimientos.MOV_ARRIBA);
                        break;
                    case "s":
                        logicaJuegoRobots.movimientoValidos(Pc.Movimientos.MOV_ABAJO);
                        break;
                    case "d":
                        logicaJuegoRobots.movimientoValidos(Pc.Movimientos.MOV_DERECHA);
                        break;
                    case "a":
                        logicaJuegoRobots.movimientoValidos(Pc.Movimientos.MOV_iZQUIERDA);
                        break;
                    case "e":
                        logicaJuegoRobots.movimientoValidos(Pc.Movimientos.MOV_SUP_DERECHA);
                        break;
                    case "q":
                        logicaJuegoRobots.movimientoValidos(Pc.Movimientos.MOV_SUP_INQUIERDA);
                        break;
                    case "x":
                        logicaJuegoRobots.movimientoValidos(Pc.Movimientos.MOV_INF_DERECHA);
                        break;
                    case "z":
                        logicaJuegoRobots.movimientoValidos(Pc.Movimientos.MOV_INF_IZQUIERDA);
                        break;
                }
                vistaTablero.actualizarSuelo(sueloPane, stage);
                logicaJuegoRobots.movilizarNPC();
                logicaJuegoRobots.detectarColisiones();
                vistaTablero.actualizarSuelo(sueloPane, stage);
            }
        });
        sueloPane.setOnMouseClicked(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();
            double anchoNodo = ((GridPane) event.getSource()).getWidth() / (logicaJuegoRobots.getTablero().GetColumnas());
            double alturaNodo = ((GridPane) event.getSource()).getHeight() / (logicaJuegoRobots.getTablero().GetFilas());
            int fila = (int) (mouseY / alturaNodo);
            int columna = (int) (mouseX / anchoNodo);
            logicaJuegoRobots.movimientoValido(fila, columna);
            logicaJuegoRobots.movilizarNPC();
            logicaJuegoRobots.detectarColisiones();
            vistaTablero.actualizarSuelo(sueloPane, stage);

        });

    }
}