package ve.edu.unet.nodosAST;

public class NodoVector extends NodoBase {
    private String nombre;
    private NodoBase indice;

    public NodoVector(String nombre, NodoBase indice) {
        super();
        this.nombre = nombre;
        this.indice = indice;
    }

    public String getNombre() { return nombre; }
    public NodoBase getIndice() { return indice; }
}