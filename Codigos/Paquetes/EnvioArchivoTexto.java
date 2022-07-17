package Paquetes;

import java.io.File;

public class EnvioArchivoTexto extends EnvioArchivo{
    private static final long serialVersionUID=1;
    private String texto;
    public EnvioArchivoTexto(String nombreArchivo, long tamanio, String texto){
        super(3,nombreArchivo,tamanio);
        this.texto=texto;
    }
    public EnvioArchivoTexto(File f, String texto){
        this(f.getName(),f.length(),texto);
    }
    public String getTexto(){
        return texto;
    }
}
