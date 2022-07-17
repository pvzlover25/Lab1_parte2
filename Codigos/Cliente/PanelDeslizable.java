package Cliente;
import Paquetes.ArchivosServer;
import java.awt.BorderLayout;
import javax.swing.*;

/**
 *
 * @author marag
 */
public class PanelDeslizable extends JPanel{
    private JTextArea areatxt;
    private JScrollPane scroll;
    public PanelDeslizable(ArchivosServer archivos){
        this.setLayout(new BorderLayout());
        areatxt=new JTextArea();
        areatxt.setFont(VentanaCliente.ARIAL18);
        areatxt.setBackground(VentanaCliente.FONDO);
        areatxt.setEditable(false);
        for(String st:archivos) areatxt.append(st+'\n');
        scroll=new JScrollPane(areatxt,20,30);
        this.add(scroll);
    }
}
