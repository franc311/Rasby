/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author Anna
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;


public class PanelTab extends JPanel {
  
 ShapeTabbedPane pestañas;
 int tipo;
 public boolean compilable = false, leido = false;


    public PanelTab(ShapeTabbedPane p,int op, JPanel panel4, JLabel l1,JLabel l2,JLabel l3,JLabel l4,JLabel l5, JTextField t1, boolean comp, boolean le, JLabel ll1,JLabel ll2,JLabel ll3,JLabel ll4,JLabel ll5,JLabel ll6, JLabel ll7) {
     compilable = comp;
     leido = le;
        //if (p != null) {
         
         this.pestañas = p;
         tipo=op;
         
            setOpaque(false);
          
            JLabel titulo = new JLabel() {
             
                public String getText() {
                    int i = pestañas.indexOfTabComponent(PanelTab.this);
                    if (i != -1) {
                        return pestañas.getTitleAt(i);
                    }
                    return null;
                }
            };
            titulo.setForeground(Color.WHITE);
            String titulo2 = titulo.getText() + ".c";
            add(titulo);
            titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
            JButton button = new BotonX(pestañas,this,1, panel4, l1, l2, l3, l4, l5, t1,ll1,ll2,ll3,ll4,ll5,ll6,ll7);
            add(button);
        //}
    }
}

