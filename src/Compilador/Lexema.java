package Compilador;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Francisco
 * @date    25-jul-2015
 */
public class Lexema {
    String lexema;
    String componenteLexico;
    String tipoLexema;
    String descripcion;
    String renglon;
    String columna;

    public Lexema(String lexema, String componente, String tipoLexema) {
        this.lexema = lexema;
        this.componenteLexico = componente;
        this.tipoLexema = tipoLexema;
    }
    //construir lexemas para la output lexica
    public Lexema(String lexema, String componenteLexico, String tipoLexema, String descripcion, String renglon, String columna) {
        this.lexema = lexema;
        this.componenteLexico = componenteLexico;
        this.tipoLexema = tipoLexema;
        this.descripcion = descripcion;
        this.renglon = renglon;
        this.columna = columna;
    }
}
