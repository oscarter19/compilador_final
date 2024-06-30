package ve.edu.unet.nodosAST;

public class NodoValorBooleano extends NodoBase {
    private Boolean nombre;

    public NodoValorBooleano(Boolean nombre) {
        super();
        this.nombre = nombre;
    }

    public Boolean getNombre() {
        return nombre;
    }

    public void setNombre(Boolean nombre) {
        this.nombre = nombre;
    }
}
