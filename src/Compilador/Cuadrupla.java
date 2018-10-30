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
public class Cuadrupla {
    String nombre;
    String op1;
    String op2;
    String res;

    public Cuadrupla(String nombre, String op1, String op2, String res) {
        this.nombre = nombre;
        this.op1 = op1;
        this.op2 = op2;
        this.res = res;
    }

    public String getNombre() {
        return nombre;
    }

    public String getOp1() {
        return op1;
    }

    public String getOp2() {
        return op2;
    }

    public String getRes() {
        return res;
    }
}