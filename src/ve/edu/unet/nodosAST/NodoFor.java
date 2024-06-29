package ve.edu.unet.nodosAST;

public class NodoFor extends NodoBase{
    private Object cuerpo;
    private NodoBase inicio;
    private NodoBase fin;
    private String variable_name;
    private boolean v_bool;

    public NodoFor(String variable_name, NodoBase inicio, NodoBase fin, Object cuerpo,  boolean n_bool) {
        super();
        this.cuerpo = cuerpo;
        this.inicio = inicio;
        this.fin = fin;
        this.variable_name = variable_name;
        this.v_bool = n_bool;
    }

    public Object getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(Object cuerpo) {
        this.cuerpo = cuerpo;
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

    public String getVariable_name() {
        return variable_name;
    }

    public void setVariable_name(String variable_name) {
        this.variable_name = variable_name;
    }

    public boolean isV_bool() {
        return v_bool;
    }

    public void setV_bool(boolean v_bool) {
        this.v_bool = v_bool;
    }
}
