package Compilador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author francisco
 * @date 26-may-2015
 */
public class CodigoFinal {

    private CodigoIntermedio codigoIntermedio = null;
    private String ficheroCF;
    private PrintWriter fichero;
    private int loopDividir;  //contador para loops internos de la division
    private int loopTemporizador;  //contador para loops internos del timer

    public CodigoFinal(CodigoIntermedio ci, String nombrePrograma) {
        codigoIntermedio = ci;
        String nombre = nombrePrograma.substring(0, nombrePrograma.lastIndexOf("."));
        ficheroCF = nombre.concat(".ens");
        loopDividir = 0;
        loopTemporizador = 0;
    }

    //Abrir fichero para guardar
    public void abrirFichero() throws IOException {
        fichero = new PrintWriter(new BufferedWriter(new FileWriter(ficheroCF)));
    }

    //Cerrar fichero
    private void cerrarFichero() {
        fichero.close();
    }

    //Escribir en el fichero
    private void escribirLinea(String linea) {
        fichero.println(linea);
    }

    //Escribir salto de linea...
    private void escribirBlanco() {
        fichero.println("");
    }

    //Traducir CI -> CF
    public void traducirCodigo() throws IOException {
        Cuadrupla cuadrupla;
        abrirFichero();
        for (int i = 0; i < codigoIntermedio.instrucciones.size(); i++) {
            cuadrupla = (Cuadrupla) codigoIntermedio.instrucciones.elementAt(i);
            procesarCuadrupla(cuadrupla);
        }
        cerrarFichero();
    }

    //Procesar cuadruplas
    private void procesarCuadrupla(Cuadrupla cuadrupla) {
        String op1, op2, inst, res;
        String linea = "            ";
        String amv1 = "BL   av1";
        String cmv1 = "BL   cv1";
        String amv2 = "BL   av2";
        String cmv2 = "BL   cv2";
        op1 = cuadrupla.op1;
        op2 = cuadrupla.op2;
        inst = cuadrupla.nombre;
        res = cuadrupla.res;
        if (inst.equals("PONER_HEADER")) {
            escribirLinea(linea + ".extern printf");
            escribirLinea(linea + ".global main");
            escribirLinea("main:");
        }

        if (inst.equals("CARGAR_DIRECCION")) {
            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "LDR  R1, [R5, #" + op1 + "]");
            escribirLinea(linea + "STR  R1, [R5, #" + res + "]");
            escribirLinea(linea + cmv2);
        }

