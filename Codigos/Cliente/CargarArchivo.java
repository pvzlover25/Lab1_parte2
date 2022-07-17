package Cliente;
import Paquetes.AbstractPaquete;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;

public class CargarArchivo extends JButton implements ActionListener{
    private int indice;
    private PanelCentralC panel;
    public CargarArchivo(PanelCentralC panel){
        super("Subir archivo");
        this.panel=panel;
        indice=0;
        this.addActionListener(this);
    }
    public void actionPerformed(ActionEvent ae){
        if(indice==0) elegirArchivo();
        if(indice==1) cargarArchivo();
        if(indice==2) enviarArchivo();
        indice++;
    }
    private void elegirArchivo(){
        JFileChooser select=new JFileChooser();
        if(select.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
            panel.setArchivoAleer(select.getSelectedFile(),this);
            panel.reiniciarCarga(true);
            this.setText("Cargar Archivo");
        }else indice--;
    }
    private void cargarArchivo(){
        this.setEnabled(false);
        this.setText("Enviar archivo");
        panel.iniciarTimer(false);
    }
    private void enviarArchivo(){
        String nombreIP=JOptionPane.showInputDialog("Ingresar la IP del servidor a donde enviar el archivo");
        try{
            InetAddress ipDestino=InetAddress.getByName(nombreIP);
            DatagramSocket sc=new DatagramSocket();
            byte[] bytes=panel.crearPaquete().enBytes();
            DatagramPacket envio=new DatagramPacket(bytes,bytes.length,ipDestino,5000);
            sc.send(envio);
            sc.close();
            this.setText("Subir Archivo");
            JOptionPane.showMessageDialog(null,"Archivo enviado con exito");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Error al enviar el archivo. Intente de nuevo.","Error",0);
            System.err.println(ex.getMessage());
        }
        indice=-1;
    }
}
