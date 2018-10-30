package Compilador;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author  francisco
 * @date    23-jul-2015
 */
public class Tres {
    
    public int direccion, resultado;
    public String etiqueta, tipo, vT, opn1, opn2, opd = "";
    //salto o etiqueta
    public Tres(String etiqueta, String tipo) {
        this.etiqueta = etiqueta;
        this.tipo = tipo;
    }
    //asignacion unaria
    public Tres(String vT, String opn1,int resultado, String tipo) {
        this.vT = vT;
        this.opn1 = opn1;
        this.resultado = resultado;
        this.tipo = tipo;
    }
    //asignacion binaria
    public Tres(String vT, String opn1, String opd, String opn2, int resultado, String tipo) {
        this.vT = vT;
        this.opn1 = opn1;
        this.opd = opd;
        this.opn2 = opn2;
        this.resultado = resultado;
        this.tipo = tipo;
    }
    //condicion
    public Tres(String opn1, String opd, String opn2, String etiqueta, String tipo) {
        this.opn1 = opn1;
        this.opd = opd;
        this.opn2 = opn2;
        this.etiqueta = etiqueta;
        this.tipo = tipo;
    }
}
