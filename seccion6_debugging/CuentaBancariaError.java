package com.taller.poo;

public class CuentaBancariaError {

    public static void main(String[] args) {
        CuentaBancaria sinLimite = new CuentaBancaria("444-444", "Pedro Ortiz", 100.0, null);

        boolean primerRetiro = sinLimite.retirar(60.0);
        System.out.printf("Retiro de 60.00: %b | Saldo: %.2f%n", primerRetiro, sinLimite.getSaldo());

        boolean segundoRetiro = sinLimite.retirar(60.0);
        System.out.printf("Retiro de 60.00: %b | Saldo: %.2f%n", segundoRetiro, sinLimite.getSaldo());

        Double limite = sinLimite.getLimiteCredito();
        double limiteSeguro = (limite != null) ? limite : 0.0;
        System.out.printf("Límite de crédito efectivo: %.2f%n", limiteSeguro);
    }
}
