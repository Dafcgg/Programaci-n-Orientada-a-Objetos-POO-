package co.edu.puj.rpg.principal;

import co.edu.puj.rpg.modelo.Personaje;
import co.edu.puj.rpg.modelo.enemigo.Enemigo;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Personaje heroe = new Personaje("Aragorn", 100, 15, 50.0);
        Enemigo enemigo = new Enemigo("Orco Bruto", 80, 12, 2, 40.0, 60);
        StringBuilder historial = new StringBuilder();

        int pocionesPequenas = 2;
        int pocionesGrandes = 0;
        int curacionPequena = 30;
        int curacionGrande = 60;
        int turno = 0;
        boolean huyo = false;

        historial.append(String.format("Comienza la batalla: %s vs %s%n", heroe.getNombre(), enemigo.getNombre()));

        do {
            turno++;
            System.out.printf("%n=== TURNO %d ===%n", turno);
            System.out.printf("%s (Nv %d): %d/%d HP%n", heroe.getNombre(), heroe.getNivel(), heroe.getPuntosVida(), heroe.getVidaMaxima());
            System.out.printf("%s (Nv %d): %d/%d HP%n", enemigo.getNombre(), enemigo.getNivel(), enemigo.getPuntosVida(), enemigo.getVidaMaxima());
            System.out.println("1. Atacar");
            System.out.println("2. Usar Pocion");
            System.out.println("3. Ver Estado");
            System.out.println("4. Huir");
            System.out.println("5. Tienda");
            System.out.print("Elige una opcion: ");

            int opcion;
            if (teclado.hasNextInt()) {
                opcion = teclado.nextInt();
            } else if (teclado.hasNext()) {
                teclado.next();
                opcion = -1;
            } else {
                opcion = 4;
            }

            boolean consumeTurno = false;

            if (opcion < 1 || opcion > 5) {
                System.out.println("Opcion invalida. Elige un numero entre 1 y 5.");
                historial.append(String.format("Turno %d: opcion invalida, no se hizo nada.%n", turno));
            } else {
                switch (opcion) {
                    case 1:
                        int vidaEnemigoAntes = enemigo.getPuntosVida();
                        heroe.atacar(enemigo);
                        int danoInfligido = vidaEnemigoAntes - enemigo.getPuntosVida();
                        String tipoGolpe = heroe.isUltimoGolpeCritico() ? "GOLPE CRITICO" : "golpe normal";
                        historial.append(String.format("Turno %d: %s inflige %d de dano a %s (%s).%n", turno, heroe.getNombre(), danoInfligido, enemigo.getNombre(), tipoGolpe));
                        consumeTurno = true;
                        break;
                    case 2:
                        int vidaAntes = heroe.getPuntosVida();
                        if (vidaAntes == heroe.getVidaMaxima()) {
                            System.out.println("Tu vida ya esta completa. No gastas la pocion.");
                            historial.append(String.format("Turno %d: pocion cancelada, vida completa.%n", turno));
                        } else if (pocionesGrandes > 0) {
                            heroe.usarPocion(curacionGrande);
                            pocionesGrandes--;
                            historial.append(String.format("Turno %d: %s usa una Pocion Grande y recupera %d HP.%n", turno, heroe.getNombre(), heroe.getPuntosVida() - vidaAntes));
                            consumeTurno = true;
                        } else if (pocionesPequenas > 0) {
                            heroe.usarPocion(curacionPequena);
                            pocionesPequenas--;
                            historial.append(String.format("Turno %d: %s usa una Pocion y recupera %d HP.%n", turno, heroe.getNombre(), heroe.getPuntosVida() - vidaAntes));
                            consumeTurno = true;
                        } else {
                            System.out.println("No tienes pociones disponibles.");
                            historial.append(String.format("Turno %d: intento de usar pocion sin inventario.%n", turno));
                        }
                        break;
                    case 3:
                        System.out.println("--- ESTADO DEL HEROE ---");
                        System.out.printf("Nombre: %s%n", heroe.getNombre());
                        System.out.printf("Vida: %d/%d%n", heroe.getPuntosVida(), heroe.getVidaMaxima());
                        System.out.printf("Ataque: %d%n", heroe.getPuntosAtaque());
                        System.out.printf("Nivel: %d%n", heroe.getNivel());
                        System.out.printf("Experiencia: %d/%d%n", heroe.getExperiencia(), heroe.getUmbralExperiencia());
                        System.out.printf("Oro: %.2f%n", heroe.getOro());
                        System.out.printf("Pociones: %d normales, %d grandes%n", pocionesPequenas, pocionesGrandes);
                        System.out.printf("Vivo: %s%n", heroe.isEstaVivo() ? "Si" : "No");
                        historial.append(String.format("Turno %d: se consulto el estado.%n", turno));
                        break;
                    case 4:
                        huyo = true;
                        historial.append(String.format("Turno %d: %s huye del combate.%n", turno, heroe.getNombre()));
                        break;
                    case 5:
                        int compradas = abrirTienda(teclado, heroe, historial, turno);
                        pocionesGrandes += compradas;
                        break;
                    default:
                        break;
                }
            }

            if (consumeTurno && enemigo.isEstaVivo()) {
                int vidaHeroeAntes = heroe.getPuntosVida();
                enemigo.atacar(heroe);
                int danoRecibido = vidaHeroeAntes - heroe.getPuntosVida();
                historial.append(String.format("Turno %d: %s inflige %d de dano a %s.%n", turno, enemigo.getNombre(), danoRecibido, heroe.getNombre()));
                if (!heroe.isEstaVivo()) {
                    historial.append(String.format("%s ha caido en combate.%n", heroe.getNombre()));
                }
            }

            if (!enemigo.isEstaVivo()) {
                historial.append(String.format("%s ha sido derrotado.%n", enemigo.getNombre()));
            }

            System.out.println("--- HISTORIAL DE BATALLA ---");
            System.out.print(historial);

        } while (heroe.isEstaVivo() && enemigo.isEstaVivo() && !huyo);

        System.out.println();
        if (huyo) {
            System.out.printf("%s escapa de la arena con %d HP.%n", heroe.getNombre(), heroe.getPuntosVida());
        } else if (!enemigo.isEstaVivo()) {
            heroe.setOro(heroe.getOro() + enemigo.getOro());
            boolean subioNivel = heroe.ganarExperiencia(enemigo.getExperienciaRecompensa());
            System.out.printf("VICTORIA! %s gana %.2f de oro y %d de experiencia.%n", heroe.getNombre(), enemigo.getOro(), enemigo.getExperienciaRecompensa());
            if (subioNivel) {
                System.out.printf("%s sube al nivel %d!%n", heroe.getNombre(), heroe.getNivel());
            }
            System.out.printf("Oro total: %.2f | Experiencia: %d/%d%n", heroe.getOro(), heroe.getExperiencia(), heroe.getUmbralExperiencia());
        } else {
            System.out.printf("DERROTA. %s ha sido vencido por %s.%n", heroe.getNombre(), enemigo.getNombre());
        }

        teclado.close();
    }

    private static int abrirTienda(Scanner teclado, Personaje heroe, StringBuilder historial, int turno) {
        double costoPocionGrande = 30.0;
        int compradas = 0;
        int opcionTienda;

        do {
            System.out.println("--- TIENDA ---");
            System.out.printf("Oro disponible: %.2f%n", heroe.getOro());
            System.out.printf("1. Pocion Grande de Salud (%.2f de oro)%n", costoPocionGrande);
            System.out.println("2. Volver al combate");
            System.out.print("Elige una opcion: ");

            if (teclado.hasNextInt()) {
                opcionTienda = teclado.nextInt();
            } else if (teclado.hasNext()) {
                teclado.next();
                opcionTienda = -1;
            } else {
                opcionTienda = 2;
            }

            if (opcionTienda == 1) {
                double saldoAntes = heroe.getOro();
                double saldoNuevo = saldoAntes - costoPocionGrande;
                heroe.setOro(saldoNuevo);
                if (heroe.getOro() != saldoAntes) {
                    compradas++;
                    System.out.printf("Compraste una Pocion Grande de Salud. Oro restante: %.2f%n", heroe.getOro());
                    historial.append(String.format("Turno %d: %s compra una Pocion Grande de Salud.%n", turno, heroe.getNombre()));
                } else {
                    System.out.println("Compra rechazada: no tienes oro suficiente.");
                    historial.append(String.format("Turno %d: compra rechazada por saldo insuficiente.%n", turno));
                }
            } else if (opcionTienda != 2) {
                System.out.println("Opcion invalida en la tienda.");
            }
        } while (opcionTienda != 2);

        return compradas;
    }
}
