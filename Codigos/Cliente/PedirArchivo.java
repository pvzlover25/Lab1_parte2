package Cliente;
import Paquetes.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.net.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PedirArchivo extends JButton implements ActionListener{
    private int indice;
    private PanelCentralC panel;
    private String nombreArchivo,texto;
    private byte[] datos;
    private boolean binario;
    public PedirArchivo(PanelCentralC panel){
        super("Pedir archivo");
        indice=0;
        this.panel=panel;
        nombreArchivo="";
        texto="";
        datos=null;
        binario=false;
        this.addActionListener(this);
    }
    private AbstractPaquete decodificar(byte[] bytes) throws IOException,ClassNotFoundException{
        ByteArrayInputStream byteArray=new ByteArrayInputStream(bytes);
        ObjectInputStream ois=new ObjectInputStream(byteArray);
        AbstractPaquete ret=(AbstractPaquete)ois.readObject();
        ois.close();
        return ret;
    }
    public void actionPerformed(ActionEvent ae){
        if(indice==0) pedirArchivo();
        if(indice==1) guardarArchivo();
        indice++;
    }
    private void mensajeError(String texto){
        JOptionPane.showMessageDialog(null,texto,"Error",0);
        indice--;
    }
    private void pedirArchivo(){
        String nombreIP=JOptionPane.showInputDialog("Ingresar la IP del servidor a donde enviar la peticion");
        AbstractPaquete recibo=null;
        try{
            DatagramSocket sc=new DatagramSocket();
            InetAddress ipDestino=InetAddress.getByName(nombreIP);
            int max=sc.getReceiveBufferSize();
            nombreArchivo=JOptionPane.showInputDialog("Ingresar el nombre del archivo a pedir");
            Mensaje men=new Mensaje("Recibir archivo del servidor",nombreArchivo);
            byte[] bytes=men.enBytes();
            DatagramPacket pack=new DatagramPacket(bytes,bytes.length,ipDestino,5000);
            sc.send(pack);
            pack=new DatagramPacket(new byte[max],max);
            sc.receive(pack);
            sc.close();
            recibo=decodificar(pack.getData());
            if(recibo.getIndice()==2){
                EnvioArchivoBin bin=(EnvioArchivoBin)recibo;
                datos=bin.getDatos();
                panel.setTextoArea(bin);
                binario=true;
            }else{
                EnvioArchivoTexto txt=(EnvioArchivoTexto)recibo;
                texto=txt.getTexto();
                panel.setTextoArea(txt);
                binario=false;
            }
            panel.reiniciarCarga(false);
            this.setText("Guardar archivo");
        }catch(IOException ioe){
            mensajeError("Error de conexion. Intente de nuevo.");
        }catch(ClassCastException cce){
            try{
                Mensaje msg=(Mensaje)recibo;
                mensajeError(msg.getMensaje());
            }catch(Exception ex){mensajeError("Error desconocido. Intente de nuevo.");}
        }catch(Exception ex){
            mensajeError("Error desconocido. Intente de nuevo.");
        }
    }
    private FileNameExtensionFilter filtro(){
        String[] partes=nombreArchivo.split("[.]");
        String extension=partes[partes.length-1];
        String descripcion="Archivo "+extension;
        return new FileNameExtensionFilter(descripcion,extension);
    }
    private void guardarArchivo(){
        JFileChooser elegir=new JFileChooser();
        elegir.setAcceptAllFileFilterUsed(false);
        elegir.addChoosableFileFilter(filtro());
        elegir.setSelectedFile(new File(nombreArchivo));
        if(elegir.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
            if(binario) panel.setArchivoAguardar(elegir.getSelectedFile(),this,datos);
            else panel.setArchivoAguardar(elegir.getSelectedFile(), this, texto);
            this.setEnabled(false);
            this.setText("Pedir Archivo");
            indice=-1;
            panel.iniciarTimer(true);
        }     
    }
}
