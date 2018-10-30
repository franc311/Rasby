package clases;


import java.awt.Color;
import java.util.regex.Matcher;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DocumentFilter;
import javax.swing.text.StyledDocument;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kleinz
 */
public class StyleParser extends DocumentFilter{
    protected RegularExpRules reg = new RegularExpRules();
    protected JComponent object;
    StyledDocument styledDocument;
    
    public StyleParser(JTextPane textpane, JScrollPane scroll, Color bgColor, Color fgColor){
       
        
        UIDefaults defaults = new UIDefaults(); // Object to modify background color to JTextPane
        defaults.put("TextPane[Enabled].backgroundPainter", bgColor); // enable background color to JTextPane adding a new property
        defaults.put("ScrollPane[Enabled].backgroundPainter",bgColor); // enable background color to JScrollPane adding a new property
        textpane.putClientProperty("Nimbus.Overrides", defaults); // Add the new property
        textpane.putClientProperty("Nimbus.Overrides.InheritDefaults", true); 
        LinePainter painter = new LinePainter(textpane); // HighLight current row class assigned to JTextPane
        painter.setColor(new Color(255,0,153,50));
        textpane.setBackground(bgColor);
        textpane.setForeground(fgColor);
     
        scroll.setBackground(bgColor);
        TextLineNumber row = new TextLineNumber(textpane);
        scroll.setViewportView(textpane);
        
        scroll.setRowHeaderView( row ); // numeration rows
        object = row;
       
        textpane.getDocument().putProperty(DefaultEditorKit.EndOfLineStringProperty, "\n"); // fix the painted problem by row.
       
    ((AbstractDocument) textpane.getDocument()).setDocumentFilter(new DocumentFilter(){   
       
        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attributeSet) throws BadLocationException {
            super.insertString(fb, offset, text, attributeSet);        
                handleTextChanged();       
        }
        @Override
        public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);       
                handleTextChanged();             
        }
        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attributeSet) throws BadLocationException {
            super.replace(fb, offset, length, text, attributeSet);      
                handleTextChanged();
        }
        /*
          Add a Thread to analize the inputs from user
        */
        private void handleTextChanged()
        {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    reg.styleDefault(textpane, 0, textpane.getText().length());
                    colorFunction(textpane);
                    //stringTextPane();
                
                }
            });
        
        }
        /*
          Scan each pattern saved in the ArrayList<Pattern> from RegularExpRules class and add each Style to each Word or Element in the language
        */

        });

    /**
     *
     * @return
     */
    
    
    }

    public String stringTextPane(JTextPane textpane){
        return textpane.getText();
    }
    public void colorFunction(JTextPane textpane){
            Matcher m1, m2, m3, m4, m5, m6;
            m1 = reg.buildPatterns().get(0).matcher(textpane.getText());
            m2 = reg.buildPatterns().get(1).matcher(textpane.getText());
            m3 = reg.buildPatterns().get(2).matcher(textpane.getText());
            m4 = reg.buildPatterns().get(3).matcher(textpane.getText());
            m5 = reg.buildPatterns().get(4).matcher(textpane.getText());
            m6 = reg.buildPatterns().get(5).matcher(textpane.getText());
            while(m1.find()){
                reg.styleKeywords(textpane, m1.start(), m1.end() - m1.start());
            }
            while(m2.find()){
                reg.styleFunctions(textpane, m2.start(), m2.end() - m2.start());
            }
            while(m3.find()){
                reg.styleOperators(textpane, m3.start(), m3.end() - m3.start());
            }
            while(m4.find()){
                reg.styleComments(textpane, m4.start(), m4.end() - m4.start());
            }
            while(m5.find()){
                reg.styleDigits(textpane, m5.start(), m5.end() - m5.start());
            }
            while(m6.find()){
                reg.styleStrings(textpane, m6.start(), m6.end() - m6.start());
            }
        
        }
        
   

}
    