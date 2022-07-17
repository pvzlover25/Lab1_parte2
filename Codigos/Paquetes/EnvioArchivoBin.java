package Paquetes;
import java.io.File;

public class EnvioArchivoBin extends EnvioArchivo{
    private static final long serialVersionUID=1;
    private byte[] datosArchivo;
    public EnvioArchivoBin(String nombreArchivo, long tamanio, byte[] datos){
        super(2,nombreArchivo,tamanio);
        datosArchivo=datos;
    }
    public EnvioArchivoBin(File f, byte[] datos){
        this(f.getName(),f.length(),datos);
    }
    public byte[] getDatos(){
        return datosArchivo;
    }
}
