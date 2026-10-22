/*
 * ANÁLISIS: PRIMITIVOS VS WRAPPERS EN Vehiculo
 * Se eligieron los primitivos double (kilometraje), int (velocidadMaxima) y boolean (encendido).
 *
 * Consumo de memoria (Stack vs Heap): un primitivo guarda el valor directamente: en el Stack si es una
 * variable local, o dentro del propio objeto si es un atributo (double 8 bytes, int 4 bytes, boolean 1 byte
 * aproximado). Un Wrapper (Double, Integer, Boolean) es un objeto que vive en el Heap: la variable solo guarda
 * una referencia y el objeto añade cabecera y carga para el recolector de basura, por lo que consume más memoria.
 *
 * Valores nulos y NullPointerException: un primitivo nunca puede ser null (por defecto vale 0, 0.0 o false).
 * Un Wrapper sí admite null, y operar con él en ese estado (unboxing) lanza NullPointerException.
 * Elegir primitivos previene ese error: un vehículo siempre tiene kilometraje, velocidad máxima y estado de
 * motor, por lo que null no aporta significado. Si se usara un Wrapper habría que validar != null antes de operar.
 */
package com.taller.poo;

public class Vehiculo {

    private String marca;
    private String modelo;
    private double kilometraje;
    private int velocidadMaxima;
    private boolean encendido;

    public Vehiculo() {
        this.marca = "Genérico";
        this.modelo = "Sin Modelo";
        this.kilometraje = 0.0;
        this.velocidadMaxima = 120;
        this.encendido = false;
    }

    public Vehiculo(String marca, String modelo, double kilometraje, int velocidadMaxima, boolean encendido) {
        this.marca = marca;
        this.modelo = modelo;
        this.kilometraje = kilometraje;
        this.velocidadMaxima = velocidadMaxima;
        this.encendido = encendido;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getKilometraje() {
        return kilometraje;
    }

    public int getVelocidadMaxima() {
        return velocidadMaxima;
    }

    public boolean isEncendido() {
        return encendido;
    }
}
