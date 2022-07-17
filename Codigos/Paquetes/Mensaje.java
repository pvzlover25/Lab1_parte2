package Paquetes;
import java.io.Serializable;

public class Mensaje extends AbstractPaquete{
    private static final long serialVersionUID=1;
    private String peticion,nombreArchivo;
    public Mensaje(String peticion, String nombreArchivo){
        super(1);
        this.peticion=peticion;
        this.nombreArchivo=nombreArchivo;
    }
    public Mensaje(String peticion){
        this(peticion,"");
    }
    public String getMensaje(){
        return peticion;
    }
    public String nombreArchivoPedido(){
        return nombreArchivo;
    }
}
