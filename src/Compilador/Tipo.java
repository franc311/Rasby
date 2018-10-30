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
public class Tipo {

    private int cod;
    private String id;
    
    Tipo() {
        cod = -1;
        id = "";
    }
    
    Tipo(int cod, String id) {
        this.cod = cod;
        this.id = id;
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
    
}
