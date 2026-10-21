public class Guerrero extends Personaje {

    private int fuerzaEscudo;

    public Guerrero(String nombre, int vida, int ataqueBase, int posicionX, int posicionY, int fuerzaEscudo) {
        super(nombre, vida, ataqueBase, posicionX, posicionY);
        this.fuerzaEscudo = Math.max(fuerzaEscudo, 0);
    }

    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        if (!puedeAtacar(objetivo)) {
            return;
        }
        int dano = ataqueBase * 2;
        System.out.println(nombre + " lanza un Golpe Brutal contra " + objetivo.getNombre() + " (dano base: " + dano + ")");
        objetivo.defender(dano);
    }

    @Override
    public void defender(int danoRecibido) {
        int reduccion = fuerzaEscudo / 2;
        int danoReducido = danoRecibido > 0 ? Math.max(danoRecibido - reduccion, 1) : 0;
        if (danoRecibido > 0 && danoRecibido > danoReducido) {
            System.out.println(nombre + " bloquea " + (danoRecibido - danoReducido) + " de dano con su escudo.");
        }
        super.defender(danoReducido);
    }

    public void atacar(Personaje objetivo) {
        atacar(objetivo, false);
    }

    public void atacar(Personaje objetivo, boolean modoFuria) {
        if (!puedeAtacar(objetivo)) {
            return;
        }
        int dano = modoFuria ? (ataqueBase * 3) / 2 : ataqueBase;
        String tipo = modoFuria ? "en modo furia" : "con un ataque basico";
        System.out.println(nombre + " ataca " + tipo + " a " + objetivo.getNombre() + " (dano base: " + dano + ")");
        objetivo.defender(dano);
    }

    public int getFuerzaEscudo() {
        return fuerzaEscudo;
    }

    public void setFuerzaEscudo(int fuerzaEscudo) {
        this.fuerzaEscudo = Math.max(fuerzaEscudo, 0);
    }
}
