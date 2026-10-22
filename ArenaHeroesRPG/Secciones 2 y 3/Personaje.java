public class Personaje {
    private String nombre;
    private int nivel;
    private Arma armaEquipada;
    private Gremio gremio;
    private final CorazonRPG corazon;

    public Personaje(String nombre, int nivel) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.armaEquipada = null;
        this.gremio = null;
        this.corazon = new CorazonRPG(100 + nivel * 10, 50 + nivel * 5);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Arma getArmaEquipada() {
        return armaEquipada;
    }

    public Gremio getGremio() {
        return gremio;
    }

    public CorazonRPG getCorazon() {
        return corazon;
    }

    public void equiparArma(Arma nuevaArma) {
        if (nuevaArma == null) {
            System.out.println("No se puede equipar un arma nula.");
            return;
        }
        this.armaEquipada = nuevaArma;
        System.out.println(nombre + " ha equipado el arma: " + nuevaArma.getNombre());
    }

    public void desequiparArma() {
        if (this.armaEquipada != null) {
            String nombreArma = this.armaEquipada.getNombre();
            this.armaEquipada = null;
            System.out.println(nombre + " ha retirado el arma: " + nombreArma);
        } else {
            System.out.println(nombre + " no tiene ningun arma para retirar.");
        }
    }

    public int calcularAtaqueTotal() {
        int ataqueBase = this.nivel * 5;
        if (this.armaEquipada != null) {
            ataqueBase += this.armaEquipada.getDanioBase();
        }
        return ataqueBase;
    }

    public void unirseAGremio(Gremio nuevoGremio) {
        if (nuevoGremio == null || nuevoGremio == this.gremio) {
            return;
        }
        if (this.gremio != null) {
            Gremio anterior = this.gremio;
            this.gremio = null;
            anterior.removerMiembro(this);
        }
        this.gremio = nuevoGremio;
        nuevoGremio.agregarMiembro(this);
    }

    public void salirDeGremio() {
        if (this.gremio == null) {
            return;
        }
        Gremio anterior = this.gremio;
        this.gremio = null;
        anterior.removerMiembro(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Personaje: %s | Nivel: %d%n", nombre, nivel));
        sb.append(String.format("  Corazon : salud=%d, mana=%d%n", corazon.getSaludBase(), corazon.getManaBase()));
        if (armaEquipada != null) {
            sb.append(String.format("  Arma    : %s (danio %d)%n", armaEquipada.getNombre(), armaEquipada.getDanioBase()));
        } else {
            sb.append(String.format("  Arma    : desarmado%n"));
        }
        if (gremio != null) {
            sb.append(String.format("  Gremio  : %s%n", gremio.getNombre()));
        } else {
            sb.append(String.format("  Gremio  : sin gremio%n"));
        }
        sb.append(String.format("  Ataque total: %d", calcularAtaqueTotal()));
        return sb.toString();
    }
}
