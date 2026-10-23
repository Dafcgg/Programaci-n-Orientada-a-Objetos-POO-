import engine.Bestiario;
import engine.CombateEngine;
import exception.CapacidadExcedidaException;
import exception.SinManaException;
import exception.VidaInsuficienteException;
import model.PerfilJugador;
import model.Personaje;
import model.enums.TipoPersonaje;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    private static final int OPCION_SALIR = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);
        Bestiario bestiario = new Bestiario();
        PerfilJugador perfilJugador = new PerfilJugador("Jugador 1");
        CombateEngine motor = new CombateEngine(bestiario, perfilJugador);
        Personaje personaje = null;
        int opcion = -1;

        try {
            do {
                mostrarMenu();
                opcion = leerEntero(scanner, "Seleccione una opcion: ");
                try {
                    switch (opcion) {
                        case 1:
                            personaje = crearPersonaje(scanner);
                            break;
                        case 2:
                            asignarHabilidad(scanner, personaje);
                            break;
                        case 3:
                            registrarVictoria(scanner, motor);
                            break;
                        case 4:
                            ejecutarAtaqueEspecial(scanner, personaje);
                            break;
                        case 5:
                            consultarBestiarioYLogros(motor);
                            break;
                        case OPCION_SALIR:
                            System.out.println("Hasta pronto, aventurero.");
                            break;
                        default:
                            System.err.println("Opcion fuera de rango: elija un numero entre 0 y 5.");
                            break;
                    }
                } catch (SinManaException e) {
                    System.err.println("No se pudo ejecutar el ataque: " + e.getMessage());
                } catch (CapacidadExcedidaException e) {
                    System.err.println("No se pudo aprender la habilidad: " + e.getMessage());
                } catch (VidaInsuficienteException e) {
                    System.err.println("Accion cancelada: " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.err.println("Dato invalido: " + e.getMessage());
                }
            } while (opcion != OPCION_SALIR);
        } catch (NoSuchElementException e) {
            System.err.println("Se cerro la entrada de datos. Finalizando el programa.");
        } finally {
            scanner.close();
        }
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== ARENA DE HEROES =====");
        System.out.println("1. Crear Personaje");
        System.out.println("2. Asignar Habilidad Unica");
        System.out.println("3. Registrar Victoria en Bestiario");
        System.out.println("4. Ejecutar Ataque Especial");
        System.out.println("5. Consultar Bestiario y Logros");
        System.out.println("0. Salir");
    }

    private static Personaje crearPersonaje(Scanner scanner) {
        String nombre = leerTextoNoVacio(scanner, "Nombre del personaje: ");
        TipoPersonaje[] tipos = TipoPersonaje.values();
        TipoPersonaje tipoElegido = null;
        while (tipoElegido == null) {
            System.out.println("Tipos disponibles:");
            for (int i = 0; i < tipos.length; i++) {
                System.out.println((i + 1) + ". " + tipos[i]);
            }
            int indice = leerEntero(scanner, "Seleccione el tipo: ");
            if (indice >= 1 && indice <= tipos.length) {
                tipoElegido = tipos[indice - 1];
            } else {
                System.err.println("Tipo fuera de rango: elija un numero entre 1 y " + tipos.length + ".");
            }
        }
        Personaje nuevo = new Personaje(nombre, tipoElegido);
        System.out.println("Personaje creado: " + nuevo.getNombre() + " (" + nuevo.getTipoPersonaje() + ")"
                + " | Estado: " + nuevo.getEstado()
                + " | Vida: " + nuevo.getVidaActual() + "/" + nuevo.getVidaMaxima()
                + " | Mana: " + nuevo.getManaActual() + "/" + nuevo.getManaMaximo());
        return nuevo;
    }

    private static void asignarHabilidad(Scanner scanner, Personaje personaje) throws CapacidadExcedidaException {
        if (personaje == null) {
            System.err.println("Primero debe crear un personaje (opcion 1).");
            return;
        }
        String habilidad = leerTextoNoVacio(scanner, "Nombre de la habilidad: ");
        if (personaje.aprenderHabilidad(habilidad)) {
            System.out.println("Habilidad aprendida: " + habilidad.trim());
        } else {
            System.err.println("Advertencia: la habilidad '" + habilidad.trim() + "' ya esta aprendida.");
        }
    }

    private static void registrarVictoria(Scanner scanner, CombateEngine motor) {
        String enemigo = leerTextoNoVacio(scanner, "Nombre del enemigo derrotado: ");
        boolean primeraVez = motor.registrarVictoria(enemigo);
        System.out.println("Victoria registrada contra " + enemigo.trim() + ". Total: "
                + motor.getBestiario().obtenerDerrotas(enemigo));
        if (primeraVez) {
            System.out.println("Nuevo logro desbloqueado: Cazador de " + enemigo.trim());
        }
    }

    private static void ejecutarAtaqueEspecial(Scanner scanner, Personaje personaje) throws SinManaException {
        if (personaje == null) {
            System.err.println("Primero debe crear un personaje (opcion 1).");
            return;
        }
        double costo = leerDecimal(scanner, "Costo de mana del ataque (use punto decimal): ");
        personaje.ejecutarAtaqueEspecial(costo);
        System.out.println("Ataque especial ejecutado. Mana restante: " + personaje.getManaActual()
                + "/" + personaje.getManaMaximo());
    }

    private static void consultarBestiarioYLogros(CombateEngine motor) {
        System.out.println("--- Bestiario ---");
        Map<String, Integer> registro = motor.getBestiario().obtenerRegistroCompleto();
        if (registro.isEmpty()) {
            System.out.println("Aun no hay enemigos derrotados.");
        } else {
            for (Map.Entry<String, Integer> entrada : registro.entrySet()) {
                System.out.println(entrada.getKey() + ": " + entrada.getValue() + " derrota(s)");
            }
        }
        System.out.println("--- Logros (orden alfabetico) ---");
        if (motor.getPerfilJugador().listarLogrosOrdenados().isEmpty()) {
            System.out.println("Aun no hay logros desbloqueados.");
        } else {
            for (String logro : motor.getPerfilJugador().listarLogrosOrdenados()) {
                System.out.println("- " + logro);
            }
        }
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Entrada invalida: ingrese un numero entero.");
            } finally {
                limpiarBufer(scanner);
            }
        }
    }

    private static double leerDecimal(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.err.println("Entrada invalida: ingrese un numero valido (ejemplo 12.5).");
            } finally {
                limpiarBufer(scanner);
            }
        }
    }

    private static void limpiarBufer(Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    private static String leerTextoNoVacio(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine();
            if (texto != null && !texto.trim().isEmpty()) {
                return texto.trim();
            }
            System.err.println("Entrada invalida: el texto no puede estar vacio.");
        }
    }
}
