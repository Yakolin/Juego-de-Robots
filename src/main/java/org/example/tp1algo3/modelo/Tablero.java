package org.example.tp1algo3.modelo;

public class Tablero {
    private int filas;
    private int columnas;

    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
    }

    public int GetFilas() {
        return filas;
    }

    public int GetColumnas() {
        return columnas;
    }

    public void SetFilas(int filasNuevas) {
        this.filas = filasNuevas;
    }

    public void SetColumnas(int columnasNuevas) {
        this.columnas = columnasNuevas;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }
}