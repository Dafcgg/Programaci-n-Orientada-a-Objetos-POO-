public class Mago extends Personaje {

    private static final int COSTO_ESPECIAL = 20;
    private static final int COSTO_FURIA = 10;

    private int puntosMana;

    public Mago(String nombre, int vida, int ataqueBase, int posicionX, int posicionY, int puntosMana) {
        super(nombre, vida, ataqueBase, posicionX, posicionY);
        this.puntosMana = Math.max(puntosMana, 0);
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        if (!puedeAtacar(objetivo)) {
            return;
        }
        if (puntosMana < COSTO_ESPECIAL) {
            System.out.println(nombre + " no tiene mana suficiente y recurre a un ataque basico.");
            atacar(objetivo);
            return;
        }
        puntosMana -= COSTO_ESPECIAL;
        int dano = ataqueBase * 2;
        System.out.println(nombre + " lanza una Bola de Fuego contra " + objetivo.getNombre()
                + " (dano base: " + dano + ", mana restante: " + puntosMana + ")");
        objetivo.defender(dano);
    }

    public void atacar(Personaje objetivo) {
        atacar(objetivo, false);
    }

    public void atacar(Personaje objetivo, boolean modoFuria) {
        if (!puedeAtacar(objetivo)) {
            return;
        }
        int dano = ataqueBase;
        String tipo = "con un rayo arcano";
        if (modoFuria && puntosMana >= COSTO_FURIA) {
            puntosMana -= COSTO_FURIA;
            dano = (ataqueBase * 3) / 2;
            tipo = "con un rayo arcano potenciado";
        }
        System.out.println(nombre + " ataca " + tipo + " a " + objetivo.getNombre() + " (dano base: " + dano + ")");
        objetivo.defender(dano);
    }

    public int getPuntosMana() {
        return puntosMana;
    }

    public void setPuntosMana(int puntosMana) {
        this.puntosMana = Math.max(puntosMana, 0);
    }
}
