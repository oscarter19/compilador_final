package ve.edu.unet.nodosAST;

public class NodoDeclaracion extends NodoBase {
    private String var;


    public NodoDeclaracion(String var) {
        super();
        this.var = var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getVar() {
        return var;
    }
}
