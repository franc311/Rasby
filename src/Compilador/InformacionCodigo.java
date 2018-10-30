package Compilador;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author  francisco
 * @date    21-may-2015
 */
public class InformacionCodigo {

    public static int linea;
    public static String token;
    public static void guardarInformacionCodigo(int l, String t) {
        linea = l;
        token = t;
    }
}
