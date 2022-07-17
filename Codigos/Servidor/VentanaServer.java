package Servidor;
import java.awt.*;
import javax.swing.*;

public class VentanaServer extends JFrame{
    public static final Color FONDO=new Color(255,252,183);
    public static final Font ARIAL=new Font("Arial", Font.BOLD, 20);
    public static final Font ARIAL18=ARIAL.deriveFont(18f);
    private PanelCentralS pcentral;
    private PanelSurS psur;
    public VentanaServer(String carpetaServer){
        this.setTitle("Servidor");
        this.setLayout(new BorderLayout());
        pcentral=new PanelCentralS(carpetaServer);
        this.add(pcentral,BorderLayout.CENTER);
        psur=new PanelSurS(pcentral);
        this.add(psur,BorderLayout.SOUTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100,300,740,380);
        this.setVisible(true);
    }
}
