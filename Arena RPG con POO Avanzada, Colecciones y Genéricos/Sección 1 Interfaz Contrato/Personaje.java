public abstract class Personaje implements ICombatiente {

    protected String nombre;
    protected int vida;
    protected int ataqueBase;
    protected int posicionX;
    protected int posicionY;
    private final int vidaMaxima;

    public Personaje(String nombre, int vida, int ataqueBase, int posicionX, int posicionY) {
        this.nombre = (nombre == null || nombre.trim().isEmpty()) ? "Sin nombre" : nombre.trim();
        this.vida = Math.max(vida, 0);
        this.ataqueBase = Math.max(ataqueBase, 0);
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.vidaMaxima = this.vida;
    }

    public abstract void realizarAtaqueEspecial(Personaje objetivo);

    public boolean estaVivo() {
        return vida > 0;
    }

    public void recibirDano(int cantidad) {
        if (cantidad <= 0) {
            return;
        }
        vida = Math.max(vida - cantidad, 0);
    }

    public void curar(int cantidad) {
        if (cantidad <= 0 || !estaVivo()) {
            return;
        }
        vida = Math.min(vida + cantidad, vidaMaxima);
    }

    @Override
    public void defender(int danoRecibido) {
        int vidaAnterior = vida;
        recibirDano(danoRecibido);
        System.out.println(nombre + " recibe " + (vidaAnterior - vida) + " de dano. Vida restante: " + vida);
        if (!estaVivo()) {
            System.out.println(nombre + " ha caido en combate.");
        }
    }

    protected boolean puedeAtacar(Personaje objetivo) {
        return objetivo != null && objetivo != this && estaVivo() && objetivo.estaVivo();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        }
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = Math.min(Math.max(vida, 0), vidaMaxima);
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getAtaqueBase() {
        return ataqueBase;
    }

    public void setAtaqueBase(int ataqueBase) {
        this.ataqueBase = Math.max(ataqueBase, 0);
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }
}
