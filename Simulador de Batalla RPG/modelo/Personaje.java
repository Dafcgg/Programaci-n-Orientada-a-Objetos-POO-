package co.edu.puj.rpg.modelo;

import co.edu.puj.rpg.modelo.enemigo.Enemigo;

public class Personaje {

    private String nombre;
    private int puntosVida;
    private int vidaMaxima;
    private int puntosAtaque;
    private int nivel;
    private double oro;
    private boolean estaVivo;
    private int experiencia;
    private boolean ultimoGolpeCritico;

    public Personaje(String nombre, int vidaMaxima, int puntosAtaque, double oro) {
        this.nombre = nombre;
        this.vidaMaxima = vidaMaxima > 0 ? vidaMaxima : 1;
        this.puntosAtaque = puntosAtaque > 0 ? puntosAtaque : 1;
        this.experiencia = 0;
        this.ultimoGolpeCritico = false;
        this.setNivel(1);
        this.setPuntosVida(this.vidaMaxima);
        this.setOro(oro);
    }

    public Personaje(String nombre) {
        this(nombre, 100, 15, 50.0);
    }

    public void atacar(Enemigo enemigo) {
        int dano = this.puntosAtaque;
        this.ultimoGolpeCritico = Math.random() < 0.15;
        if (this.ultimoGolpeCritico) {
            dano = dano * 2;
        }
        enemigo.recibirDano(dano);
    }

    public void usarPocion(int cantidadRestaurada) {
        if (!this.estaVivo || cantidadRestaurada <= 0) {
            return;
        }
        this.setPuntosVida(this.puntosVida + cantidadRestaurada);
    }

    public void recibirDano(int cantidadDano) {
        if (cantidadDano < 0) {
            return;
        }
        this.setPuntosVida(this.puntosVida - cantidadDano);
    }

    public boolean ganarExperiencia(int puntos) {
        if (puntos <= 0) {
            return false;
        }
        this.experiencia += puntos;
        boolean subioNivel = false;
        int umbral = this.getUmbralExperiencia();
        while (this.experiencia >= umbral) {
            this.experiencia -= umbral;
            this.setNivel(this.nivel + 1);
            subioNivel = true;
            umbral = this.getUmbralExperiencia();
        }
        return subioNivel;
    }

    public int getUmbralExperiencia() {
        return this.nivel * 50;
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

    public int getExperiencia() {
        return this.experiencia;
    }

    public boolean isUltimoGolpeCritico() {
        return this.ultimoGolpeCritico;
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

    public void setOro(double oro) {
        if (oro < 0.0) {
            System.out.println("Saldo insuficiente: la operacion fue rechazada.");
            return;
        }
        this.oro = oro;
    }

    public void setNivel(int nivel) {
        if (nivel < 1) {
            System.out.println("Nivel invalido: se conserva el nivel actual.");
            return;
        }
        this.nivel = nivel;
    }
}
