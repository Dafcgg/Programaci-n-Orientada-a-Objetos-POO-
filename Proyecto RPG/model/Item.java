package model;

import model.enums.RaridadItem;

public class Item {

    private String nombre;
    private RaridadItem raridadItem;
    private double statBase;

    public Item(String nombre, RaridadItem raridadItem, double statBase) {
        setNombre(nombre);
        setRaridadItem(raridadItem);
        setStatBase(statBase);
    }

    public String getNombre() {
        return nombre;
    }

    public final void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del item no puede estar vacio");
        }
        this.nombre = nombre.trim();
    }

    public RaridadItem getRaridadItem() {
        return raridadItem;
    }

    public final void setRaridadItem(RaridadItem raridadItem) {
        if (raridadItem == null) {
            throw new IllegalArgumentException("La raridad del item es obligatoria");
        }
        this.raridadItem = raridadItem;
    }

    public double getStatBase() {
        return statBase;
    }

    public final void setStatBase(double statBase) {
        if (Double.isNaN(statBase) || statBase < 0) {
            throw new IllegalArgumentException("El stat base debe ser un numero igual o mayor que cero");
        }
        this.statBase = statBase;
    }

    public double calcularStatFinal() {
        return statBase * raridadItem.getMultiplicadorStat();
    }
}
