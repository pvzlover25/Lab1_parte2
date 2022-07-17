package Paquetes;
import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArchivosServer extends AbstractPaquete implements Iterable<String>{
    private static final long serialVersionUID=1;
    private String[] nombres;
    public ArchivosServer(File carpeta){
        super(0);
        nombres=carpeta.list();
    }
    public Iterator<String> iterator() {
        return new IterarStrings();
    }
    private class IterarStrings implements Iterator<String>{
        private int indice;
        public IterarStrings(){
            indice=0;
        }
        public boolean hasNext() {
            return (indice<nombres.length);
        }
        public String next() {
            if(!hasNext()) throw new NoSuchElementException("No hay mas elementos");
            String ret=nombres[indice];
            indice++;
            return ret;
        }
    }
}



