package Oyentes;
import java.awt.event.ActionEvent;
import java.io.*;

public abstract class OutputBin extends OyenteTimer{
    protected FileOutputStream escritor;
    private String direccion;
    private byte[] bytes;
    public OutputBin(byte[] bytes, String direccion){
        super(false,true,bytes.length);
        this.bytes=bytes;
        escritor=null;
        this.direccion=direccion;
    }
    public String getTexto() {
        return "";
    }
    public byte[] getBytes() {
        return bytes;
    }
    public void accion() throws IOException{
        if(escritor==null) escritor=new FileOutputStream(direccion);
        for(int i=partes[indice];i<partes[indice+1]-1;i++){
            escritor.write(bytes[i]);
        }
        indice++;
    }
}
