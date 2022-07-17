package Servidor;
import Auxiliar.Extensiones;
import Oyentes.*;
import java.awt.*;
import java.net.*;
import javax.swing.*;
import Paquetes.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class PanelCentralS extends JPanel implements Runnable{
    //private boolean pintarCarga;
    private String dirCarpeta;
    private int largoEnvio,largoRecibo;
    private AreaTextoS entregado,recibido;
    //Paquete recibido
    private GuardarArchivo g;
    private File archivoAguardar;
    private Timer toutput;
    private TimerHilo tinput;
    public PanelCentralS(String carpetaServer){
        this.setBackground(VentanaServer.FONDO);
        this.setLayout(null);
        //pintarCarga=true;
        dirCarpeta=carpetaServer;
        archivoAguardar=null;
        largoEnvio=0;
        largoRecibo=0;
        tinput=null;
        toutput=null;
        g=null;
        recibido=new AreaTextoS(20,20,320,100,false);
        entregado=new AreaTextoS(380,20,320,100,true);
        JScrollPane scrollRec=new JScrollPane(recibido,21,30);
        JScrollPane scrollEnt=new JScrollPane(entregado,21,30);
        scrollRec.setBounds(20,20,320,100);
        scrollEnt.setBounds(380,20,320,100);
        scrollRec.setBorder(null);
        scrollEnt.setBorder(null);
        this.add(scrollRec);
        this.add(scrollEnt);
        Thread hilo=new Thread(this);
        hilo.start();
    }
    private void carga(int x, int y, int largo, Graphics gr){
        gr.setColor(Color.black);
        gr.fillRect(x,y,310,75);
        gr.setColor(VentanaServer.FONDO);
        gr.fillRect(x+5, y+5, 300, 65);
        gr.setColor(Color.green);
        gr.fillRect(x+5, y+5, largo, 65);
        gr.setColor(Color.black);
        Graphics2D gd = (Graphics2D) gr;
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gd.setFont(VentanaServer.ARIAL);
        gd.drawString(largo / 3 + "%", x, y-5);
    }
    public void paint(Graphics gr){
        super.paint(gr);
        gr.setColor(Color.black);
        gr.drawLine(360,0,360,500);
        carga(25,165,largoRecibo,gr);
        carga(385,165,largoEnvio,gr);
    }
    private AbstractPaquete decodificar(byte[] bytes) throws Exception{
        ByteArrayInputStream byteArray=new ByteArrayInputStream(bytes);
        ObjectInputStream ois=new ObjectInputStream(byteArray);
        AbstractPaquete ret=(AbstractPaquete)ois.readObject();
        ois.close();
        return ret;
    }
    private File buscarArchivo(File carpeta, String nombre){
        for(File f:carpeta.listFiles()){
            if(nombre.equals(f.getName())) return f;
        }
        return null;
    }
    private AbstractPaquete getPaquete(File archivo, OyenteTimer oyente, boolean binario){
        entregado.setText(archivo);
        tinput=new TimerHilo(oyente);
        return tinput.crearPaquete(archivo,binario);
    }
    private AbstractPaquete responderMsg(Mensaje men){
        AbstractPaquete ret=null;
        File carpeta=new File(dirCarpeta);
        if(men.getMensaje().equals("Ver archivos en la carpeta del servidor")){
            ret=new ArchivosServer(carpeta);
        }else{
            String nombreArchivo=men.nombreArchivoPedido();
            String[] partes=nombreArchivo.split("[.]");
            File archivoPedido=buscarArchivo(carpeta,nombreArchivo);
            if(archivoPedido==null) return new Mensaje("Error: No existe el archivo pedido");
            reiniciarCarga(true);
            if(Extensiones.extensionesTexto.contains(partes[partes.length-1])){
                ret=getPaquete(archivoPedido,new InputText(archivoPedido),false);
            }else ret=getPaquete(archivoPedido,new InputBinario(archivoPedido),true);
        }
        return ret;
    }
    private void reiniciarCarga(boolean envio){
        if(envio) largoEnvio=0;
        else largoRecibo=0;
        repaint();
    }
    private void leerTxt(EnvioArchivoTexto txt){
        reiniciarCarga(false);
        recibido.setText(txt);
        archivoAguardar=new File(dirCarpeta,txt.getNombre());
        OutputText oyente=new OutputText(txt.getTexto(),archivoAguardar.getAbsolutePath());
        toutput=new Timer(200,oyente);
    }
    private void leerBin(EnvioArchivoBin bin){
        reiniciarCarga(false);
        recibido.setText(bin);
        archivoAguardar=new File(dirCarpeta,bin.getNombre());
        OutputBinario oyente=new OutputBinario(bin.getDatos(),archivoAguardar.getAbsolutePath());
        toutput=new Timer(200,oyente);
    }
    public void setBoton(GuardarArchivo g){
        this.g=g;
    }
    public void iniciarTimer(){
        toutput.start();
    }
    public void run(){
        try{
            DatagramSocket serverUDP=new DatagramSocket(5000);
            int max=serverUDP.getReceiveBufferSize();
            while(true){
                DatagramPacket paqueteUDP=new DatagramPacket(new byte[max],max);
                serverUDP.receive(paqueteUDP);
                AbstractPaquete recepcion=decodificar(paqueteUDP.getData());
                if(recepcion.getIndice()==3){
                    EnvioArchivoTexto txt=(EnvioArchivoTexto)recepcion;
                    g.setEnabled(true);
                    leerTxt(txt);
                }else if(recepcion.getIndice()==2){
                    EnvioArchivoBin bin=(EnvioArchivoBin)recepcion;
                    g.setEnabled(true);
                    leerBin(bin);
                }else if(recepcion.getIndice()==1){
                    Mensaje men=(Mensaje)recepcion;
                    AbstractPaquete enviar=responderMsg(men);
                    int portCliente=paqueteUDP.getPort();
                    InetAddress ip=paqueteUDP.getAddress();
                    byte[] bytes=enviar.enBytes();
                    DatagramPacket respuesta=new DatagramPacket(bytes,bytes.length,ip,portCliente);
                    serverUDP.send(respuesta);
                }      
            }
        }catch(Exception ex){}
    }
    private class InputText extends InputTexto{
        public InputText(File f){
            super(f);
        }
        public void actionPerformed(ActionEvent ae) {
            try {
                this.accion();
                largoEnvio+=30;
                repaint();
                if(indice==10){
                    lector.close();
                    //tinput.stop();
                }
            } catch (IOException ex) {}
        }
    }
    private class InputBinario extends InputBin{
        public InputBinario(File f){
            super(f);
        }
        public void actionPerformed(ActionEvent ae) {
            try {
                this.accion();
                largoEnvio+=30;
                repaint();
                if(indice==10){
                    lector.close();
                    //tinput.stop();
                }
            } catch (IOException ex) {}
        }
    }
    private class OutputText extends OutputTexto{
        public OutputText(String texto, String direccion){
            super(texto,direccion);
        }
        public void actionPerformed(ActionEvent ae){
            try{
                this.accion();
                largoRecibo+=30;
                repaint();
                if(indice==10){
                    escritor.close();
                    toutput.stop();
                    JOptionPane.showMessageDialog(null,"Archivo guardado con exito");
                }
            }catch(IOException ex){}
        }
    }
    private class OutputBinario extends OutputBin{
        public OutputBinario(byte[] bytes, String direccion){
            super(bytes,direccion);
        }
        public void actionPerformed(ActionEvent ae){
            try{
                this.accion();
                largoRecibo+=30;
                repaint();
                if(indice==10){
                    escritor.close();
                    toutput.stop();
                    JOptionPane.showMessageDialog(null,"Archivo guardado con exito");
                }
            }catch(IOException ex){}
        }
    }
}
