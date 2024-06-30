package ve.edu.unet.nodosAST;
public class NodoSecuencia extends NodoBase{
    private NodoBase dcs;
    private NodoBase decl;

    public NodoSecuencia(NodoBase dcs, NodoBase decl) {
        super();
        this.dcs = dcs;
        this.decl = decl;
    }

    public NodoSecuencia(NodoBase decl) {
        super();
        this.dcs = null;
        this.decl = decl;
    }

    public NodoBase getDcs() {
        return dcs;
    }

    public void setDcs(NodoBase dcs) {
        this.dcs = dcs;
    }

    public NodoBase getDecl() {
        return decl;
    }

    public void setDecl(NodoDeclaracion decl) {
        this.decl = decl;
    }
}
