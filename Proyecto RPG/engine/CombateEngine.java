package engine;

import model.PerfilJugador;

import java.util.Map;
import java.util.TreeMap;

public class CombateEngine {

    private final Map<String, Double> catalogoTienda = new TreeMap<String, Double>();
    private final Bestiario bestiario;
    private final PerfilJugador perfilJugador;

    public CombateEngine(Bestiario bestiario, PerfilJugador perfilJugador) {
        if (bestiario == null || perfilJugador == null) {
            throw new IllegalArgumentException("El bestiario y el perfil del jugador son obligatorios");
        }
        this.bestiario = bestiario;
        this.perfilJugador = perfilJugador;
    }

    public boolean registrarVictoria(String nombreEnemigo) {
        if (nombreEnemigo == null || nombreEnemigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del enemigo no puede estar vacio");
        }
        String enemigo = nombreEnemigo.trim();
        boolean primeraVez = bestiario.obtenerDerrotas(enemigo) == 0;
        bestiario.registrarVictoria(enemigo);
        if (primeraVez) {
            perfilJugador.registrarLogro("Cazador de " + enemigo);
        }
        return primeraVez;
    }

    public void agregarAlCatalogo(String nombreItem, double precio) {
        if (nombreItem == null || nombreItem.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del item no puede estar vacio");
        }
        if (Double.isNaN(precio) || precio < 0) {
            throw new IllegalArgumentException("El precio debe ser un numero igual o mayor que cero");
        }
        catalogoTienda.put(nombreItem.trim(), precio);
    }

    public String listarCatalogo() {
        StringBuilder reporte = new StringBuilder();
        for (Map.Entry<String, Double> entrada : catalogoTienda.entrySet()) {
            reporte.append(entrada.getKey()).append(" -> ").append(entrada.getValue()).append(System.lineSeparator());
        }
        return reporte.toString();
    }

    public Map<String, Double> obtenerCatalogoTienda() {
        return catalogoTienda;
    }

    public Bestiario getBestiario() {
        return bestiario;
    }

    public PerfilJugador getPerfilJugador() {
        return perfilJugador;
    }
}
