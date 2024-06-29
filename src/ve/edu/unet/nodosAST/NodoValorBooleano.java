package ve.edu.unet.nodosAST;

public class NodoValorBooleano extends NodoBase{
    private boolean valor;

    public NodoValorBooleano(boolean valor) {
        super();
        this.valor = valor;
    }

    public boolean isValor() {
        return valor;
    }

    public void setValor(boolean valor) {
        this.valor = valor;
    }
}
