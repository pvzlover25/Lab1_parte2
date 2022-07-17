package Cliente;
import javax.swing.JPanel;

public class PanelSurC extends JPanel{
    public PanelSurC(PanelCentralC panel){
        this.setBackground(VentanaCliente.FONDO);
        this.add(new CargarArchivo(panel));
        this.add(new PedirArchivosServer());
        this.add(new PedirArchivo(panel));
    }
}
