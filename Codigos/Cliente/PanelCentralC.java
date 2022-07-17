package Cliente;
import Auxiliar.Extensiones;
import Oyentes.*;
import Paquetes.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.swing.*;

public class PanelCentralC extends JPanel{
    private int largoRecibo,largoEnvio;
    private long tamArchivo;
    private Timer tcarga,tguarda;
    private JButton cargar,guardar;
    private AreaTextoC entregado,recibido;
    private OyenteTimer input,output;
    private String nombreArchivo;
    public PanelCentralC(){
        this.setLayout(null);
        this.setBackground(VentanaCliente.FONDO);
        largoRecibo=0;
        largoEnvio=0;
        cargar=null;
        guardar=null;
        tcarga=null;
        tguarda=null;
        input=null;
        output=null;
        recibido=new AreaTextoC(20,20,320,100,false);
        entregado=new AreaTextoC(380,20,320,100,true);
        nombreArchivo="";
        JScrollPane scrollRec=new JScrollPane(recibido,21,30);
        JScrollPane scrollEnt=new JScrollPane(entregado,21,30);
        scrollRec.setBounds(20,20,320,100);
        scrollEnt.setBounds(380,20,320,100);
        scrollRec.setBorder(null);
        scrollEnt.setBorder(null);
        this.add(scrollRec);
        this.add(scrollEnt);
    }
    private void carga(int x, int y, int largo, Graphics gr){
        gr.setColor(Color.black);
        gr.fillRect(x,y,310,75);
        gr.setColor(VentanaCliente.FONDO);
        gr.fillRect(x+5, y+5, 300, 65);
        gr.setColor(Color.green);
        gr.fillRect(x+5, y+5, largo, 65);
        gr.setColor(Color.black);
        Graphics2D gd = (Graphics2D) gr;
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gd.setFont(VentanaCliente.ARIAL);
        gd.drawString(largo / 3 + "%", x, y-5);
    }
    public void paint(Graphics gr){
        super.paint(gr);
        gr.setColor(Color.BLACK);
        gr.drawLine(360,0,360,500);
        carga(25,165,largoRecibo,gr);
        carga(385,165,largoEnvio,gr);
    }
    private boolean esArchivoTexto(String nombre){
        String[] partes=nombre.split("[.]");
        return Extensiones.extensionesTexto.contains(partes[partes.length-1]);
    }
    public void setArchivoAleer(File f, CargarArchivo c){
        cargar=c;
        tamArchivo=f.length();
        nombreArchivo=f.getName();
        entregado.setText(f);
        if(esArchivoTexto(nombreArchivo)){
            input=new InputText(f);
        }else input=new InputBinario(f);
        tcarga=new Timer(200,input);
    }
    public void iniciarTimer(boolean guarda){
        if(guarda) tguarda.start();
        else tcarga.start();
    }
    public AbstractPaquete crearPaquete(){
        if(esArchivoTexto(nombreArchivo)){
            String texto=input.getTexto();
            return new EnvioArchivoTexto(nombreArchivo,tamArchivo,texto);
        }else{
            byte[] bytes=input.getBytes();
            return new EnvioArchivoBin(nombreArchivo,tamArchivo,bytes);
        }
    }
    public void reiniciarCarga(boolean envio){
        if(envio) largoEnvio=0;
        else largoRecibo=0;
        repaint();
    }
    public void setArchivoAguardar(File f, PedirArchivo p, String texto){
        guardar=p;
        output=new OutputText(texto,f.getAbsolutePath());
        tguarda=new Timer(200,output);  
    }
    public void setArchivoAguardar(File f, PedirArchivo p, byte[] bytes){
        guardar=p;
        output=new OutputBinario(bytes,f.getAbsolutePath());
        tguarda=new Timer(200,output); 
    }
    public void setTextoArea(EnvioArchivo pack){
        recibido.setText(pack);
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
                    cargar.setEnabled(true);
                    tcarga.stop();
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
                    cargar.setEnabled(true);
                    tcarga.stop();
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
                    guardar.setEnabled(true);
                    tguarda.stop();
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
                    guardar.setEnabled(true);
                    tguarda.stop();
                }
            }catch(IOException ex){}
        }
    }
}
