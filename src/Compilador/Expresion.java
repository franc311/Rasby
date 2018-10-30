package Compilador;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author  francisco
 * @date    24-may-2015
 */
public class Expresion {
    String valorTemporal, arg1, arg2, op="";
    
    
    int resultado = 0;
    String valor = "";
    int direccion;
    String etqVerdad, etqFalso;

    public Expresion(int direccion) {
        this.direccion = direccion;
    }
    public Expresion(int direccion, String etVerdad, String etFalsa) {
        this.direccion = direccion;
        this.etqVerdad = etVerdad;
        this.etqFalso = etFalsa;
    }
    public Expresion(int direccion, String valor) {
        this.direccion = direccion;
        this.valor = valor;
    }
    public Expresion(int direccion, String valor, int resultado) {
        this.direccion = direccion;
        this.resultado = resultado;
        this.valor = valor;
    }

    public int getDireccion() {
        return direccion;
    }
    public String getValor() {
        return valor;
    }
    public int getResultado() {
        return resultado;
    }
    
}
