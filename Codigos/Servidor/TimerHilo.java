package Servidor;
import Oyentes.OyenteTimer;
import Paquetes.*;
import java.io.File;

public class TimerHilo {
    private MiHilo h;
    public TimerHilo(OyenteTimer oyente) {
        h=new MiHilo(oyente);
    }
    public synchronized void notificar(){
        notify();
    }
    public synchronized AbstractPaquete crearPaquete(File archivo, boolean binario){
        h.start();
        boolean b=true;
        while(b){
            try{wait();}
            catch(InterruptedException ie){}
            b=false;
        }
        if(binario) return new EnvioArchivoBin(archivo,h.getBytes());
        return new EnvioArchivoTexto(archivo,h.getTexto());
    }
    private class MiHilo extends Thread{
        private OyenteTimer oyente;
        private int i;
        public MiHilo(OyenteTimer oyente){
            this.oyente=oyente;
            i=0;
        }
        public void run(){
            while(i<10){
                oyente.actionPerformed(null);
                i++;
                if(i<10){
                    try{Thread.sleep(200);}
                    catch(InterruptedException ie){}
                }
            }
            notificar();
        }
        public String getTexto(){
            return oyente.getTexto();
        }
        public byte[] getBytes(){
            return oyente.getBytes();
        }
    }
}
