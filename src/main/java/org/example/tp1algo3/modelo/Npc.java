package org.example.tp1algo3.modelo;

public abstract class Npc {
    private Coordenada coordenada;
    private boolean muerto;
    private boolean puntuado;

    public Npc(Coordenada coordenada) {
        this.coordenada = coordenada;
        this.muerto = false;
        this.puntuado = false;
    }

    public void setCoordenada(Coordenada coordenadaNueva) {
        this.coordenada = coordenadaNueva;
    }

    /**
     * POST: calcula el movimiento hacia la posicion del jugador.
     **/
    public abstract void calcularMovimiento(Coordenada coordenadaJugador);

    /**
     * POST: realiza el movimiento del npc.
     **/
    public void realizarMovimiento(Coordenada coordenadaJugador) {
        Coordenada nuevaCoordenada = calcularNuevoMovimiento(coordenadaJugador);
        setCoordenada(nuevaCoordenada);
    }

    /**
     * POST: mata al jugador y marca como puntuado al npc.
     **/
    public void transformarFuego() {
        this.puntuado = true;
        this.muerto = true;
    }

    /**
     * PRE: recibe la coordenada del jugador.
     * POST: Calcula la nueva coordenada que se movera el robot hacia el jugador
     **/
    private Coordenada calcularNuevoMovimiento(Coordenada coordenadaJugador) {
        int dirFila = 0;
        int dirCol = 0;
        if (coordenadaJugador.getFil() > coordenada.getFil()) dirFila = 1;
        else if (coordenadaJugador.getFil() < coordenada.getFil()) dirFila = -1;
        if (coordenadaJugador.getCol() > coordenada.getCol()) dirCol = 1;
        else if (coordenadaJugador.getCol() < coordenada.getCol()) dirCol = -1;
        return new Coordenada(dirFila + coordenada.getFil(), dirCol + coordenada.getCol());
    }

    public boolean isMuerto() {
        return muerto;
    }

    public boolean isPuntuado() {
        return puntuado;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }
}