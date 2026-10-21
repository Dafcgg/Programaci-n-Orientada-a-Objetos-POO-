public class Pocion {

    private final String nombre;
    private final int curacion;

    public Pocion(String nombre, int curacion) {
        this.nombre = (nombre == null || nombre.trim().isEmpty()) ? "Pocion" : nombre.trim();
        this.curacion = Math.max(curacion, 0);
    }

    public String getNombre() {
        return nombre;
    }

    public int getCuracion() {
        return curacion;
    }
}
