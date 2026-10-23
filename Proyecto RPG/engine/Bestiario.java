package engine;

import java.util.HashMap;
import java.util.Map;

public class Bestiario {

    private final Map<String, Integer> registroEnemigos = new HashMap<String, Integer>();

    public void registrarVictoria(String nombreEnemigo) {
        if (nombreEnemigo == null || nombreEnemigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del enemigo no puede estar vacio");
        }
        String clave = nombreEnemigo.trim();
        if (!registroEnemigos.containsKey(clave)) {
            registroEnemigos.put(clave, 0);
        }
        registroEnemigos.put(clave, registroEnemigos.getOrDefault(clave, 0) + 1);
    }

    public int obtenerDerrotas(String nombreEnemigo) {
        if (nombreEnemigo == null) {
            return 0;
        }
        return registroEnemigos.getOrDefault(nombreEnemigo.trim(), 0);
    }

    public Map<String, Integer> obtenerRegistroCompleto() {
        return registroEnemigos;
    }
}
