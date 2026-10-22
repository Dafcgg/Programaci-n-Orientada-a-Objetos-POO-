package com.taller.poo;

public class PruebaEstudiante {

    public static void main(String[] args) {
        Estudiante estudiante1 = new Estudiante("E-100", "Sofía Herrera", 4.10, true);

        String reporte = estudiante1.obtenerReporteAcademicos();
        System.out.println(reporte);
    }
}
