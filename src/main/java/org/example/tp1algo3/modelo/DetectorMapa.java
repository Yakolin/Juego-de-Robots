package org.example.tp1algo3.modelo;

import java.util.ArrayList;

public class DetectorMapa {

    /**
     * PRE:npcs es un HashMap<Integer, Npc> que contiene los NPCs presentes en el mapa,jugador es un objeto Pc que representa al jugador.
     * POST:Registra las colisiones entre los NPCs y el jugador en el mapa.
     * Si dos NPCs colisionan entre sí, uno de ellos se transforma en fuego y el otro recibe puntos adicionales si aún no ha sido puntuado.
     * Si un NPC colisiona con el jugador, el jugador es marcado como muerto.
     **/
    public void registrarColisiones(ArrayList<Npc> npcs, Pc jugador) {
        for (Npc ejemplo : npcs) {
            for (Npc npc : npcs) {
                if (npc != ejemplo && npc.getCoordenada().equals(ejemplo.getCoordenada())) {
                    if (!ejemplo.isPuntuado()) jugador.agregarPuntos(100);
                    ejemplo.transformarFuego();
                    break;
                }
            }
            if (ejemplo.getCoordenada().equals(jugador.getCoordenada())) jugador.setMuerto(true);
        }
    }

    /**
     * PRE:recibe npcs es un HashMap<Integer, Npc> que contiene los NPCs presentes en el mapa.
     * POST:Devuelve verdadero si todos los NPCs en el mapa están muertos; de lo contrario, devuelve falso.
     **/
    public boolean nivelPasado(ArrayList<Npc> npcs) {
        boolean todosMuertos = true;
        for (Npc npc : npcs) {
            if (!npc.isMuerto()) {
                todosMuertos = false;
                break;
            }
        }
        return todosMuertos;
    }
}