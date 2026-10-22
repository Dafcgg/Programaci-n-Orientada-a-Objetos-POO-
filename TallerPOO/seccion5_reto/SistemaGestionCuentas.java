package com.taller.poo;

import java.util.Scanner;

public class SistemaGestionCuentas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CuentaBancaria[] cuentas = new CuentaBancaria[3];
        cuentas[0] = new CuentaBancaria("111-111", "Ana Gómez", 1000.0, 200.0);
        cuentas[1] = new CuentaBancaria("222-222", "Luis Pérez", 500.0, 100.0);
        cuentas[2] = new CuentaBancaria("333-333", "Marta Ruiz", 250.0, 50.0);

        int opcion;

        do {
            System.out.printf("%n===== SISTEMA DE GESTIÓN DE CUENTAS =====%n");
            System.out.printf("1. Depositar%n");
            System.out.printf("2. Retirar%n");
            System.out.printf("3. Reporte consolidado%n");
            System.out.printf("4. Salir%n");
            System.out.print("Seleccione una opción: ");

            String entrada = scanner.nextLine().trim();
            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Número de cuenta: ");
                    String numeroDeposito = scanner.nextLine().trim();
                    CuentaBancaria cuentaDeposito = buscarCuenta(cuentas, numeroDeposito);
                    if (cuentaDeposito == null) {
                        System.out.printf("La cuenta %s no existe.%n", numeroDeposito);
                    } else {
                        double montoDeposito = leerMonto(scanner);
                        if (montoDeposito <= 0) {
                            System.out.printf("El monto debe ser mayor a cero. El depósito no fue realizado.%n");
                        } else if (cuentaDeposito.depositar(montoDeposito)) {
                            System.out.printf("Depósito exitoso. Nuevo saldo: %.2f%n", cuentaDeposito.getSaldo());
                        } else {
                            System.out.printf("No fue posible realizar el depósito.%n");
                        }
                    }
                    break;
                case 2:
                    System.out.print("Número de cuenta: ");
                    String numeroRetiro = scanner.nextLine().trim();
                    CuentaBancaria cuentaRetiro = buscarCuenta(cuentas, numeroRetiro);
                    if (cuentaRetiro == null) {
                        System.out.printf("La cuenta %s no existe.%n", numeroRetiro);
                    } else {
                        double montoRetiro = leerMonto(scanner);
                        if (montoRetiro <= 0) {
                            System.out.printf("El monto debe ser mayor a cero. El retiro no fue realizado.%n");
                        } else if (cuentaRetiro.retirar(montoRetiro)) {
                            System.out.printf("Retiro exitoso. Nuevo saldo: %.2f%n", cuentaRetiro.getSaldo());
                        } else {
                            System.out.printf("Fondos insuficientes. El retiro no fue realizado.%n");
                        }
                    }
                    break;
                case 3:
                    StringBuilder reporte = new StringBuilder();
                    double total = 0.0;
                    reporte.append(String.format("%n--- REPORTE CONSOLIDADO ---%n"));
                    for (int i = 0; i < cuentas.length; i++) {
                        CuentaBancaria c = cuentas[i];
                        reporte.append(String.format("Cuenta %s | %s | Saldo: %.2f%n",
                                c.getNumeroCuenta(), c.getTitular(), c.getSaldo()));
                        total += c.getSaldo();
                    }
                    reporte.append(String.format("Saldo total acumulado: %.2f%n", total));
                    System.out.printf("%s", reporte);
                    break;
                case 4:
                    System.out.printf("Saliendo del sistema. ¡Hasta pronto!%n");
                    break;
                default:
                    System.out.printf("Opción inválida. Intente de nuevo.%n");
                    break;
            }
        } while (opcion != 4);

        scanner.close();
    }

    private static CuentaBancaria buscarCuenta(CuentaBancaria[] cuentas, String numeroCuenta) {
        for (int i = 0; i < cuentas.length; i++) {
            if (cuentas[i].getNumeroCuenta().equals(numeroCuenta)) {
                return cuentas[i];
            }
        }
        return null;
    }

    private static double leerMonto(Scanner scanner) {
        System.out.print("Monto: ");
        String entrada = scanner.nextLine().trim();
        try {
            return Double.parseDouble(entrada);
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }
}
