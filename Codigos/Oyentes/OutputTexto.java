/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Oyentes;
import java.awt.event.ActionEvent;
import java.io.*;

/**
 *
 * @author marag
 */
public abstract class OutputTexto extends OyenteTimer{
    protected FileWriter escritor;
    //private File archivoAguardar;
    private String texto,direccion;
    public OutputTexto(String texto, String direccion){
        super(true,false,texto.length());
        this.texto=texto;
        this.direccion=direccion;
        int size=texto.length();
        partes=new int[11];
        partes[0] = 0;
        partes[10]=size;
        for (int i = 1; i <10; i++) {
            double proporcion = (double) i / 10;
            partes[i] = (int) (proporcion * size);
        }
        escritor=null;
    }
    public String getTexto() {
        return texto;
    }
    public byte[] getBytes() {
        return null;
    }    
    public void accion() throws IOException{
        if(escritor==null) escritor=new FileWriter(direccion);
        for(int i=partes[indice];i<partes[indice+1];i++){
            char c=texto.charAt(i);
            escritor.write(c);
        }
        indice++;
    }
}
