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
public class CTD {
    String cad="";
    String nombreFichero;
    PrintWriter ficheroEscritura;
    BufferedReader ficheroLectura;
    Vector instrucciones;

    public CTD(String nombreFichero) {
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
        String espacio = "\t\t";
        Tres tres;
            for (int i = 0; i < instrucciones.size(); i++) {
                tres = (Tres) instrucciones.elementAt(i);
                if(tres.tipo.equals("expresion")) {
                    ficheroEscritura.println(espacio +
                        tres.vT + 
                        "=" + tres.opn1 + 
                        " " + tres.opd + 
                        " " + tres.opn2);
                } else if(tres.tipo.equals("asignacion")) {
                    ficheroEscritura.println(espacio +
                        tres.vT + 
                        "=" + tres.opn1);
                } else if(tres.tipo.equals("condicion")) {
                    ficheroEscritura.println(espacio +
                        "if " + tres.opn1 + 
                        " " + tres.opd +
                        " " + tres.opn2 +
                        " goto " + tres.etiqueta);
                } else if(tres.tipo.equals("salto")) {
                    ficheroEscritura.println(espacio +
                        "goto " + tres.etiqueta);
                } else if(tres.tipo.equals("etiqueta")) {
                    ficheroEscritura.println(
                        tres.etiqueta + ":");
                } else if(tres.tipo.equals("parametro")) {
                    ficheroEscritura.println(espacio +
                        "param " + tres.etiqueta);
                } else if(tres.tipo.equals("texto")) {
                    ficheroEscritura.println(espacio +
                        tres.vT + 
                        "=" + tres.opn1);
                } else if(tres.tipo.equals("printN")) {
                    ficheroEscritura.println(espacio +
                        "call " + tres.etiqueta + 
                        ", 1");
                } else if(tres.tipo.equals("printS")) {
                    ficheroEscritura.println(espacio +
                        "call " + tres.etiqueta + 
                        ", 1");
                } else if(tres.tipo.equals("painter")) {
                    ficheroEscritura.println(espacio +
                        "call " + tres.etiqueta + 
                        ", 1");
                } else if(tres.tipo.equals("pinWriter")) {
                    ficheroEscritura.println(espacio +
                        "call " + tres.etiqueta + 
                        ", 2");
                } else if(tres.tipo.equals("timer")) {
                    ficheroEscritura.println(espacio +
                        "call " + tres.etiqueta + 
                        ", 1");
                }
                
            }
        ficheroEscritura.close();
    }
    //Guardar cuadrupla en el fichero abierto
    public void guardarCTD(Tres tres) {
        instrucciones.addElement(tres);
    }
}