package ve.edu.unet.nodosAST;

public class NodoFor extends NodoBase {
    private NodoBase inicializacion;
    private NodoBase condicion;
    private NodoBase incremento;
    private NodoBase cuerpo;

    public NodoFor(NodoBase inicializacion, NodoBase condicion, NodoBase incremento, NodoBase cuerpo) {
        super();
        this.inicializacion = inicializacion;
        this.condicion = condicion;
        this.incremento = incremento;
        this.cuerpo = cuerpo;
    }

    public NodoBase getInicializacion() { return inicializacion; }
    public NodoBase getCondicion() { return condicion; }
    public NodoBase getIncremento() { return incremento; }
    public NodoBase getCuerpo() { return cuerpo; }
}
