package Compilador;
import java.util.Vector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author  francisco
 * @date    27-jul-2015
 */
public class Lexicos {
    public static Vector lexemas = new Vector();
    
    public static void lex(Lexema lex) {
        lexemas.addElement(lex);
    }
    
    public static Vector getLexemas() {
        return lexemas;
    }
}
