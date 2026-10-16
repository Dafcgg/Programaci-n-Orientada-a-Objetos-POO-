package com.taller.poo;

public class VehiculoShadowing {

    private String marca;
    private int velocidadMaxima;

    public VehiculoShadowing(String marca, int velocidadMaxima) {
        this.marca = marca;
        this.velocidadMaxima = velocidadMaxima;
    }

    public void setVelocidadMaxima(int velocidadMaxima) {
        this.velocidadMaxima = velocidadMaxima;
    }

    public String getMarca() {
        return marca;
    }

    public int getVelocidadMaxima() {
        return velocidadMaxima;
    }

    public static void main(String[] args) {
        VehiculoShadowing vehiculo = new VehiculoShadowing("Kia", 160);
        System.out.printf("Marca: %s | Velocidad máxima: %d%n", vehiculo.getMarca(), vehiculo.getVelocidadMaxima());
        vehiculo.setVelocidadMaxima(190);
        System.out.printf("Marca: %s | Velocidad máxima: %d%n", vehiculo.getMarca(), vehiculo.getVelocidadMaxima());
    }
}
