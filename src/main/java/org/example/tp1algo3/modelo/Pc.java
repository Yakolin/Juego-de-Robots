package org.example.tp1algo3.modelo;

import java.util.ArrayList;
import java.util.Random;


public class Pc {


    public enum Movimientos {
        MOV_ARRIBA, MOV_ABAJO, MOV_DERECHA, MOV_iZQUIERDA, MOV_SUP_DERECHA, MOV_SUP_INQUIERDA, MOV_INF_DERECHA, MOV_INF_IZQUIERDA

    }

    private final Coordenada coordenada;
    private boolean muerto;
    private int puntos;
    private int cantidadTeletransport;

    public Pc() {
        this.coordenada = new Coordenada(0, 0);
        this.muerto = false;
        this.puntos = 0;
        Random random = new Random();
        this.cantidadTeletransport = random.nextInt(10) + 5;
    }

    /**
     * PRE:Recibe un movimieto valido
     * POST:realiza el movimiento.
     **/
    public void movValidosTeclado(Movimientos movimiento) {

        if (movimiento == Movimientos.MOV_ARRIBA) {
            moverArriba();
        } else if (movimiento == Movimientos.MOV_ABAJO) {
            moverAbajo();
        } else if (movimiento == Movimientos.MOV_DERECHA) {
            moverDerecha();
        } else if (movimiento == Movimientos.MOV_iZQUIERDA) {
            moverIzquierda();
        } else if (movimiento == Movimientos.MOV_SUP_DERECHA) {
            moverSupDerecha();
        } else if (movimiento == Movimientos.MOV_SUP_INQUIERDA) {
            moverSupIzquierda();
        } else if (movimiento == Movimientos.MOV_INF_DERECHA) {
            moverInfDerecha();
        } else if (movimiento == Movimientos.MOV_INF_IZQUIERDA) {
            moverInfIzquierda();
        }
    }

    /**
     * PRE:Recibe dos enteros validos dentro del rango del tablero.
     * POST:Mueve el personaje a la posición (filNueva, colNueva) en función de su posición actual.
     **/
    public void movimientoMouse(int filNueva, int colNueva) {
        int fil = this.coordenada.getFil();
        int col = this.coordenada.getCol();
        if (filNueva < fil) {
            if (colNueva < col) {
                coordenada.sumarValorCoordenada(-1, -1);
            } else if (colNueva > col) {
                coordenada.sumarValorCoordenada(-1, 1);
            } else {
                coordenada.sumarValorCoordenada(-1, 0);
            }
        } else if (filNueva == fil) {
            if (colNueva < col) {
                coordenada.sumarValorCoordenada(0, -1);
            } else if (colNueva > col) {
                coordenada.sumarValorCoordenada(0, 1);
            }
        } else {
            if (colNueva < col) {
                coordenada.sumarValorCoordenada(1, -1);
            } else if (colNueva > col) {
                coordenada.sumarValorCoordenada(1, 1);
            } else {
                coordenada.sumarValorCoordenada(1, 0);
            }
        }
    }

    /**
     * PRE:tecla es un carácter válido.
     * POST:Mueve el personaje en la dirección indicada por la tecla,Si el personaje está muerto, no se realiza ningún movimiento.
     **/

    private void moverArriba() {
        coordenada.sumarValorCoordenada(-1, 0);
    }

    private void moverAbajo() {
        coordenada.sumarValorCoordenada(1, 0);
    }

    private void moverDerecha() {
        coordenada.sumarValorCoordenada(0, 1);
    }

    private void moverIzquierda() {
        coordenada.sumarValorCoordenada(0, -1);
    }

    private void moverSupIzquierda() {
        coordenada.sumarValorCoordenada(-1, -1);
    }

    private void moverSupDerecha() {
        coordenada.sumarValorCoordenada(-1, 1);
    }

    private void moverInfIzquierda() {
        coordenada.sumarValorCoordenada(1, -1);
    }

    private void moverInfDerecha() {
        coordenada.sumarValorCoordenada(1, 1);
    }

    /**
     * PRE:filTablero y colTablero son valores válidos que representan las dimensiones del tablero,robots contiene Npc válidos.
     * POST:Teletransporta al personaje a una posición aleatoria dentro del tablero,Verifica si la posición teletransportada coincide
     * con la de algún robot en el mapa robots. Si coincide, el personaje muere.
     **/
    public void teletransporAlertorio(int filTablero, int colTablero, ArrayList<Npc> robots) {
        Random random = new Random();
        coordenada.setFil(random.nextInt(filTablero - 1) + 1);
        coordenada.setCol(random.nextInt(colTablero - 1) + 1);
        for (Npc robot : robots) {
            if (this.coordenada.getFil() == robot.getCoordenada().getFil() && this.coordenada.getCol() == robot.getCoordenada().getCol()) {
                setMuerto(true);
                break;
            }
        }
    }

    /**
     * PRE:fila y columna son cadenas que representan números enteros válidos
     * POST:Teletransporta al personaje a la posición especificada por (fila, columna)
     **/
    public void teletransportaseSeguro(String fila, String columna) {
        int fil = Integer.parseInt(fila);
        int col = Integer.parseInt(columna);
        coordenada.setFil(fil);
        coordenada.setCol(col);
    }

    public void resetCantidadTeletransportes() {
        Random random = new Random();
        this.cantidadTeletransport = random.nextInt(10) + 5;
    }

    public int getCol() {
        return coordenada.getCol();
    }

    public int getFil() {
        return coordenada.getFil();
    }

    public boolean estadoMuerto() {
        return muerto;
    }

    public void setMuerto(boolean muerto) {
        this.muerto = muerto;
    }

    public void agregarPuntos(int puntos) {
        this.puntos += puntos;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getCantidadTeletransport() {
        return cantidadTeletransport;
    }

    public void setCantidadTeletransport(int cantidadTeletransport) {
        this.cantidadTeletransport = cantidadTeletransport;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }
}