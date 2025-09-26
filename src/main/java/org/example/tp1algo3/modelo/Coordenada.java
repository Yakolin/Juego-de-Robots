package org.example.tp1algo3.modelo;

public class Coordenada {
    private int fil;
    private int col;

    public Coordenada(int fil, int col) {
        this.fil = fil;
        this.col = col;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordenada other = (Coordenada) obj;
        return fil == other.getFil() && col == other.getCol();
    }

    public void setFil(int fil) {
        this.fil = fil;
    }

    public void setCol(int col) {
        this.col = col;
    }

    /*
        PRE: recibe un valor POSITIVO o NEGATIVO
        Post: el valor de fil y col recibido sera SUMADO a la coordenada
    */
    public void sumarValorCoordenada(int filSuma, int colSuma) {
        this.fil += filSuma;
        this.col += colSuma;
    }

    public int getCol() {
        return col;
    }

    public int getFil() {
        return fil;
    }
}
