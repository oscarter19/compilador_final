package ve.edu.unet.nodosAST;

public class NodoVariableBooleana extends NodoBase {
    private String nombre;

    public NodoVariableBooleana(String nombre) {
        super();
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
