package Compilador;
import java.util.Vector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Francisco
 */
public class CompLex {
    public static Vector cl = new Vector();
    
    public static boolean existeLexema(String lexema) {
        boolean retorno = false;
        Lexema ic;
        for (int i = 0; i < cl.size(); i++) {
            ic = (Lexema) cl.get(i);
            if (ic.lexema.equals(lexema)) {
                retorno = true;
            }
        }
        return retorno;
    }
    
    public static void guardarLexema(Lexema ic) {
        cl.addElement(ic);
    }
    public static String checarComponenteLexico(String lexema) {
        Lexema ic;
        for (int i = 0; i < cl.size(); i++) {
            ic = (Lexema) cl.get(i);
            if (ic.lexema.equals(lexema)) {
                lexema = ic.componenteLexico;
            }
        }
        return lexema;
    }
}
