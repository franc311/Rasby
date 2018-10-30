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
public class OutputEstatica {
    String cad="";
    String nombreFichero;
    PrintWriter ficheroEscritura;
    BufferedReader ficheroLectura;
    Vector instrucciones;

    public OutputEstatica(String nombreFichero) {
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
        ficheroEscritura.println("Tablita Estatica");
        ficheroEscritura.println();
        Simbolo simbolo;
        Lexema lexema;
        for (int i = 0; i < instrucciones.size(); i++) {
            lexema = (Lexema) instrucciones.get(i);
            if(!lexema.componenteLexico.equals("identificador"))
                ficheroEscritura.println("["+lexema.renglon+"]["+lexema.columna+"]\t\t\t"+ lexema.lexema + "\t\t\t"+lexema.componenteLexico);
        }
        ficheroEscritura.close();
    }
    
    public void guardarVector(Vector vector){
        instrucciones = vector;
    }
}