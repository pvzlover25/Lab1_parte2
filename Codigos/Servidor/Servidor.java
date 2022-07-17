package Servidor;
import javax.swing.JFileChooser;

public class Servidor {
    public static void main(String[] args){
        JFileChooser elegir=new JFileChooser();
        elegir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        while(elegir.showOpenDialog(null)==JFileChooser.CANCEL_OPTION){}
        String carpeta=elegir.getSelectedFile().getAbsolutePath();
        VentanaServer miventana=new VentanaServer(carpeta);
    }
}
