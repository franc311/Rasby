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
public class OutputLexico {
    String cad="";
    String nombreFichero;
    PrintWriter ficheroEscritura;
    BufferedReader ficheroLectura;
    Vector instrucciones;

    public OutputLexico(String nombreFichero) {
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
        Lexema lexema;
        ficheroEscritura.println("[R][C]\t\t\tLexema\t\t\tComponente Lexico");
        for (int i = 0; i < instrucciones.size(); i++) {
            lexema = (Lexema) instrucciones.get(i);
            ficheroEscritura.println(//lexema.lexema + " - " + lexema.componenteLexico
                    "["+lexema.renglon+"]["+lexema.columna+"]\t\t\t"+ lexema.lexema + "\t\t\t"+lexema.componenteLexico
            );
        }
        ficheroEscritura.close();
    }
    //Guardar cuadrupla en el fichero abierto
    public void guardarLexico(Lexema lexema) {
        instrucciones.addElement(lexema);
    }
}