package ve.edu.unet.nodosAST;

public class NodoValorBooleano extends NodoBase {
    private Boolean value;

    public NodoValorBooleano(Boolean nombre) {
        super();
        this.value = nombre;
    }

    public int getValor() {
        return value ? 1 : 0;
    }

    public void setValue(Boolean nombre) {
        this.value = nombre;
    }
}
