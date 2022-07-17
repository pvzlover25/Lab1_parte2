package Servidor;
import javax.swing.JButton;
import java.awt.event.*;

public class GuardarArchivo extends JButton implements ActionListener{
    private PanelCentralS panel;
    public GuardarArchivo(PanelCentralS panel) {
        super("Guardar archivo");
        this.addActionListener(this);
        this.panel=panel;
        this.setEnabled(false);
    }
    public void actionPerformed(ActionEvent ae){
        panel.iniciarTimer();
        this.setEnabled(false);
    }
}
