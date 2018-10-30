package Compilador;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author  francisco
 * @date    22-may-2015
 */
public class Simbolo {

    private int cod;
    private String id;
    private int direccion;
    private int valor;
    
    Simbolo() {
        cod = -1;
        id = "";
    }
    
    Simbolo(int c, String i) {
        cod = c;
        id = i;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
    
    
}
