package Oyentes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public abstract class OyenteTimer implements ActionListener{
    private boolean deTexto,binario;
    protected int indice;
    protected int[] partes;
    public OyenteTimer(boolean deTexto, boolean binario, int size){
        this.deTexto=deTexto;
        this.binario=binario;
        indice=0;
        partes=new int[11];
        partes[0]=0;
        partes[10]=size;
        for(int i=1;i<10;i++){
            double proporcion=(double)i/10;
            partes[i]=(int)(proporcion*size);
        }
    }
    public boolean archivoEsDeTexto(){
        return deTexto;
    }
    public boolean archivoEsBinario(){
        return binario;
    }
    public abstract String getTexto();
    public abstract byte[] getBytes();
    public abstract void accion() throws IOException;
}
