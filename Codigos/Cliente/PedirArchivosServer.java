package Cliente;
import Paquetes.*;
import java.awt.event.*;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.*;
import javax.swing.*;

public class PedirArchivosServer extends JButton implements ActionListener{
    public PedirArchivosServer(){
        super("Archivos del servidor");
        this.addActionListener(this);
    }
    private ArchivosServer decodificar(byte[] bytes) throws Exception{
        ByteArrayInputStream byteArray=new ByteArrayInputStream(bytes);
        ObjectInputStream ois=new ObjectInputStream(byteArray);
        ArchivosServer ret=(ArchivosServer)ois.readObject();
        ois.close();
        return ret;
    }
    public void actionPerformed(ActionEvent ae){
        String nombreIP=JOptionPane.showInputDialog("Ingresar la IP del servidor a donde enviar la peticion");
        try{
            InetAddress ipDestino=InetAddress.getByName(nombreIP);
            DatagramSocket sc=new DatagramSocket();
            int max=sc.getReceiveBufferSize();
            Mensaje men=new Mensaje("Ver archivos en la carpeta del servidor");
            byte[] bytes=men.enBytes();
            DatagramPacket pack=new DatagramPacket(bytes,bytes.length,ipDestino,5000);
            sc.send(pack);
            pack=new DatagramPacket(new byte[max],max);
            sc.receive(pack);
            ArchivosServer archivos=decodificar(pack.getData());
            sc.close();
            VentanaDeslizable ventana=new VentanaDeslizable(archivos);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Error en la conexion. Intente de nuevo.","Error",0);
        }
    }
}
