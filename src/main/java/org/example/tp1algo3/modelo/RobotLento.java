package org.example.tp1algo3.modelo;

public class RobotLento extends Npc {
    public RobotLento(Coordenada coordenada) {
        super(coordenada);
    }

    /**
     * POST:si no esta muerto el jugador ,realiza el movimiento hacia el .
     **/
    @Override
    public void calcularMovimiento(Coordenada coordenadaJugador) {
        if (isMuerto()) return;
        realizarMovimiento(coordenadaJugador);
    }
}