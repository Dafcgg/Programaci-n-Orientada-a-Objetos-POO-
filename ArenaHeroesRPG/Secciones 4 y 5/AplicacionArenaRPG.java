import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AplicacionArenaRPG {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        List<Personaje> personajes = new ArrayList<>();
        List<Gremio> gremios = new ArrayList<>();
        List<Escuadron> escuadrones = new ArrayList<>();

        do {
            System.out.println("\n=== MENÚ ARENA DE HÉROES RPG ===");
            System.out.println("1. Crear Personaje");
            System.out.println("2. Crear Gremio");
            System.out.println("3. Reclutar Personaje a Escuadrón");
            System.out.println("4. Equipar Arma a Personaje");
            System.out.println("5. Mostrar Estado Detallado (Personaje y Corazón RPG)");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero(scanner);

            switch (opcion) {
                case 1:
                    crearPersonaje(scanner, personajes);
                    break;
                case 2:
                    crearGremio(scanner, gremios, personajes);
                    break;
                case 3:
                    reclutarEnEscuadron(scanner, escuadrones, personajes);
                    break;
                case 4:
                    equiparArma(scanner, personajes);
                    break;
                case 5:
                    mostrarEstado(personajes, gremios, escuadrones);
                    break;
                case 6:
                    System.out.println("Cerrando la Arena de Héroes...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 6);

        scanner.close();
    }

    private static int leerEntero(Scanner scanner) {
        String linea = scanner.hasNextLine() ? scanner.nextLine().trim() : "6";
        try {
            return Integer.parseInt(linea);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static String leerTexto(Scanner scanner, String mensaje) {
        String texto = "";
        while (texto.isEmpty()) {
            System.out.print(mensaje);
            if (!scanner.hasNextLine()) {
                return "Sin nombre";
            }
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("El valor no puede estar vacío.");
            }
        }
        return texto;
    }

    private static int leerEnteroPositivo(Scanner scanner, String mensaje) {
        int valor = -1;
        while (valor < 0) {
            System.out.print(mensaje);
            if (!scanner.hasNextLine()) {
                return 0;
            }
            valor = leerEntero(scanner);
            if (valor < 0) {
                System.out.println("Ingrese un número entero válido mayor o igual a 0.");
            }
        }
        return valor;
    }

    private static void listarPersonajes(List<Personaje> personajes) {
        System.out.println("--- Personajes disponibles ---");
        for (int i = 0; i < personajes.size(); i++) {
            System.out.printf("%d. %s (Nivel %d)%n", i + 1, personajes.get(i).getNombre(), personajes.get(i).getNivel());
        }
    }

    private static Personaje seleccionarPersonaje(Scanner scanner, List<Personaje> personajes) {
        if (personajes.isEmpty()) {
            System.out.println("No hay personajes creados. Cree uno primero con la opción 1.");
            return null;
        }
        listarPersonajes(personajes);
        System.out.print("Seleccione el número del personaje: ");
        int indice = leerEntero(scanner);
        if (indice < 1 || indice > personajes.size()) {
            System.out.println("Selección de personaje inválida.");
            return null;
        }
        return personajes.get(indice - 1);
    }

    private static void crearPersonaje(Scanner scanner, List<Personaje> personajes) {
        String nombre = leerTexto(scanner, "Nombre del personaje: ");
        int nivel = leerEnteroPositivo(scanner, "Nivel del personaje: ");
        Personaje personaje = new Personaje(nombre, nivel);
        personajes.add(personaje);
        System.out.printf("Personaje '%s' creado con nivel %d.%n", personaje.getNombre(), personaje.getNivel());
    }

    private static void crearGremio(Scanner scanner, List<Gremio> gremios, List<Personaje> personajes) {
        String nombre = leerTexto(scanner, "Nombre del gremio: ");
        Gremio gremio = new Gremio(nombre);
        gremios.add(gremio);
        System.out.printf("Gremio '%s' creado.%n", gremio.getNombre());

        if (personajes.isEmpty()) {
            return;
        }
        System.out.print("¿Desea inscribir un personaje en este gremio? (s/n): ");
        String respuesta = scanner.hasNextLine() ? scanner.nextLine().trim() : "n";
        if (respuesta.equalsIgnoreCase("s")) {
            Personaje personaje = seleccionarPersonaje(scanner, personajes);
            if (personaje != null) {
                personaje.unirseAGremio(gremio);
                System.out.printf("%s se unió al gremio '%s'.%n", personaje.getNombre(), gremio.getNombre());
            }
        }
    }

    private static void reclutarEnEscuadron(Scanner scanner, List<Escuadron> escuadrones, List<Personaje> personajes) {
        Personaje personaje = seleccionarPersonaje(scanner, personajes);
        if (personaje == null) {
            return;
        }

        Escuadron escuadron = null;
        if (!escuadrones.isEmpty()) {
            System.out.println("--- Escuadrones disponibles ---");
            for (int i = 0; i < escuadrones.size(); i++) {
                System.out.printf("%d. %s (%d miembros)%n", i + 1, escuadrones.get(i).getNombre(), escuadrones.get(i).getMiembros().size());
            }
            System.out.print("Seleccione el número del escuadrón (0 para crear uno nuevo): ");
            int indice = leerEntero(scanner);
            if (indice >= 1 && indice <= escuadrones.size()) {
                escuadron = escuadrones.get(indice - 1);
            } else if (indice != 0) {
                System.out.println("Selección de escuadrón inválida.");
                return;
            }
        }

        if (escuadron == null) {
            String nombre = leerTexto(scanner, "Nombre del nuevo escuadrón: ");
            escuadron = new Escuadron(nombre);
            escuadrones.add(escuadron);
            System.out.printf("Escuadrón '%s' creado.%n", escuadron.getNombre());
        }

        if (escuadron.reclutarPersonaje(personaje)) {
            System.out.printf("%s fue reclutado en el escuadrón '%s'.%n", personaje.getNombre(), escuadron.getNombre());
        } else {
            System.out.printf("%s ya pertenece al escuadrón '%s'.%n", personaje.getNombre(), escuadron.getNombre());
        }
    }

    private static void equiparArma(Scanner scanner, List<Personaje> personajes) {
        Personaje personaje = seleccionarPersonaje(scanner, personajes);
        if (personaje == null) {
            return;
        }
        String nombreArma = leerTexto(scanner, "Nombre del arma: ");
        int danio = leerEnteroPositivo(scanner, "Daño base del arma: ");
        personaje.equiparArma(new Arma(nombreArma, danio));
    }

    private static void mostrarEstado(List<Personaje> personajes, List<Gremio> gremios, List<Escuadron> escuadrones) {
        if (personajes.isEmpty()) {
            System.out.println("No hay personajes registrados.");
            return;
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("\n========== ESTADO DETALLADO DE LA ARENA ==========\n");
        System.out.print(reporte);

        System.out.printf("%-4s %-15s %-6s %-8s %-8s %-15s %-8s%n", "#", "Nombre", "Nivel", "Salud", "Maná", "Arma", "Ataque");
        System.out.println("-------------------------------------------------------------------------");
        for (int i = 0; i < personajes.size(); i++) {
            Personaje p = personajes.get(i);
            String arma = p.getArmaEquipada() != null ? p.getArmaEquipada().getNombre() : "Desarmado";
            System.out.printf("%-4d %-15s %-6d %-8d %-8d %-15s %-8d%n",
                    i + 1,
                    p.getNombre(),
                    p.getNivel(),
                    p.getCorazon().getSaludBase(),
                    p.getCorazon().getManaBase(),
                    arma,
                    p.calcularAtaqueTotal());
        }

        System.out.println("\n--- Detalle por personaje ---");
        for (Personaje p : personajes) {
            System.out.println(p);
        }

        System.out.println("\n--- Gremios ---");
        if (gremios.isEmpty()) {
            System.out.println("No hay gremios registrados.");
        }
        for (Gremio g : gremios) {
            System.out.printf("%-20s miembros: %d%n", g.getNombre(), g.getMiembros().size());
            System.out.println("  " + g);
        }

        System.out.println("\n--- Escuadrones ---");
        if (escuadrones.isEmpty()) {
            System.out.println("No hay escuadrones registrados.");
        }
        for (Escuadron e : escuadrones) {
            System.out.printf("%-20s miembros: %d%n", e.getNombre(), e.getMiembros().size());
            System.out.println("  " + e);
        }
    }
}
