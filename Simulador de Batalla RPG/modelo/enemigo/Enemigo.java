package co.edu.puj.rpg.modelo.enemigo;

import co.edu.puj.rpg.modelo.Personaje;

public class Enemigo {

    private String nombre;
    private int puntosVida;
    private int vidaMaxima;
    private int puntosAtaque;
    private int nivel;
    private double oro;
    private boolean estaVivo;
    private int experienciaRecompensa;

    public Enemigo(String nombre, int vidaMaxima, int puntosAtaque, int nivel, double oro, int experienciaRecompensa) {
        this.nombre = nombre;
        this.vidaMaxima = vidaMaxima > 0 ? vidaMaxima : 1;
        this.puntosAtaque = puntosAtaque > 0 ? puntosAtaque : 1;
        this.nivel = nivel >= 1 ? nivel : 1;
        this.oro = oro >= 0.0 ? oro : 0.0;
        this.experienciaRecompensa = experienciaRecompensa > 0 ? experienciaRecompensa : 0;
        this.setPuntosVida(this.vidaMaxima);
    }

    public void atacar(Personaje personaje) {
        personaje.recibirDano(this.puntosAtaque);
    }

    public void recibirDano(int cantidadDano) {
        if (cantidadDano < 0) {
            return;
        }
        this.setPuntosVida(this.puntosVida - cantidadDano);
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getPuntosVida() {
        return this.puntosVida;
    }

    public int getVidaMaxima() {
        return this.vidaMaxima;
    }

    public int getPuntosAtaque() {
        return this.puntosAtaque;
    }

    public int getNivel() {
        return this.nivel;
    }

    public double getOro() {
        return this.oro;
    }

    public boolean isEstaVivo() {
        return this.estaVivo;
    }

    public int getExperienciaRecompensa() {
        return this.experienciaRecompensa;
    }

    public void setPuntosVida(int vida) {
        if (vida <= 0) {
            this.puntosVida = 0;
            this.estaVivo = false;
        } else if (vida > this.vidaMaxima) {
            this.puntosVida = this.vidaMaxima;
            this.estaVivo = true;
        } else {
            this.puntosVida = vida;
            this.estaVivo = true;
        }
    }
}
