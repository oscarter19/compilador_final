
package ve.edu.unet.nodosAST;

public class NodoVariableEntera extends NodoBase {
    private String nombre;

    public NodoVariableEntera(String nombre) {
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