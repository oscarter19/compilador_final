package ve.edu.unet.nodosAST;

public class NodoFor extends NodoBase {
    private String variable;
    private NodoBase inicio;

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public NodoBase getInicio() {
        return inicio;
    }

    public void setInicio(NodoBase inicio) {
        this.inicio = inicio;
    }

    public NodoBase getFin() {
        return fin;
    }

    public void setFin(NodoBase fin) {
        this.fin = fin;
    }

    public boolean isAscendente() {
        return ascendente;
    }

    public void setAscendente(boolean ascendente) {
        this.ascendente = ascendente;
    }

    private NodoBase fin;

    private boolean ascendente;

    public NodoFor(String variable, NodoBase inicio, NodoBase fin, boolean ascendente) {
        super();
        this.variable = variable;
        this.inicio = inicio;
        this.fin = fin;
        this.ascendente = ascendente;
    }
}