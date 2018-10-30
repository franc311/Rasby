/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.plaf.nimbus.AbstractRegionPainter;

/**
 *
 * @author Kleinz
 */
public class MyPaint extends AbstractRegionPainter{
     private Color fillColor;

    public MyPaint(Color color) {
        fillColor = new Color(
                color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    @Override
    public void doPaint(Graphics2D g, JComponent c, int width,
                        int height, Object[] extendedCacheKeys) {
        g.setColor(fillColor);
        g.fillRect(3, 3, width - 6, height - 6);
    }

    @Override
    public PaintContext getPaintContext() {
        return null;
    }
}
