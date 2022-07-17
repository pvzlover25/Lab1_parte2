package Cliente;
import Cliente.PanelDeslizable;
import javax.swing.JFrame;
import Paquetes.ArchivosServer;
import java.awt.BorderLayout;

public class VentanaDeslizable extends JFrame{
    private PanelDeslizable pdesliza;
    public VentanaDeslizable(ArchivosServer archivos){
        this.setTitle("Archivos del servidor");
        this.setLayout(new BorderLayout());
        pdesliza=new PanelDeslizable(archivos);
        this.add(pdesliza);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(100,300,300,300);
        this.setVisible(true);
    }
}
