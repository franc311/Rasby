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
import principales.IDE;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;


public class BotonX extends JButton implements MouseListener {
 boolean flag = false;
 ShapeTabbedPane panel;
 PanelTab btc;
 IDE ide;
 int tipo;

 IDE id;
    public BotonX(ShapeTabbedPane pane,PanelTab btc,int op, JPanel panel4, JLabel l1,JLabel l2,JLabel l3,JLabel l4,JLabel l5, JTextField t1, JLabel ll1,JLabel ll2,JLabel ll3,JLabel ll4,JLabel ll5,JLabel ll6, JLabel ll7) {
     panel=pane;
     this.btc=btc;
     tipo=op;
        int size = 17;
        setPreferredSize(new Dimension(size, size));
        setToolTipText("Cerrar Pestaña");
        setUI(new BasicButtonUI());
        setContentAreaFilled(false);
        setFocusable(false);
        setBorder(BorderFactory.createEtchedBorder());
        setBorderPainted(false);
        addMouseListener(this);
        setRolloverEnabled(true);
        addActionListener(new ActionListener(){


   @Override
   public void actionPerformed(ActionEvent e) {
    int i = panel.indexOfTabComponent(BotonX.this.btc);
        
         if (i != -1) {
             panel.remove(i);
           if(panel.verificaComponentes()){
               panel4.setVisible(true);
               l1.setVisible(false);
               l2.setVisible(false);
               l3.setVisible(false);
               l4.setVisible(false);
               l5.setVisible(false);
               t1.setVisible(false);
               ll1.setVisible(false);
               ll2.setVisible(false);
               ll3.setVisible(false);
               ll4.setVisible(false);
               ll5.setVisible(false);
               ll6.setVisible(false);
               ll7.setVisible(false);
            }else{              
               panel4.setVisible(false);
               
           }
             
         }
   }
        });
    }
  
    public void updateUI() {
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        if(tipo==0){
         if (getModel().isPressed()) {
          g2.translate(1, 1);
         }
         g2.setStroke(new BasicStroke(3));
         g2.setColor(Color.BLACK);
         if (getModel().isRollover()) {
          g2.setColor(Color.RED);
         }
         g2.drawLine(5, 5, 12, 12);
         g2.drawLine(12, 6, 6, 12);
         g2.dispose();
        }else{
         ImageIcon icon = new ImageIcon(getClass().getResource("/icons/cross2.png"));
         g2.drawImage(icon.getImage(), 1,1 ,14,14,this);
         //
         g2.dispose();
        }
    }


 @Override
 public void mouseClicked(MouseEvent e) {
  // TODO Auto-generated method stub
  
 }


 @Override
 public void mouseEntered(MouseEvent e) {
  Component component = e.getComponent();
        if (component instanceof AbstractButton) {
            AbstractButton button = (AbstractButton) component;
            button.setBorderPainted(true);
        }
 }


 @Override
 public void mouseExited(MouseEvent e) {
  Component component = e.getComponent();
        if (component instanceof AbstractButton) {
            AbstractButton button = (AbstractButton) component;
            button.setBorderPainted(false);
        }
 }


 @Override
 public void mousePressed(MouseEvent e) {
  // TODO Auto-generated method stub
  
 }


 @Override
 public void mouseReleased(MouseEvent e) {
  // TODO Auto-generated method stub
  
 }
}
