package model;

import exception.CapacidadExcedidaException;
import exception.SinManaException;
import exception.VidaInsuficienteException;
import model.enums.EstadoPersonaje;
import model.enums.TipoPersonaje;

import java.util.HashSet;
import java.util.Set;

public class Personaje {

    private static final double VIDA_BASE = 100.0;
    private static final double MANA_BASE = 50.0;
    private static final int CAPACIDAD_HABILIDADES_POR_DEFECTO = 5;

    private final String nombre;
    private final TipoPersonaje tipoPersonaje;
    private EstadoPersonaje estado = EstadoPersonaje.VIVO;
    private final Set<String> habilidades = new HashSet<String>();
    private double vidaActual;
    private final double vidaMaxima;
    private double manaActual;
    private final double manaMaximo;
    private final int capacidadMaximaHabilidades;

    public Personaje(String nombre, TipoPersonaje tipoPersonaje) {
        this(nombre, tipoPersonaje, CAPACIDAD_HABILIDADES_POR_DEFECTO);
    }

    public Personaje(String nombre, TipoPersonaje tipoPersonaje, int capacidadMaximaHabilidades) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del personaje no puede estar vacio");
        }
        if (tipoPersonaje == null) {
            throw new IllegalArgumentException("El tipo de personaje es obligatorio");
        }
        if (capacidadMaximaHabilidades <= 0) {
            throw new IllegalArgumentException("La capacidad de habilidades debe ser mayor que cero");
        }
        this.nombre = nombre.trim();
        this.tipoPersonaje = tipoPersonaje;
        this.capacidadMaximaHabilidades = capacidadMaximaHabilidades;
        this.vidaMaxima = VIDA_BASE * tipoPersonaje.getBonificadorDefensaBase();
        this.manaMaximo = MANA_BASE * tipoPersonaje.getBonificadorAtaqueBase();
        this.vidaActual = this.vidaMaxima;
        this.manaActual = this.manaMaximo;
    }

    public boolean aprenderHabilidad(String habilidad) throws CapacidadExcedidaException {
        if (habilidad == null || habilidad.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la habilidad no puede estar vacio");
        }
        if (habilidades.size() >= capacidadMaximaHabilidades) {
            throw new CapacidadExcedidaException(
                    "El libro de habilidades de " + nombre + " esta lleno (maximo " + capacidadMaximaHabilidades + ")");
        }
        return habilidades.add(habilidad.trim());
    }

    public Set<String> obtenerHabilidades() {
        return habilidades;
    }

    public void ejecutarAtaqueEspecial(double costoMana) throws SinManaException {
        if (Double.isNaN(costoMana) || costoMana < 0) {
            throw new IllegalArgumentException("El costo de mana debe ser un numero igual o mayor que cero");
        }
        if (costoMana > manaActual) {
            throw new SinManaException(
                    "Mana insuficiente: se requieren " + costoMana + " y " + nombre + " solo tiene " + manaActual);
        }
        manaActual -= costoMana;
    }

    public void recibirDano(double dano) {
        if (Double.isNaN(dano) || dano < 0) {
            throw new IllegalArgumentException("El dano debe ser un numero igual o mayor que cero");
        }
        vidaActual -= dano;
        if (vidaActual <= 0) {
            vidaActual = 0;
            estado = EstadoPersonaje.DERROTADO;
        }
    }

    public void consumirVida(double costoVida) {
        if (Double.isNaN(costoVida) || costoVida < 0) {
            throw new IllegalArgumentException("El costo de vida debe ser un numero igual o mayor que cero");
        }
        if (costoVida >= vidaActual) {
            throw new VidaInsuficienteException(
                    "Vida insuficiente: el costo es " + costoVida + " y " + nombre + " solo tiene " + vidaActual);
        }
        vidaActual -= costoVida;
    }

    public double calcularAtaque(double ataqueBase) {
        return ataqueBase * tipoPersonaje.getBonificadorAtaqueBase();
    }

    public double calcularDefensa(double defensaBase) {
        return defensaBase * tipoPersonaje.getBonificadorDefensaBase();
    }

    public String getNombre() {
        return nombre;
    }

    public TipoPersonaje getTipoPersonaje() {
        return tipoPersonaje;
    }

    public EstadoPersonaje getEstado() {
        return estado;
    }

    public void setEstado(EstadoPersonaje estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        this.estado = estado;
    }

    public double getVidaActual() {
        return vidaActual;
    }

    public double getVidaMaxima() {
        return vidaMaxima;
    }

    public double getManaActual() {
        return manaActual;
    }

    public double getManaMaximo() {
        return manaMaximo;
    }

    public int getCapacidadMaximaHabilidades() {
        return capacidadMaximaHabilidades;
    }
}
