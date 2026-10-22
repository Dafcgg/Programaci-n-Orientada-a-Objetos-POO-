package com.taller.poo;

public class CuentaBancaria {

    private String numeroCuenta;
    private String titular;
    private double saldo;
    private Double limiteCredito;

    /*
     * PREGUNTAS DE ANÁLISIS:
     * 1. ¿Qué diferencia ocurre a nivel de memoria y asignación por defecto al declarar 'saldo' como double
     *    primitivo frente a 'limiteCredito' como Wrapper Double?
     *    RESPUESTA: 'saldo' es un primitivo: la variable guarda directamente el número (8 bytes) dentro del objeto
     *    y, si no se inicializa, Java le asigna 0.0 por defecto. 'limiteCredito' es un Wrapper: la variable solo
     *    guarda una referencia y el valor vive en un objeto Double separado en el heap (mayor consumo por la
     *    cabecera del objeto y la gestión del recolector de basura). Si no se inicializa, su valor por defecto
     *    es null, es decir, no apunta a ningún objeto.
     * 2. ¿Por qué una operación aritmética sobre un atributo no inicializado de tipo Double provoca una
     *    NullPointerException, mientras que sobre un double primitivo opera con 0.0?
     *    RESPUESTA: los operadores aritméticos trabajan con valores primitivos, así que Java convierte el Double
     *    a double automáticamente (unboxing) llamando internamente a doubleValue() sobre el objeto. Si la
     *    referencia es null, no hay objeto sobre el cual invocar ese método y se lanza NullPointerException.
     *    En cambio, el primitivo double siempre tiene un valor real (0.0 por defecto), por lo que la operación
     *    se realiza sin problema.
     */

    public CuentaBancaria() {
        this("000000", "Sin Nombre", 0.0, 0.0);
    }

    public CuentaBancaria(String numeroCuenta, String titular) {
        this(numeroCuenta, titular, 0.0, 100.0);
    }

    public CuentaBancaria(String numeroCuenta, String titular, double saldo, Double limiteCredito) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldo;
        this.limiteCredito = limiteCredito;
    }

    public boolean depositar(double monto) {
        if (monto > 0) {
            this.saldo += monto;
            return true;
        } else {
            return false;
        }
    }

    public boolean retirar(double monto) {
        if (monto > 0) {
            double credito = (limiteCredito != null) ? limiteCredito : 0.0;
            if (saldo + credito >= monto) {
                this.saldo -= monto;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public Double getLimiteCredito() {
        return limiteCredito;
    }
}
