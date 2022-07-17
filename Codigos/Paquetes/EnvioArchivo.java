package Paquetes;

public abstract class EnvioArchivo extends AbstractPaquete{
    private static final long serialVersionUID=1;
    private String nombreArchivo;
    private long tamanio;
    public EnvioArchivo(int indice, String nombre, long tam){
        super(indice);
        nombreArchivo=nombre;
        tamanio=tam;
    }
    public long getTamanio(){
        return tamanio;
    }
    public String getNombre(){
        return nombreArchivo;
    }
}
