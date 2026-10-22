package com.taller.poo;

public class Principal {

    public static void main(String[] args) {
        Vehiculo vehiculo1 = new Vehiculo("Toyota", "Corolla", 15000.50, 180, true);
        Vehiculo vehiculo2 = new Vehiculo();
        Vehiculo vehiculo3 = new Vehiculo("Mazda", "CX-5", 42000.0, 200, false);
        Vehiculo[] vehiculos = {vehiculo1, vehiculo2, vehiculo3};

        Estudiante estudiante1 = new Estudiante("E-001", "Laura Martínez", 4.35, true);
        Estudiante estudiante2 = new Estudiante("E-002", "Andrés Rojas", 3.80, true);
        Estudiante estudiante3 = new Estudiante("E-003", "Marta Ruiz", 2.95, false);
        Estudiante[] estudiantes = {estudiante1, estudiante2, estudiante3};

        CuentaBancaria cuenta1 = new CuentaBancaria("1001", "Carlos Pérez", 1000.75, 500.0);
        CuentaBancaria cuenta2 = new CuentaBancaria("1002", "Ana Gómez");
        CuentaBancaria cuenta3 = new CuentaBancaria();

        boolean deposito1 = cuenta1.depositar(250.0);
        boolean deposito2 = cuenta2.depositar(200.0);
        boolean retiro2 = cuenta2.retirar(150.0);
        boolean depositoInvalido = cuenta3.depositar(-20.0);
        boolean retiroRechazado = cuenta3.retirar(80.0);

        double dolares = 250.0;
        double tasa = 0.92;
        double euros = ConversorMoneda.convertirDolaresAEuros(dolares, tasa);
        double eurosInvalido = ConversorMoneda.convertirDolaresAEuros(-10.0, tasa);

        System.out.printf("=== REPORTE DE VEHÍCULOS ===%n");
        for (int i = 0; i < vehiculos.length; i++) {
            Vehiculo v = vehiculos[i];
            System.out.printf("[%d] Marca: %s | Modelo: %s | KM: %.2f | VelMax: %d km/h | Encendido: %b%n",
                    i + 1, v.getMarca(), v.getModelo(), v.getKilometraje(), v.getVelocidadMaxima(), v.isEncendido());
        }

        System.out.printf("%n=== REPORTE DE ESTUDIANTES ===%n");
        for (int i = 0; i < estudiantes.length; i++) {
            System.out.printf("[%d] %s%n", i + 1, estudiantes[i].obtenerReporteAcademicos());
        }

        System.out.printf("%n=== OPERACIONES BANCARIAS ===%n");
        System.out.printf("Depósito de 250.00 en %s: %b%n", cuenta1.getNumeroCuenta(), deposito1);
        System.out.printf("Depósito de 200.00 en %s: %b%n", cuenta2.getNumeroCuenta(), deposito2);
        System.out.printf("Retiro de 150.00 en %s: %b%n", cuenta2.getNumeroCuenta(), retiro2);
        System.out.printf("Depósito de -20.00 en %s: %b%n", cuenta3.getNumeroCuenta(), depositoInvalido);
        System.out.printf("Retiro de 80.00 en %s: %b%n", cuenta3.getNumeroCuenta(), retiroRechazado);

        System.out.printf("%n=== REPORTE DE CUENTAS BANCARIAS ===%n");
        CuentaBancaria[] cuentas = {cuenta1, cuenta2, cuenta3};
        for (int i = 0; i < cuentas.length; i++) {
            CuentaBancaria c = cuentas[i];
            System.out.printf("Cuenta: %s | Titular: %s | Saldo Final: %s | Crédito: %s%n",
                    c.getNumeroCuenta(), c.getTitular(),
                    ConversorMoneda.formatearMoneda(c.getSaldo()),
                    ConversorMoneda.formatearMoneda(c.getLimiteCredito()));
        }

        System.out.printf("%n=== CONVERSOR DE MONEDA ===%n");
        System.out.printf("%s USD a tasa %.2f = %s EUR%n",
                ConversorMoneda.formatearMoneda(dolares), tasa, ConversorMoneda.formatearMoneda(euros));
        System.out.printf("Conversión con monto inválido (-10.00): %s%n", ConversorMoneda.formatearMoneda(eurosInvalido));
    }
}
