package ve.edu.unet.nodosAST;

public class NodoBloque extends NodoBase{
    private NodoBase dlcs;
    private NodoBase st;

    public NodoBloque(NodoBase dlcs, NodoBase st) {
        super();
        this.dlcs = dlcs;
        this.st = st;
    }

    public NodoBloque(NodoBase st) {
        super();
        this.dlcs = null;
        this.st = st;
    }

    public NodoBase getSt() {
        return st;
    }

    public void setSt(NodoBase st) {
        this.st = st;
    }

    public NodoBase getDlcs() {
        return dlcs;
    }

    public void setDlcs(NodoBase dlcs) {
        this.dlcs = dlcs;
    }
}
