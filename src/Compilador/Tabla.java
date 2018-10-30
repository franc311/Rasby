package Compilador;
import java.util.Vector;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author  francisco
 * @date    22-may-2015
 */
public class Tabla {

    private Vector tablaSimbolos;
    private Vector tablaTipos;

    Tabla() {
        tablaSimbolos = new Vector();
        tablaTipos = new Vector();
        addTipo("int"); //por defecto solo tenemos int
    }
    
    void addSimbolo(String id) {
        tablaSimbolos.add(new Simbolo(countSimbolos(), id));
    }
    
    void addTipo(String id) {
        tablaTipos.add(new Tipo(countTipos(), id));
    }
    
    int countSimbolos() {
        return tablaSimbolos.size();
    }
    
    int countTipos() {
        return tablaTipos.size();
    }
    
    Simbolo getSimbolo(int pos) {
        return (Simbolo) tablaSimbolos.elementAt(pos);
    }
    
    Tipo getTipo(int pos) {
        return (Tipo) tablaTipos.elementAt(pos);
    }
    
    Simbolo getSimbolo(String id) {
        Simbolo simbolo = null;
        for (int i = 0; i < countSimbolos(); i++) {
            simbolo = getSimbolo(i);
            if (simbolo.getId().equals(id)) {
                break;
            } else {
                simbolo = null;
            }
        }
        return simbolo;
    }
    
    Tipo getTipo(String id) {
        Tipo tipo = null;
        for (int i = 0; i < countTipos(); i++) {
            tipo = getTipo(i);
            if (tipo.getId().equals(id)) {
                break;
            } else {
                tipo = null;
            }
        }
        return tipo;
    }
    
    boolean existeSimbolo(String id) {
        return getSimbolo(id) != null;
    }
    
    boolean existeTipo(String id) {
        return getTipo(id) != null;
    }
    
    void setSimbolo(Simbolo s) {
        int cod = s.getCod();
        tablaSimbolos.setElementAt(s, cod);
    }
    
    void setDireccionSimbolo(String id, int d) {
        Simbolo simbolo = getSimbolo(id);
        simbolo.setDireccion(d);
        setSimbolo(simbolo);
    }
    
    void setValorSimbolo(String id, int val) {
        Simbolo simbolo = getSimbolo(id);
        simbolo.setValor(val);
        setSimbolo(simbolo);
    }
    
    void imprimirTablaDinamica() {
        String cad = "Tablita dinamica >:B\n";
        Simbolo simbolo;
        Tipo tipo;
        for (int i = 0; i < tablaSimbolos.size(); i++) {
            simbolo = (Simbolo) tablaSimbolos.get(i);
            tipo = getTipo(simbolo.getId());
            cad +="Indice Código: " + simbolo.getCod()+ ", Tipo: int, Nombre:"+
                    simbolo.getId() +", CompLexico: "+ CompLex.checarComponenteLexico(simbolo.getId()) + ", Direccion: " + simbolo.getDireccion()+ 
                    ", Valor: "+ simbolo.getValor() +"\n";
        }
        JOptionPane.showMessageDialog(null, cad);
        System.out.print(cad);
    }
    public Vector getVector() {
        return tablaSimbolos;
    }
}
