package Oyentes;
import java.awt.event.ActionEvent;
import java.io.*;

public abstract class InputBin extends OyenteTimer{
    private byte[] bytes;
    protected FileInputStream lector;
    private File archivoAleer;
    public InputBin(File f){
        super(false,true,(int)f.length());
        archivoAleer=f;
        lector=null;
        int size = (int) archivoAleer.length();
        partes = new int[11];
        bytes=new byte[size];
        partes[0] = 0;
        for (int i = 1; i <= 10; i++) {
            double proporcion = (double) i / 10;
            partes[i] = (int) (proporcion * size);
        }
    }
    public String getTexto(){
        return "";
    }
    public byte[] getBytes(){
        return bytes;
    }
    public void accion() throws IOException{
        if(lector==null) lector=new FileInputStream(archivoAleer);
        for (int i = partes[indice]; i < partes[indice + 1] - 1; i++) {
            bytes[i] = (byte) lector.read();
        }
        indice++;
    }
    
}
