import java.util.ArrayList;
import java.util.List;

public class Escuadron {
    private String nombre;
    private List<Personaje> miembros;

    public Escuadron(String nombre) {
        this.nombre = nombre;
        this.miembros = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Personaje> getMiembros() {
        return new ArrayList<>(miembros);
    }

    public boolean reclutarPersonaje(Personaje p) {
        if (p == null || miembros.contains(p)) {
            return false;
        }
        miembros.add(p);
        return true;
    }

    public boolean expulsarPersonaje(Personaje p) {
        if (p == null) {
            return false;
        }
        return miembros.remove(p);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Escuadron[nombre=").append(nombre);
        sb.append(", totalMiembros=").append(miembros.size());
        sb.append(", miembros=[");
        for (int i = 0; i < miembros.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(miembros.get(i).getNombre());
        }
        sb.append("]]");
        return sb.toString();
    }
}
