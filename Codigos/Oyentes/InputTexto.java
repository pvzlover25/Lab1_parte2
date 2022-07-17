package Oyentes;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.swing.*;

public abstract class InputTexto extends OyenteTimer{
    private String texto;
    protected FileReader lector;
    private File archivoAleer;
    public InputTexto(File f){
        super(true,false,(int)f.length());
        texto="";
        lector=null;
        archivoAleer=f;
    }
    public String getTexto() {
        return texto;
    }
    public byte[] getBytes() {
        return null;
    }
    public void accion() throws IOException{
        if (lector == null) lector = new FileReader(archivoAleer);
        for (int i = partes[indice]; i < partes[indice + 1]; i++) {
            int c = lector.read();
            char caracter = (char) c;
            texto += caracter;
        }
        indice++;
    }
}
