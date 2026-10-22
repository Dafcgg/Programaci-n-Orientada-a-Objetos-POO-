public class CorazonRPG {
    private int saludBase;
    private int manaBase;

    public CorazonRPG(int saludBase, int manaBase) {
        this.saludBase = saludBase;
        this.manaBase = manaBase;
    }

    public int getSaludBase() {
        return saludBase;
    }

    public void setSaludBase(int saludBase) {
        this.saludBase = saludBase;
    }

    public int getManaBase() {
        return manaBase;
    }

    public void setManaBase(int manaBase) {
        this.manaBase = manaBase;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CorazonRPG[saludBase=").append(saludBase);
        sb.append(", manaBase=").append(manaBase).append("]");
        return sb.toString();
    }
}
