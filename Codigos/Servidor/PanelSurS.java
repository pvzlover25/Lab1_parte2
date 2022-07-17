package Servidor;
import javax.swing.JPanel;
/**
 *
 * @author marag
 */
public class PanelSurS extends JPanel{
    public PanelSurS(PanelCentralS panel){
        this.setBackground(VentanaServer.FONDO);
        GuardarArchivo boton=new GuardarArchivo(panel);
        panel.setBoton(boton);
        this.add(boton);
    }
}
