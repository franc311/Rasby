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
public class GndFlex {

    public static void main(String[] args) {
        jflex.Main.generate(new File("src\\Compilador" + File.separator + "formato.jlex"));
    }
}
