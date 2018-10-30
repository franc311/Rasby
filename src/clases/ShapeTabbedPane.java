package clases;


import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import clases.BotonX;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
/**
 * @web http://www.jc-mouse.net
 * @author Mouse
 */
public class ShapeTabbedPane extends JTabbedPane {
    
    boolean vacio = false;
    public ShapeTabbedPane()
    {
    
        this.setPreferredSize( new Dimension(100,100) );        
        this.setUI( new shapeTabbedPaneUI() );
        this.setForeground(java.awt.Color.WHITE);
        this.setVisible(true);
        this.setBorder(BorderFactory.createEmptyBorder());
        

        
        
    }
    public boolean verificaComponentes(){
        if(getTabCount() == 0){
            vacio=true;
        }else{
            vacio = false;
        }
        return vacio;
    }
    
    
}
