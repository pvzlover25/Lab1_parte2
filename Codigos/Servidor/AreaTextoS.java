package Servidor;
import Auxiliar.Extensiones;
import javax.swing.JTextArea;
import Paquetes.EnvioArchivo;
import java.io.File;
//import java.awt.Color;

public class AreaTextoS extends JTextArea{
    private String texto;
    public AreaTextoS(int x, int y, int ancho, int alto, boolean enviado){
        texto="Datos del archivo ";
        if(enviado) texto+="enviado:\n\n";
        else texto+="recibido:\n\n";
        this.setBounds(x, y, ancho, alto);
        this.setFont(VentanaServer.ARIAL18);//18f
        this.setBackground(VentanaServer.FONDO);
        this.setText(texto);
        this.setEditable(false);
    }
    public void setText(EnvioArchivo paquete){
        if(paquete==null){
            this.setText(texto);
            return;
        }
        String nuevo=texto+"Nombre: "+paquete.getNombre();
        if(paquete.getIndice()==2) nuevo+=" (archivo binario)\n";
        else nuevo+=" (archivo de texto)\n";
        nuevo+="Tamanio: "+paquete.getTamanio()+" bytes.";
        this.setText(nuevo);
    }
    private boolean esTexto(File f){
        String[] partes=f.getName().split("[.]");
        return (Extensiones.extensionesTexto.contains(partes[partes.length-1]));
    }
    public void setText(File f){
        String nuevo=texto+"Nombre: "+f.getName();
        if(esTexto(f)) nuevo+=" (archivo de texto)\n";
        else nuevo+=" (archivo binario)\n";
        nuevo+="Tamanio: "+f.length()+" bytes.";
        this.setText(nuevo);
    }
}