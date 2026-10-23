package model;

import java.util.Set;
import java.util.TreeSet;

public class PerfilJugador {

    private final String nombreJugador;
    private final Set<String> logros = new TreeSet<String>();

    public PerfilJugador(String nombreJugador) {
        if (nombreJugador == null || nombreJugador.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del jugador no puede estar vacio");
        }
        this.nombreJugador = nombreJugador.trim();
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public boolean registrarLogro(String logro) {
        if (logro == null || logro.trim().isEmpty()) {
            throw new IllegalArgumentException("El logro no puede estar vacio");
        }
        return logros.add(logro.trim());
    }

    public Set<String> listarLogrosOrdenados() {
        return logros;
    }
}
