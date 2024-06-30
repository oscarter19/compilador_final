package ve.edu.unet.nodosAST;

public class NodoOperacionVector extends NodoBase {
    private NodoVector vectorIzquierdo;
    private NodoVector vectorDerecho;
    private Operacion operacion;

    public enum Operacion {
        SUMA,
        RESTA,
        MULTIPLICACION,
        DIVISION
    }

    public NodoOperacionVector(NodoVector vectorIzquierdo, NodoVector vectorDerecho, Operacion operacion) {
        super();
        this.vectorIzquierdo = vectorIzquierdo;
        this.vectorDerecho = vectorDerecho;
        this.operacion = operacion;
    }

    public NodoVector getVectorIzquierdo() {
        return vectorIzquierdo;
    }

    public NodoVector getVectorDerecho() {
        return vectorDerecho;
    }

    public Operacion getOperacion() {
        return operacion;
    }
}
