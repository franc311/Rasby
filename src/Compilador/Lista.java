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
 * @date    25-may-2015
 */
public class Lista {

    Vector lista;

    public Lista() {
        lista = new Vector();
    }
    
    void addCadena(String c) {
        lista.addElement(c);
    }
    
    String getCadena(int i) {
        return (String) lista.elementAt(i);
    }
    
    int size() {
        return lista.size();
    }
}
