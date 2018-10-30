/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gnd;

import java.io.File;

/**
 *
 * @author  francisco
 * @date    21-may-2015
 */
public class GndCup {

    public static void main(String[] args) {
        //Options for setting JavaCup
        String opciones[] = new String[5];
        opciones[0] = "-destdir";
        opciones[1] = "src\\Compilador" + File.separator;
        opciones[2] = "-parser";
        opciones[3] = "parser";
        opciones[4] = "src\\Compilador" + File.separator + "formato.cup";
        try {
            java_cup.Main.main(opciones);
        } catch(Exception e) {
            
        }
    }
}
