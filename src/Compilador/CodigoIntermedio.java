package Compilador;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @date    24-may-2015
 */
public class CodigoIntermedio {
    String cad="";
    String nombreFichero;
    PrintWriter ficheroEscritura;
    BufferedReader ficheroLectura;
    Vector instrucciones;

    public CodigoIntermedio(String nombreFichero) {
        this.nombreFichero = nombreFichero;
        ficheroEscritura = null;
        ficheroLectura = null;
        crearInstrucciones();
    }
    //crear tabla para indicar las instrucciones que se deben escribir
    private void crearInstrucciones() {
        instrucciones = new Vector();
    }
    //Abrir fichero de lectura
    public void abrirFicheroLectura() throws IOException {
        ficheroLectura = new BufferedReader(new FileReader(nombreFichero));
    }
    //Cerrar fichero de lectura
    public void cerrarFicheroLectura() throws IOException {
        ficheroLectura.close();
    }
    //Abrir fichero de escritura
    public void abrirFicheroEscritura() throws IOException {
        ficheroEscritura = new PrintWriter(new BufferedWriter(new FileWriter(nombreFichero)));
    }
    //Cerrar fichero de escritura
    public void cerrarFicheroEscritura() {
        Cuadrupla cuadrupla;
        for (int i = 0; i < instrucciones.size(); i++) {
            cuadrupla = (Cuadrupla) instrucciones.elementAt(i);
            ficheroEscritura.println(cuadrupla.getNombre() + 
                    " " + cuadrupla.getOp1() + 
                    " " + cuadrupla.getOp2() + 
                    " " + cuadrupla.getRes());
            cad += cuadrupla.getNombre() + 
                    " " + cuadrupla.getOp1() + 
                    " " + cuadrupla.getOp2() + 
                    " " + cuadrupla.getRes() + "\n";
        }
        ficheroEscritura.close();
        //JOptionPane.showMessageDialog(null, cad);
    }
    //Guardar cuadrupla en el fichero abierto
    public void guardarCuadrupla(Cuadrupla cuadrupla) {
        instrucciones.addElement(cuadrupla);
    }
}