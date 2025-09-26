package org.example.tp1algo3.modelo;

public class RobotRapido extends Npc {
    public RobotRapido(Coordenada coordenada) {
        super(coordenada);
    }

    @Override
    public void calcularMovimiento(Coordenada coordenadaJugador) {
        if (isMuerto()) return;
        realizarMovimiento(coordenadaJugador);
        realizarMovimiento(coordenadaJugador);
    }
}