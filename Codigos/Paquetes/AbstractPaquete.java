package Paquetes;
import java.io.*;

public abstract class AbstractPaquete implements Serializable{
    private int indice;
    public AbstractPaquete(int i){
        indice=i;
    }
    public int getIndice(){
        return indice;
    }
    public byte[] enBytes() throws IOException{
        ByteArrayOutputStream bytes=new ByteArrayOutputStream();
        ObjectOutputStream flujo=new ObjectOutputStream(bytes);
        flujo.writeObject(this);
        flujo.close();
        return bytes.toByteArray();
    }
}
