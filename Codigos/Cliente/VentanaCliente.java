package Cliente;
import java.awt.*;
import javax.swing.*;

public class VentanaCliente extends JFrame{
    public static final Color FONDO=new Color(255,252,183);
    public static final Font ARIAL=new Font("Arial", Font.BOLD, 20);
    public static final Font ARIAL18=ARIAL.deriveFont(18f);
    private PanelCentralC pcentral;
    private PanelSurC psur;
    public VentanaCliente(){
        this.setLayout(new BorderLayout());
        pcentral=new PanelCentralC();
        this.add(pcentral,BorderLayout.CENTER);
        psur=new PanelSurC(pcentral);
        this.add(psur,BorderLayout.SOUTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100,300,740,380);
        this.setTitle("Cliente");
        this.setVisible(true);
    }
}