        if (inst.equals("CARGAR_VALOR")) {
            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "MOV  R1, #" + op1 + "");
            escribirLinea(linea + "STR  R1, [R5, #" + res + "]");
            escribirLinea(linea + cmv2);
        }

        if (inst.equals("SUMAR")) {
            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "LDR  R1, [R5, #" + op1 + "]");
            escribirLinea(linea + "LDR  R2, [R5, #" + op2 + "]");
            escribirLinea(linea + "ADD  R3, R1, R2");
            escribirLinea(linea + "STR  R3, [R5, #" + res + "]");
            escribirLinea(linea + cmv2);
        }

        if (inst.equals("RESTA")) {
            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "LDR  R1, [R5, #" + op1 + "]");
            escribirLinea(linea + "LDR  R2, [R5, #" + op2 + "]");
            escribirLinea(linea + "SUB  R3, R1, R2");
            escribirLinea(linea + "STR  R3, [R5, #" + res + "]");
            escribirLinea(linea + cmv2);
        }

        if (inst.equals("MULTIPLICAR")) {
            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "LDR  R1, [R5, #" + op1 + "]");
            escribirLinea(linea + "LDR  R2, [R5, #" + op2 + "]");
            escribirLinea(linea + "MUL  R3, R1, R2");
            escribirLinea(linea + "STR  R3, [R5, #" + res + "]");
            escribirLinea(linea + cmv2);
        }

        if (inst.equals("DIVIDIR")) {
            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "LDR  R1, [R5, #" + op1 + "]");
            escribirLinea(linea + "LDR  R2, [R5, #" + op2 + "]");
            escribirLinea(linea + "MOV  R4, R2");
            escribirLinea(linea + "CMP  R4, R1, LSR #1");
            escribirLinea("LOOP_DIV" + (++loopDividir) + ":");
            escribirLinea(linea + "MOVLS    R4, R4, LSL #1");
            escribirLinea(linea + "CMP  R4, R1, LSL #1");
            escribirLinea(linea + "BLS  LOOP_DIV" + loopDividir + "");
            escribirLinea(linea + "MOV  R3, #0");
            escribirLinea("LOOP_DIV" + (++loopDividir) + ":");
            escribirLinea(linea + "CMP  R1, R4");
            escribirLinea(linea + "SUBHS    R1, R1, R4");
            escribirLinea(linea + "ADC  R3, R3, R3");
            escribirLinea(linea + "MOV  R4, R4, LSR #1");
            escribirLinea(linea + "CMP  R4, R2");
            escribirLinea(linea + "BHS  LOOP_DIV" + loopDividir + "");
            escribirLinea(linea + "STR  R3, [R5, #" + res + "]");
            escribirLinea(linea + cmv2);
        }

        if (inst.equals("OR")) {
            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "LDR  R1, [R5, #" + op1 + "]");
            escribirLinea(linea + "LDR  R2, [R5, #" + op2 + "]");
            escribirLinea(linea + "ORR  R3, R1, R2");
            escribirLinea(linea + "STR  R3, [R5, #" + res + "]");
            escribirLinea(linea + cmv2);
        }

        if (inst.equals("AND")) {
            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "LDR  R1, [R5, #" + op1 + "]");
            escribirLinea(linea + "LDR  R2, [R5, #" + op2 + "]");
            escribirLinea(linea + "AND  R3, R1, R2");
            escribirLinea(linea + "STR  R3, [R5, #" + res + "]");
            escribirLinea(linea + cmv2);
        }

        if (inst.equals("MENOR")) {
            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "LDR  R1, [R5, #" + op1 + "]");
            escribirLinea(linea + "LDR  R2, [R5, #" + op2 + "]");
            escribirLinea(linea + "CMP  R1, R2");
            escribirLinea(linea + "MOVPL    R3, #0");
            escribirLinea(linea + "MOVMI    R3, #1");
            escribirLinea(linea + "MOVEQ    R3, #0");
            escribirLinea(linea + "STR  R3, [R5, #" + res + "]");
            escribirLinea(linea + cmv2);
        }

        if (inst.equals("MENORI")) {
            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "LDR  R1, [R5, #" + op1 + "]");
            escribirLinea(linea + "LDR  R2, [R5, #" + op2 + "]");
            escribirLinea(linea + "CMP  R1, R2");
            escribirLinea(linea + "MOVPL    R3, #0");
            escribirLinea(linea + "MOVMI    R3, #1");
            escribirLinea(linea + "MOVEQ    R3, #1");
            escribirLinea(linea + "STR  R3, [R5, #" + res + "]");
            escribirLinea(linea + cmv2);
        }

        if (inst.equals("MAYOR")) {
            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "LDR  R1, [R5, #" + op1 + "]");
            escribirLinea(linea + "LDR  R2, [R5, #" + op2 + "]");
            escribirLinea(linea + "CMP  R2, R1");
            escribirLinea(linea + "MOVPL    R3, #0");
            escribirLinea(linea + "MOVMI    R3, #1");
            escribirLinea(linea + "MOVEQ    R3, #0");
            escribirLinea(linea + "STR  R3, [R5, #" + res + "]");
            escribirLinea(linea + cmv2);
        }

        if (inst.equals("MAYORI")) {
            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "LDR  R1, [R5, #" + op1 + "]");
            escribirLinea(linea + "LDR  R2, [R5, #" + op2 + "]");
            escribirLinea(linea + "CMP  R2, R1");
            escribirLinea(linea + "MOVPL    R3, #0");
            escribirLinea(linea + "MOVMI    R3, #1");
            escribirLinea(linea + "MOVEQ    R3, #1");
            escribirLinea(linea + "STR  R3, [R5, #" + res + "]");
            escribirLinea(linea + cmv2);
        }

        if (inst.equals("IGUAL")) {
            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "LDR  R1, [R5, #" + op1 + "]");
            escribirLinea(linea + "LDR  R2, [R5, #" + op2 + "]");
            escribirLinea(linea + "CMP  R1, R2");
            escribirLinea(linea + "MOVEQ    R3, #1");
            escribirLinea(linea + "MOVNE    R3, #0");
            escribirLinea(linea + "STR  R3, [R5, #" + res + "]");
            escribirLinea(linea + cmv2);
        }

        if (inst.equals("DISTINTO")) {
            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "LDR  R1, [R5, #" + op1 + "]");
            escribirLinea(linea + "LDR  R2, [R5, #" + op2 + "]");
            escribirLinea(linea + "CMP  R1, R2");
            escribirLinea(linea + "MOVEQ    R3, #0");
            escribirLinea(linea + "MOVNE    R3, #1");
            escribirLinea(linea + "STR  R3, [R5, #" + res + "]");
            escribirLinea(linea + cmv2);
        }

        if (inst.equals("ETIQUETA")) {
            escribirLinea(res + ":");
        }

        if (inst.equals("SALTAR_CONDICION")) {
            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "LDR  R1, [R5, #" + op1 + "]");
            escribirLinea(linea + "CMP  R1, #0");
            escribirLinea(linea + "BEQ  " + res);
            escribirLinea(linea + cmv2);
        }

        if (inst.equals("SALTAR_ETIQUETA")) {
            escribirLinea(linea + "B    " + res);
        }

        if (inst.equals("IMPRIMIR_ENTERO")) {
            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "LDR  R0, [R5, #" + op1 + "]");
            escribirLinea(linea + "MOV  R1, #0");
            escribirLinea(linea + "MOV  R1, R0");
            escribirLinea(linea + "LDR  R0, =imprimir_entero");
            escribirLinea(linea + "BL   printf");
            escribirLinea(linea + "MOV  R0, #0");
            escribirLinea(linea + cmv2);
        }

        if (inst.equals("IMPRIMIR_CADENA")) {
            escribirLinea(linea + "LDR  R0, =" + op1);
            escribirLinea(linea + "BL   printf");
            escribirLinea(linea + "MOV  R0, #0");
        }

        if (inst.equals("PONER_ENTERO")) {
            escribirLinea("imprimir_entero:");
            escribirLinea(linea + ".ascii \"%d\\n\\0\"");
            escribirLinea(linea + ".align 4");
        }

        if (inst.equals("PONER_CADENA")) {
            //res = res.replaceAll("\"", "");
            res = res.substring(0, res.length() - 1);
            res = res.substring(1, res.length());
            escribirLinea(op1 + ":");
            escribirLinea(linea + ".ascii \"" + res + "\\n\\0\"");
            escribirLinea(linea + ".align 4");
        }

        if (inst.equals("TIMER")) {
            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "LDR  R3, [R5, #" + op1 + "]");
            escribirLinea(linea + cmv2);
            escribirLinea("loop_timer_" + (++loopTemporizador) + ":");
            escribirLinea(linea + "MOV  R2, #0x11000000");
            escribirLinea("loop_timer_" + (--loopTemporizador) + ":");
            escribirLinea(linea + "SUB  R2, #1");
            escribirLinea(linea + "CMP  R2, #0");
            escribirLinea(linea + "BNE  loop_timer_" + loopTemporizador++);
            escribirLinea(linea + "SUB  R3, #1");
            escribirLinea(linea + "CMP  R3, #0");
            escribirLinea(linea + "BNE  loop_timer_" + (loopTemporizador++));
        }

        if (inst.equals("FIN")) {
            escribirLinea("salir:");
            escribirLinea(linea + "MOV  R7, #1");
            escribirLinea(linea + "SWI  0");
        }

        if (inst.equals("BASE")) {
            escribirLinea("checar_pin:");
            escribirLinea(linea + "CMP  R1, #2");
            escribirLinea(linea + "BEQ  pin_2");
            escribirLinea(linea + "CMP  R1, #3");
            escribirLinea(linea + "BEQ  pin_3");
            escribirLinea(linea + "CMP  R1, #4");
            escribirLinea(linea + "BEQ  pin_4");
            escribirLinea(linea + "CMP  R1, #5");
            escribirLinea(linea + "BEQ  pin_5");
            escribirLinea(linea + "CMP  R1, #6");
            escribirLinea(linea + "BEQ  pin_6");
            escribirLinea(linea + "CMP  R1, #7");
            escribirLinea(linea + "BEQ  pin_7");
            escribirLinea(linea + "CMP  R1, #8");
            escribirLinea(linea + "BEQ  pin_8");
            escribirLinea(linea + "CMP  R1, #9");
            escribirLinea(linea + "BEQ  pin_9");
            escribirLinea(linea + "CMP  R1, #10");
            escribirLinea(linea + "BEQ  pin_10");
            escribirLinea(linea + "CMP  R1, #11");
            escribirLinea(linea + "BEQ  pin_11");
            escribirLinea(linea + "CMP  R1, #12");
            escribirLinea(linea + "BEQ  pin_12");
            escribirLinea(linea + "CMP  R1, #13");
            escribirLinea(linea + "BEQ  pin_13");
            escribirLinea(linea + "CMP  R1, #14");
            escribirLinea(linea + "BEQ  pin_14");
            escribirLinea(linea + "CMP  R1, #15");
            escribirLinea(linea + "BEQ  pin_15");
            escribirLinea(linea + "CMP  R1, #16");
            escribirLinea(linea + "BEQ  pin_16");
            escribirLinea(linea + "CMP  R1, #17");
            escribirLinea(linea + "BEQ  pin_17");
            escribirLinea(linea + "CMP  R1, #18");
            escribirLinea(linea + "BEQ  pin_18");
            escribirLinea(linea + "CMP  R1, #19");
            escribirLinea(linea + "BEQ  pin_19");
            escribirLinea(linea + "CMP  R1, #20");
            escribirLinea(linea + "BEQ  pin_20");
            escribirLinea(linea + "CMP  R1, #21");
            escribirLinea(linea + "BEQ  pin_21");
            escribirLinea(linea + "CMP  R1, #22");
            escribirLinea(linea + "BEQ  pin_22");
            escribirLinea(linea + "CMP  R1, #23");
            escribirLinea(linea + "BEQ  pin_23");
            escribirLinea(linea + "CMP  R1, #24");
            escribirLinea(linea + "BEQ  pin_24");
            escribirLinea(linea + "CMP  R1, #25");
            escribirLinea(linea + "BEQ  pin_25");
            escribirLinea(linea + "CMP  R1, #26");
            escribirLinea(linea + "BEQ  pin_26");
            escribirLinea(linea + "CMP  R1, #27");
            escribirLinea(linea + "BEQ  pin_27");
            escribirLinea(linea + "MOV  R1, #0");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_2:");
            escribirLinea(linea + "MOV  R6, #0");
            escribirLinea(linea + "MOV  R7, #0b111<<6");
            escribirLinea(linea + "MOV  R8, #1<<6");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#2");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_3:");
            escribirLinea(linea + "MOV  R6, #0");
            escribirLinea(linea + "MOV  R7, #0b111<<9");
            escribirLinea(linea + "MOV  R8, #1<<9");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#3");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_4:");
            escribirLinea(linea + "MOV  R6, #0");
            escribirLinea(linea + "MOV  R7, #0b111<<12");
            escribirLinea(linea + "MOV  R8, #1<<12");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#4");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_5:");
            escribirLinea(linea + "MOV  R6, #0");
            escribirLinea(linea + "MOV  R7, #0b111<<15");
            escribirLinea(linea + "MOV  R8, #1<<15");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#5");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_6:");
            escribirLinea(linea + "MOV  R6, #0");
            escribirLinea(linea + "MOV  R7, #0b111<<18");
            escribirLinea(linea + "MOV  R8, #1<<18");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#6");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_7:");
            escribirLinea(linea + "MOV  R6, #0");
            escribirLinea(linea + "MOV  R7, #0b111<<21");
            escribirLinea(linea + "MOV  R8, #1<<21");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#7");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_8:");
            escribirLinea(linea + "MOV  R6, #0");
            escribirLinea(linea + "MOV  R7, #0b111<<24");
            escribirLinea(linea + "MOV  R8, #1<<24");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#8");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_9:");
            escribirLinea(linea + "MOV  R6, #0");
            escribirLinea(linea + "MOV  R7, #0b111<<27");
            escribirLinea(linea + "MOV  R8, #1<<27");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#9");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_10:");
            escribirLinea(linea + "MOV  R6, #4");
            escribirLinea(linea + "MOV  R7, #0b111<<0");
            escribirLinea(linea + "MOV  R8, #1<<0");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#10");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_11:");
            escribirLinea(linea + "MOV  R6, #4");
            escribirLinea(linea + "MOV  R7, #0b111<<3");
            escribirLinea(linea + "MOV  R8, #1<<3");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#11");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_12:");
            escribirLinea(linea + "MOV  R6, #4");
            escribirLinea(linea + "MOV  R7, #0b111<<6");
            escribirLinea(linea + "MOV  R8, #1<<6");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#12");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_13:");
            escribirLinea(linea + "MOV  R6, #4");
            escribirLinea(linea + "MOV  R7, #0b111<<9");
            escribirLinea(linea + "MOV  R8, #1<<9");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#13");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_14:");
            escribirLinea(linea + "MOV  R6, #4");
            escribirLinea(linea + "MOV  R7, #0b111<<12");
            escribirLinea(linea + "MOV  R8, #1<<12");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#14");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_15:");
            escribirLinea(linea + "MOV  R6, #4");
            escribirLinea(linea + "MOV  R7, #0b111<<15");
            escribirLinea(linea + "MOV  R8, #1<<15");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#15");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_16:");
            escribirLinea(linea + "MOV  R6, #4");
            escribirLinea(linea + "MOV  R7, #0b111<<18");
            escribirLinea(linea + "MOV  R8, #1<<18");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#16");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_17:");
            escribirLinea(linea + "MOV  R6, #4");
            escribirLinea(linea + "MOV  R7, #0b111<<21");
            escribirLinea(linea + "MOV  R8, #1<<21");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#17");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_18:");
            escribirLinea(linea + "MOV  R6, #4");
            escribirLinea(linea + "MOV  R7, #0b111<<24");
            escribirLinea(linea + "MOV  R8, #1<<24");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#18");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_19:");
            escribirLinea(linea + "MOV  R6, #4");
            escribirLinea(linea + "MOV  R7, #0b111<<27");
            escribirLinea(linea + "MOV  R8, #1<<27");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#19");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_20:");
            escribirLinea(linea + "MOV  R6, #8");
            escribirLinea(linea + "MOV  R7, #0b111<<0");
            escribirLinea(linea + "MOV  R8, #1<<0");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#20");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_21:");
            escribirLinea(linea + "MOV  R6, #8");
            escribirLinea(linea + "MOV  R7, #0b111<<3");
            escribirLinea(linea + "MOV  R8, #1<<3");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#21");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_22:");
            escribirLinea(linea + "MOV  R6, #8");
            escribirLinea(linea + "MOV  R7, #0b111<<6");
            escribirLinea(linea + "MOV  R8, #1<<6");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#22");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_23:");
            escribirLinea(linea + "MOV  R6, #8");
            escribirLinea(linea + "MOV  R7, #0b111<<9");
            escribirLinea(linea + "MOV  R8, #1<<9");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#23");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_24:");
            escribirLinea(linea + "MOV  R6, #8");
            escribirLinea(linea + "MOV  R7, #0b111<<12");
            escribirLinea(linea + "MOV  R8, #1<<12");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#24");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_25:");
            escribirLinea(linea + "MOV  R6, #8");
            escribirLinea(linea + "MOV  R7, #0b111<<15");
            escribirLinea(linea + "MOV  R8, #1<<15");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#25");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_26:");
            escribirLinea(linea + "MOV  R6, #8");
            escribirLinea(linea + "MOV  R7, #0b111<<18");
            escribirLinea(linea + "MOV  R8, #1<<18");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#26");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_27:");
            escribirLinea(linea + "MOV  R6, #8");
            escribirLinea(linea + "MOV  R7, #0b111<<21");
            escribirLinea(linea + "MOV  R8, #1<<21");
            escribirLinea(linea + "MOV  R2, #1");
            escribirLinea(linea + "MOV  R10, R2, LSL#27");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("checar_pin_estado:");
            escribirLinea(linea + "CMP  R1, #1");
            escribirLinea(linea + "BEQ  pin_on");
            escribirLinea(linea + "CMP  R1, #0");
            escribirLinea(linea + "BEQ  pin_off");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("pin_on:");
            escribirLinea(linea + "MOV  R9, #28");
            escribirLinea(linea + "MOV  PC, LR");
            escribirLinea("pin_off:");
            escribirLinea(linea + "MOV  R9, #40");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("av1:");
            escribirLinea(linea + "SUB  SP, SP, #32");
            escribirLinea(linea + "STR  LR, [SP, #24]");
            escribirLinea(linea + "LDR  R0, .addr_file");
            escribirLinea(linea + "LDR  R1, .flags");
            escribirLinea(linea + "BL   open");
            escribirLinea(linea + "STR  R0, [SP, #12]");
            escribirLinea(linea + "LDR  R3, [SP, #12]");
            escribirLinea(linea + "STR  R3, [SP, #0]");
            escribirLinea(linea + "LDR  R3, .gpiobase");
            escribirLinea(linea + "STR  R3, [SP, #4]");
            escribirLinea(linea + "MOV  R0, #0");
            escribirLinea(linea + "MOV  R1, #4096");
            escribirLinea(linea + "MOV  R2, #3");
            escribirLinea(linea + "MOV  R3, #1");
            escribirLinea(linea + "BL   mmap");
            escribirLinea(linea + "STR  R0, [SP, #16]");
            escribirLinea(linea + "LDR  LR, [SP, #24]");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("cv1:");
            escribirLinea(linea + "STR  LR, [SP, #24]");
            escribirLinea(linea + "LDR  R0, [SP, #12]");
            escribirLinea(linea + "BL   close");
            escribirLinea(linea + "LDR  LR, [SP, #24]");
            escribirLinea(linea + "ADD  SP, SP, #32");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("av2:");
            escribirLinea(linea + "SUB  SP, SP, #32");
            escribirLinea(linea + "STR  LR, [SP, #24]");
            escribirLinea(linea + "LDR  R0, .addr_file");
            escribirLinea(linea + "LDR  R1, .flags");
            escribirLinea(linea + "BL   open");
            escribirLinea(linea + "STR  R0, [SP, #12]");
            escribirLinea(linea + "LDR  R3, [SP, #12]");
            escribirLinea(linea + "STR  R3, [SP, #0]");
            escribirLinea(linea + "LDR  R3, .base");
            escribirLinea(linea + "STR  R3, [SP, #4]");
            escribirLinea(linea + "MOV  R0, #0");
            escribirLinea(linea + "MOV  R1, #4096");
            escribirLinea(linea + "MOV  R2, #3");
            escribirLinea(linea + "MOV  R3, #1");
            escribirLinea(linea + "BL   mmap");
            escribirLinea(linea + "STR  R0, [SP, #20]");
            escribirLinea(linea + "LDR  LR, [SP, #24]");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea("cv2:");
            escribirLinea(linea + "STR  LR, [SP, #24]");
            escribirLinea(linea + "LDR  R0, [SP, #12]");
            escribirLinea(linea + "BL   close");
            escribirLinea(linea + "LDR  LR, [SP, #24]");
            escribirLinea(linea + "ADD  SP, SP, #32");
            escribirLinea(linea + "MOV  PC, LR");

            escribirLinea(".addr_file:  .word   .file");
            escribirLinea(".flags:      .word   1576962");
            escribirLinea(".gpiobase:   .word   0x3F200000");
            escribirLinea(".base:       .word   0x00000000");
            escribirLinea(".data");
            escribirLinea(".file:       .ascii \"/dev/mem\\000\"");
        }

        if (inst.equals("ESCRIBIR_PIN")) {
            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "LDR  R1, [R5, #" + op1 + "]");
            escribirLinea(linea + cmv2);

            escribirLinea(linea + "BL   checar_pin");

            escribirLinea(linea + amv2);
            escribirLinea(linea + "LDR  R5, [SP, #20]");
            escribirLinea(linea + "LDR  R1, [R5, #" + op2 + "]");
            escribirLinea(linea + cmv2);

            escribirLinea(linea + "BL   checar_pin_estado");

            escribirLinea(linea + amv1);
            escribirLinea(linea + "LDR  R3, [SP, #16]");
            escribirLinea(linea + "ADD  R3, R3, R6");
            escribirLinea(linea + "LDR  R2, [SP, #16]");
            escribirLinea(linea + "ADD  R2, R2, R6");
            escribirLinea(linea + "LDR  R2, [R2, #0]");
            escribirLinea(linea + "BIC  R2, R2, R7");
            escribirLinea(linea + "STR  R2, [R3, #0]");
            escribirLinea(linea + "LDR  R3, [SP, #16]");
            escribirLinea(linea + "ADD  R3, R3, R6");
            escribirLinea(linea + "LDR  R2, [SP, #16]");
            escribirLinea(linea + "ADD  R2, R2, R6");
            escribirLinea(linea + "LDR  R2, [R2, #0]");
            escribirLinea(linea + "ORR  R2, R2, R8");
            escribirLinea(linea + "STR  R2, [R3, #0]");
            escribirLinea(linea + "LDR  R3, [SP, #16]");
            escribirLinea(linea + "ADD  R3, R3, R9");
            escribirLinea(linea + "MOV  R2, R10");
            escribirLinea(linea + "STR  R2, [R3, #0]");
            escribirLinea(linea + cmv1);
        }

        if (inst.equals("PAINTER")) {
            res = res.substring(0, res.length() - 1);
            res = res.substring(1, res.length());
            res = res.toLowerCase();
            for (int i = 0; i < res.length(); i++) {
                escribir(res.charAt(i));
            }
            
        }
    }

    void escribir_pinesXXX(int gpio, int estado) {
        String amv1 = "BL   av1";
        String cmv1 = "BL   cv1";
        String linea = "            ";
        escribirLinea(linea + "MOV  R1, #" + String.valueOf(gpio));
        escribirLinea(linea + "BL   checar_pin");
        escribirLinea(linea + "MOV  R1, #" + String.valueOf(estado));
        escribirLinea(linea + "BL   checar_pin_estado");
        escribirLinea(linea + amv1);
        escribirLinea(linea + "LDR  R3, [SP, #16]");
        escribirLinea(linea + "ADD  R3, R3, R6");
        escribirLinea(linea + "LDR  R2, [SP, #16]");
        escribirLinea(linea + "ADD  R2, R2, R6");
        escribirLinea(linea + "LDR  R2, [R2, #0]");
        escribirLinea(linea + "BIC  R2, R2, R7");
        escribirLinea(linea + "STR  R2, [R3, #0]");
        escribirLinea(linea + "LDR  R3, [SP, #16]");
        escribirLinea(linea + "ADD  R3, R3, R6");
        escribirLinea(linea + "LDR  R2, [SP, #16]");
        escribirLinea(linea + "ADD  R2, R2, R6");
        escribirLinea(linea + "LDR  R2, [R2, #0]");
        escribirLinea(linea + "ORR  R2, R2, R8");
        escribirLinea(linea + "STR  R2, [R3, #0]");
        escribirLinea(linea + "LDR  R3, [SP, #16]");
        escribirLinea(linea + "ADD  R3, R3, R9");
        escribirLinea(linea + "MOV  R2, R10");
        escribirLinea(linea + "STR  R2, [R3, #0]");
        escribirLinea(linea + cmv1);
    }

    void temporizador(int tiempo) {
        String linea = "            ";
        escribirLinea(linea + "MOV  R3, #" + tiempo);
        
        escribirLinea("loop_timer_" + (++loopTemporizador) + ":");
        escribirLinea(linea + "MOV  R2, #0x11000000");
        escribirLinea("loop_timer_" + (--loopTemporizador) + ":");
        escribirLinea(linea + "SUB  R2, #1");
        escribirLinea(linea + "CMP  R2, #0");
        escribirLinea(linea + "BNE  loop_timer_" + loopTemporizador++);
        escribirLinea(linea + "SUB  R3, #1");
        escribirLinea(linea + "CMP  R3, #0");
        escribirLinea(linea + "BNE  loop_timer_" + (loopTemporizador++));
    }

    void escribir(char actual) {
        switch (actual) {
            case 'a'://a {4,8,10,13,14,15,18,20,23,25}
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(25, 0);
                break;
            case 'b'://b {3,4,5,8,10,13,14,18,20,23,24,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(24, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(24, 0);
                escribir_pinesXXX(25, 0);
                break;
            case 'c'://c {4,5,8,13,18,24,25}
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(24, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(24, 0);
                escribir_pinesXXX(25, 0);
                break;
            case 'd'://d {3,4,8,10,13,15,18,20,23,24}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(24, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(24, 0);
                break;
            case 'e'://e {3,4,5,8,13,14,15,18,23,24,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(24, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(24, 0);
                escribir_pinesXXX(25, 0);
                break;
            case 'f'://f {3,4,5,8,13,14,15,18,23}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(23, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(23, 0);
                break;
            case 'g'://g {4,5,8,13,14,15,18,24,20}
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(24, 1);
                escribir_pinesXXX(20, 1);
                temporizador(1);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(24, 0);
                escribir_pinesXXX(20, 0);
                break;
            case 'h'://h {3,5,8,10,13,14,15,18,20,23,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(25, 0);
                break;
            case 'i'://i {3,4,5,9,14,19,23,24,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(9, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(19, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(24, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(9, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(19, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(24, 0);
                escribir_pinesXXX(25, 0);
                break;
            case 'j'://j {3,4,5,9,14,19,23}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(9, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(19, 1);
                escribir_pinesXXX(23, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(9, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(19, 0);
                escribir_pinesXXX(23, 0);
                break;
            case 'k'://k {3,5,8,9,13,18,19,23,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(9, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(19, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(9, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(19, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(25, 0);
                break;
            case 'l'://l {3,8,13,18,23,24,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(24, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(24, 0);
                escribir_pinesXXX(25, 0);
                break;
            case 'm'://m {8,10,13,14,15,18,20,23,25}
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(25, 0);
                break;
            case 'n'://n {8,9,10,13,15,18,20,23,25}
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(9, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(9, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(25, 0);
                break;
            case 'ñ'://ñ {3,4,5,14,15,18,20,23,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(25, 0);
                break;
            case 'o'://o {3,4,5,8,10,13,15,18,20,23,24,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(24, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(24, 0);
                escribir_pinesXXX(25, 0);
                break;
            case 'p'://p {3,4,5,8,10,13,14,15,18,23}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(23, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(23, 0);
                break;
            case 'q'://q {3,4,5,8,10,13,14,15,20,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(25, 0);
                break;
            case 'r': //r {3,4,5,8,10,13,14,18,19,23,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(19, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(19, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(25, 0);
                break;
            case 's': //s {4,5,8,14,20,24,23}
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(24, 1);
                temporizador(1);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(24, 0);
                break;
            case 't'://t {3,4,5,9,14,19,24}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(9, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(19, 1);
                escribir_pinesXXX(24, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(9, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(19, 0);
                escribir_pinesXXX(24, 0);
                break;
            case 'u'://u {3, 5,8,10,13,15,18,20,23,24,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(24, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(24, 0);
                escribir_pinesXXX(25, 0);
                break;
            case 'v'://v {3,5,8,10,13,15,18,20,24}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(24, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(24, 0);
                break;
            case 'w'://w {8,10,13,15,18,19,20,23,25}
                escribir_pinesXXX(8 ,1);
                escribir_pinesXXX(10 ,1);
                escribir_pinesXXX(13 ,1);
                escribir_pinesXXX(15 ,1);
                escribir_pinesXXX(8 ,1);
                escribir_pinesXXX(19 ,1);
                escribir_pinesXXX(20 ,1);
                escribir_pinesXXX(23 ,1);
                escribir_pinesXXX(25 ,1);
                temporizador(1);
                escribir_pinesXXX(8 ,0);
                escribir_pinesXXX(10 ,0);
                escribir_pinesXXX(13 ,0);
                escribir_pinesXXX(15 ,0);
                escribir_pinesXXX(8 ,0);
                escribir_pinesXXX(19 ,0);
                escribir_pinesXXX(20 ,0);
                escribir_pinesXXX(23 ,0);
                escribir_pinesXXX(25 ,0);
                break;
            case 'x'://x {8,10,14,18,20}
                escribir_pinesXXX(8, 1);            
                escribir_pinesXXX(10, 1);            
                escribir_pinesXXX(14, 1);            
                escribir_pinesXXX(18, 1);            
                escribir_pinesXXX(20, 1);    
                temporizador(1);
                escribir_pinesXXX(8, 0);            
                escribir_pinesXXX(10, 0);            
                escribir_pinesXXX(14, 0);            
                escribir_pinesXXX(18, 0);            
                escribir_pinesXXX(20, 0);   
                break;
            case 'y'://y {3,5,8,10,14,15,20,23,24,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(24, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(24, 0);
                escribir_pinesXXX(25, 0);
                break;
            case 'z'://z {3,4,5,10,15,14,13,18,23,24,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(24, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(24, 0);
                escribir_pinesXXX(25, 0);
                break;
            case '1'://1 {4,8,9,14,19,23,24,25}
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(9, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(19, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(24, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(9, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(19, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(24, 0);
                escribir_pinesXXX(25, 0);
                break;                
            case '2'://2 {3,4,5,10,14,18,23,24,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(24, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(24, 0);
                escribir_pinesXXX(25, 0);
                break;
            case '3'://3 {3,4,5,10,14,15,20,23,24,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(24, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(24, 0);
                escribir_pinesXXX(25, 0);
                break;
            case '4'://4 {3,5,8,10,13,14,15,20,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(25, 0);
                break;
            case '5'://5 {3,4,5,8,13,14,15,18,20,23,24,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(24, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(24, 0);
                escribir_pinesXXX(25, 0);
                break;
            case '6'://6 {3,4,5,8,13,14,15,18,20,23, 24,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(24, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(24, 0);
                escribir_pinesXXX(25, 0);
                break;
            case '7'://7 {3,4,5,10,14,15,20,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(25, 0);
                break;
            case '8'://8 {3,4,5,8,10,13,14,15,18,20,23,24,25}
                escribir_pinesXXX(3, 1);
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(18, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(24, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(3, 0);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(18, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(24, 0);
                escribir_pinesXXX(25, 0);
                break;
            case '9'://9 {4,5,8,10,14,15,20,25}
                escribir_pinesXXX(4, 1);
                escribir_pinesXXX(5, 1);
                escribir_pinesXXX(8, 1);
                escribir_pinesXXX(10, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                escribir_pinesXXX(20, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(4, 0);
                escribir_pinesXXX(5, 0);
                escribir_pinesXXX(8, 0);
                escribir_pinesXXX(10, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(15, 0);
                escribir_pinesXXX(20, 0);
                escribir_pinesXXX(25, 0);
                break;
            case ' ':
                temporizador(1);
                break;
            case '-':
                escribir_pinesXXX(13, 1);
                escribir_pinesXXX(14, 1);
                escribir_pinesXXX(15, 1);
                temporizador(1);
                escribir_pinesXXX(13, 0);
                escribir_pinesXXX(14, 0);
                escribir_pinesXXX(15, 0);
                break;
            case '_':
                escribir_pinesXXX(23, 1);
                escribir_pinesXXX(24, 1);
                escribir_pinesXXX(25, 1);
                temporizador(1);
                escribir_pinesXXX(23, 0);
                escribir_pinesXXX(24, 0);
                escribir_pinesXXX(25, 0);
                break;
            default:
                break;
        }
    }
}
