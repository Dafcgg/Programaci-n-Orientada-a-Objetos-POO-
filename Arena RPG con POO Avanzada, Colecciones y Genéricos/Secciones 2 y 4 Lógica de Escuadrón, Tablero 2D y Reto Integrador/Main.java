import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final int TAMANO = 5;
    private static final int COFRE_X = 2;
    private static final int COFRE_Y = 2;
    private static final char CELDA_VACIA = '.';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random azar = new Random();
        char[][] tablero = new char[TAMANO][TAMANO];

        List<Personaje> escuadron = new ArrayList<>();
        escuadron.add(new Guerrero("Thorin", 120, 15, 0, 0, 10));
        escuadron.add(new Mago("Gandalf", 80, 25, 1, 1, 50));

        List<Personaje> enemigos = new ArrayList<>();
        enemigos.add(new Guerrero("Orco", 50, 8, 4, 0, 6));
        enemigos.add(new Guerrero("Goblin", 40, 7, 0, 4, 2));
        enemigos.add(new Mago("Hechicero", 35, 10, 4, 4, 30));

        Inventario<Pocion> inventario = new Inventario<>();
        inventario.agregar(new Pocion("Pocion menor", 30));
        inventario.agregar(new Pocion("Pocion menor", 30));
        inventario.agregar(new Pocion("Pocion mayor", 50));

        Personaje principal = escuadron.get(0);
        boolean cofreAlcanzado = false;
        int ronda = 0;

        System.out.println("=========== ARENA RPG ===========");
        System.out.println("Elimina a todos los enemigos o lleva a " + principal.getNombre() + " al cofre [C].");

        do {
            ronda++;
            System.out.println();
            System.out.println("================ RONDA " + ronda + " ================");
            actualizarTablero(tablero, escuadron, enemigos);
            mostrarTablero(tablero);
            mostrarEstado(escuadron, enemigos, inventario);

            cofreAlcanzado = turnoJugador(scanner, escuadron, enemigos, inventario, principal, tablero);
            enemigos.removeIf(e -> !e.estaVivo());

            if (!cofreAlcanzado && !enemigos.isEmpty()) {
                turnoEnemigo(escuadron, enemigos, azar);
                escuadron.removeIf(h -> !h.estaVivo());
            }
        } while (!escuadronDestruido(escuadron) && !enemigos.isEmpty() && !cofreAlcanzado);

        System.out.println();
        actualizarTablero(tablero, escuadron, enemigos);
        mostrarTablero(tablero);

        if (cofreAlcanzado) {
            System.out.println("VICTORIA: " + principal.getNombre() + " ha alcanzado el cofre [C].");
        } else if (enemigos.isEmpty()) {
            System.out.println("VICTORIA: todos los enemigos han sido eliminados.");
        } else {
            System.out.println("DERROTA: el escuadron heroe ha sido destruido.");
        }

        scanner.close();
    }

    private static boolean turnoJugador(Scanner scanner, List<Personaje> escuadron, List<Personaje> enemigos,
                                        Inventario<Pocion> inventario, Personaje principal, char[][] tablero) {
        System.out.println();
        System.out.println("--- Turno de los heroes ---");
        for (Personaje heroe : escuadron) {
            if (!heroe.estaVivo()) {
                continue;
            }
            if (enemigos.isEmpty()) {
                return false;
            }
            boolean accionRealizada = false;
            while (!accionRealizada) {
                System.out.println();
                System.out.println("Turno de " + heroe.getNombre() + " (Vida: " + heroe.getVida() + "/" + heroe.getVidaMaxima() + ")");
                System.out.println("1. Atacar");
                System.out.println("2. Usar inventario");
                System.out.println("3. Mover en tablero");
                int opcion = leerEntero(scanner, 1, 3);
                switch (opcion) {
                    case 1:
                        accionRealizada = atacar(scanner, heroe, enemigos);
                        break;
                    case 2:
                        accionRealizada = usarInventario(scanner, heroe, inventario);
                        break;
                    case 3:
                        accionRealizada = mover(scanner, heroe, escuadron, enemigos);
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                        break;
                }
            }
            enemigos.removeIf(e -> !e.estaVivo());
            System.out.println();
            actualizarTablero(tablero, escuadron, enemigos);
            mostrarTablero(tablero);
            if (heroe == principal && heroe.getPosicionX() == COFRE_X && heroe.getPosicionY() == COFRE_Y) {
                return true;
            }
        }
        return false;
    }

    private static boolean atacar(Scanner scanner, Personaje heroe, List<Personaje> enemigos) {
        if (enemigos.isEmpty()) {
            System.out.println("No quedan enemigos.");
            return false;
        }
        System.out.println("Elige un enemigo:");
        for (int i = 0; i < enemigos.size(); i++) {
            Personaje enemigo = enemigos.get(i);
            System.out.println((i + 1) + ". " + enemigo.getNombre() + " (Vida: " + enemigo.getVida() + ")");
        }
        int indice = leerEntero(scanner, 1, enemigos.size());
        Personaje objetivo = enemigos.get(indice - 1);
        heroe.realizarAtaqueEspecial(objetivo);
        return true;
    }

    private static boolean usarInventario(Scanner scanner, Personaje heroe, Inventario<Pocion> inventario) {
        if (inventario.isEmpty()) {
            System.out.println("El inventario esta vacio.");
            return false;
        }
        if (heroe.getVida() >= heroe.getVidaMaxima()) {
            System.out.println(heroe.getNombre() + " ya tiene la vida completa.");
            return false;
        }
        System.out.println("Inventario:");
        for (int i = 0; i < inventario.size(); i++) {
            Pocion pocion = inventario.obtener(i);
            if (pocion != null) {
                System.out.println((i + 1) + ". " + pocion.getNombre() + " (+" + pocion.getCuracion() + " vida)");
            }
        }
        System.out.println("0. Cancelar");
        int opcion = leerEntero(scanner, 0, inventario.size());
        if (opcion == 0) {
            return false;
        }
        Pocion elegida = inventario.obtener(opcion - 1);
        if (elegida == null) {
            return false;
        }
        int vidaAnterior = heroe.getVida();
        heroe.curar(elegida.getCuracion());
        inventario.remover(opcion - 1);
        System.out.println(heroe.getNombre() + " usa " + elegida.getNombre() + " y recupera "
                + (heroe.getVida() - vidaAnterior) + " de vida. Vida actual: " + heroe.getVida());
        return true;
    }

    private static boolean mover(Scanner scanner, Personaje heroe, List<Personaje> escuadron, List<Personaje> enemigos) {
        System.out.println("Direccion: 1. Arriba  2. Abajo  3. Izquierda  4. Derecha  0. Cancelar");
        int direccion = leerEntero(scanner, 0, 4);
        if (direccion == 0) {
            return false;
        }
        int nuevoX = heroe.getPosicionX();
        int nuevoY = heroe.getPosicionY();
        switch (direccion) {
            case 1:
                nuevoY--;
                break;
            case 2:
                nuevoY++;
                break;
            case 3:
                nuevoX--;
                break;
            case 4:
                nuevoX++;
                break;
            default:
                break;
        }
        if (nuevoX < 0 || nuevoX >= TAMANO || nuevoY < 0 || nuevoY >= TAMANO) {
            System.out.println("Movimiento fuera del tablero. Elige otra accion.");
            return false;
        }
        if (hayUnidad(escuadron, nuevoX, nuevoY, heroe) || hayUnidad(enemigos, nuevoX, nuevoY, heroe)) {
            System.out.println("La celda (" + nuevoX + ", " + nuevoY + ") esta ocupada. Elige otra accion.");
            return false;
        }
        heroe.setPosicionX(nuevoX);
        heroe.setPosicionY(nuevoY);
        System.out.println(heroe.getNombre() + " se mueve a la celda (" + nuevoX + ", " + nuevoY + ").");
        return true;
    }

    private static void turnoEnemigo(List<Personaje> escuadron, List<Personaje> enemigos, Random azar) {
        System.out.println();
        System.out.println("--- Turno de los enemigos ---");
        for (Personaje enemigo : enemigos) {
            if (!enemigo.estaVivo()) {
                continue;
            }
            List<Personaje> vivos = new ArrayList<>(escuadron);
            vivos.removeIf(h -> !h.estaVivo());
            if (vivos.isEmpty()) {
                break;
            }
            Personaje objetivo = vivos.get(azar.nextInt(vivos.size()));
            enemigo.realizarAtaqueEspecial(objetivo);
        }
    }

    private static boolean hayUnidad(List<Personaje> unidades, int x, int y, Personaje ignorada) {
        for (Personaje unidad : unidades) {
            if (unidad != ignorada && unidad.estaVivo() && unidad.getPosicionX() == x && unidad.getPosicionY() == y) {
                return true;
            }
        }
        return false;
    }

    private static boolean escuadronDestruido(List<Personaje> escuadron) {
        if (escuadron.isEmpty()) {
            return true;
        }
        int vidaTotal = 0;
        for (Personaje heroe : escuadron) {
            vidaTotal += heroe.getVida();
        }
        return vidaTotal <= 0;
    }

    private static void actualizarTablero(char[][] tablero, List<Personaje> escuadron, List<Personaje> enemigos) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = CELDA_VACIA;
            }
        }
        tablero[COFRE_Y][COFRE_X] = 'C';
        for (Personaje enemigo : enemigos) {
            colocar(tablero, enemigo, 'E');
        }
        for (Personaje heroe : escuadron) {
            colocar(tablero, heroe, heroe instanceof Guerrero ? 'G' : 'M');
        }
    }

    private static void colocar(char[][] tablero, Personaje unidad, char simbolo) {
        if (!unidad.estaVivo()) {
            return;
        }
        int x = unidad.getPosicionX();
        int y = unidad.getPosicionY();
        if (y >= 0 && y < tablero.length && x >= 0 && x < tablero[y].length) {
            tablero[y][x] = simbolo;
        }
    }

    public static void mostrarTablero(char[][] matriz) {
        System.out.println("Tablero (G Guerrero, M Mago, E Enemigo, C Cofre):");
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print("[" + matriz[i][j] + "]");
            }
            System.out.println();
        }
    }

    private static void mostrarEstado(List<Personaje> escuadron, List<Personaje> enemigos, Inventario<Pocion> inventario) {
        System.out.println();
        System.out.println("Heroes:");
        for (Personaje heroe : escuadron) {
            System.out.println("  " + describir(heroe));
        }
        System.out.println("Enemigos:");
        for (Personaje enemigo : enemigos) {
            System.out.println("  " + describir(enemigo));
        }
        System.out.println("Pociones disponibles: " + inventario.size());
    }

    private static String describir(Personaje personaje) {
        String texto = personaje.getNombre() + " - Vida: " + personaje.getVida() + "/" + personaje.getVidaMaxima()
                + " - Posicion: (" + personaje.getPosicionX() + ", " + personaje.getPosicionY() + ")";
        if (personaje instanceof Mago) {
            Mago mago = (Mago) personaje;
            texto += " - Mana: " + mago.getPuntosMana();
        } else if (personaje instanceof Guerrero) {
            Guerrero guerrero = (Guerrero) personaje;
            texto += " - Escudo: " + guerrero.getFuerzaEscudo();
        }
        return texto;
    }

    private static int leerEntero(Scanner scanner, int minimo, int maximo) {
        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                System.out.println();
                System.out.println("Entrada finalizada. Fin del programa.");
                System.exit(0);
            }
            String linea = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(linea);
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.print("");
            }
            System.out.println("Ingresa un numero entre " + minimo + " y " + maximo + ".");
        }
    }
}
