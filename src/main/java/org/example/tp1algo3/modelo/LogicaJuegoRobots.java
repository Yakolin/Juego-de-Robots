package org.example.tp1algo3.modelo;

import java.util.ArrayList;
import java.util.Random;

public class LogicaJuegoRobots {
    private final DetectorMapa detectorMapa;
    private final Tablero tablero;
    private final Pc jugador;
    private ArrayList<Npc> robots;
    private int cantidadRobots;
    private int nivelActual;

    public LogicaJuegoRobots() {
        this.nivelActual = 1;
        this.cantidadRobots = 4;
        this.tablero = new Tablero(15, 15);
        this.jugador = new Pc();
        respawnJugador(jugador);
        this.robots = generarRobots();
        this.detectorMapa = new DetectorMapa();
    }

    /**
     * PRE: recibe el hash de npc y una fila y columna.
     * POST:verifica si las posiciones de repiten ,en ese caso devuelve true en caso contarrio devuelve false.
     **/
    private boolean robotRepetidos(ArrayList<Npc> robots, Coordenada nuevaCoordenada) {
        if (nuevaCoordenada.equals(jugador.getCoordenada())) return true;
        for (Npc robot : robots)
            if (nuevaCoordenada.equals(robot.getCoordenada())) return true;
        return false;
    }

    /**
     * PRE:
     * POST:Devuelve un ArrayList<Npc> que contiene los robots generados.
     * Los robots están distribuidos aleatoriamente en el tablero,No hay robots en las mismas posiciones que el jugador,
     * Los robots tienen sus posiciones inicializadas con valores válidos dentro de las dimensiones del tablero.
     **/
    //ESTA BIEN QUE CREE UNA COORDENADA? LAS COORDENADAS Y LOS ROBOTS TIENEN UNA RELACION DE ASOCIACION MAS QUE DE COMPOSICION
    private ArrayList<Npc> generarRobots() {
        Random random = new Random();
        ArrayList<Npc> robots = new ArrayList<>();
        for (int i = 0; i < cantidadRobots; i++) {
            Coordenada nuevaCoordenada = new Coordenada(0, 0);
            do {
                nuevaCoordenada.setFil(random.nextInt(tablero.getFilas() - 1) + 1);
                nuevaCoordenada.setCol(random.nextInt(tablero.getColumnas() - 1) + 1);
            } while (robotRepetidos(robots, nuevaCoordenada));
            if ((i % 2) == 0) {
                RobotRapido robot = new RobotRapido(nuevaCoordenada);
                robots.add(robot);
            } else {
                RobotLento robot = new RobotLento(nuevaCoordenada);
                robots.add(robot);
            }
        }
        return robots;
    }

    public void movimientoValidos(Pc.Movimientos movimiento) {
        jugador.movValidosTeclado(movimiento);
    }

    public void teletransposeAlertoriamente() {
        jugador.teletransporAlertorio(tablero.GetFilas(), tablero.GetColumnas(), robots);
    }

    public void movilizarNPC() {
        for (Npc robot : robots)
            robot.calcularMovimiento(jugador.getCoordenada());
    }

    public void detectarColisiones() {
        detectorMapa.registrarColisiones(robots, jugador);
    }

    public boolean detectarPasoNivel() {
        return detectorMapa.nivelPasado(robots);
    }

    /**
     * PRE: recibe dos cadenas que pueden ser cualquier cosa.
     * POST:Teletransporta al jugador a la posición especificada , verifica si la posicion  es valida.
     **/
    public void teletransporSeguro(String fila, String columna) {
        if (fila.matches("[0-9]+") && columna.matches("[0-9]+"))
            if (Integer.parseInt(fila) < tablero.GetFilas() && Integer.parseInt(columna) < tablero.GetColumnas())
                jugador.teletransportaseSeguro(fila, columna);
    }

    public ArrayList<Npc> getRobots() {
        return robots;
    }

    public int getCantidad_robots() {
        return cantidadRobots;
    }

    public boolean estadoJuego() {
        return jugador.estadoMuerto();
    }

    public void movimientoValido(int fila, int col) {
        jugador.movimientoMouse(fila, col);
    }

    /**
     * POST: resetea al judador a la posicion inicial.
     **/
    private void respawnJugador(Pc jugador) {
        int fil = tablero.GetFilas() / 2 - 1;
        int col = tablero.GetColumnas() / 2 - 1;
        jugador.getCoordenada().setFil(fil);
        jugador.getCoordenada().setCol(col);
    }

    /**
     * posts: pasa de nivel y aumenta la cantidad de robots.
     **/
    public void pasarNivel() {
        respawnJugador(jugador);
        nivelActual++;
        int areaGrilla = tablero.GetColumnas() * tablero.GetFilas();
        int limiteRobots = (int) (areaGrilla * 0.5);
        if (cantidadRobots < limiteRobots) cantidadRobots += 2;
        robots.clear();
        robots = generarRobots();
    }

    public int nivelActual() {
        return nivelActual;
    }

    /**
     * PRE:
     * POST:resetea el juego al estado inicial.
     **/
    public void resetearJuego() {
        respawnJugador(this.jugador);
        robots.clear();
        cantidadRobots = 4;
        robots = generarRobots();
        nivelActual = 1;
        jugador.setMuerto(false);
        jugador.setPuntos(0);
        jugador.resetCantidadTeletransportes();
    }


    public Tablero getTablero() {
        return tablero;
    }

    public Pc getJugador() {
        return jugador;
    }
}