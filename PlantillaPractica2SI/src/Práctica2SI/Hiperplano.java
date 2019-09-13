/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Práctica2SI;

import static java.lang.Double.MAX_VALUE;

/**
 *
 * @author rafa
 */
public class Hiperplano 
{
    public static final int dimension = 576;
    // Escala de grises [0,255]
    private final int maximoGris = 255;

    // Vector de puntos
    private double[] puntos;
    private double D; //valor idependiente D

    
    
    public Hiperplano(){
        
        puntos = new double[dimension];
        // genero puntos leatorios
        for(int i = 0; i < dimension; i++){
            // puntos aleatorios
            puntos[i] = (double) (Math.random() * (1 - (-1))) + (-1);
        }
        // Normalizo el vector para que tenga la misma dirección y sentido
        double modulo = 0;
        for(int i = 0; i < dimension; i++)
            modulo += puntos[i];
        for(int i = 0; i < dimension; i++)
            puntos[i] /= modulo;
        for(int i = 0; i < dimension; i++)
            D -= puntos[i] * (((int) (Math.random() * MAX_VALUE)) % (maximoGris + 1));
    }
    
    
    public double[] getPuntos(){ return puntos;}
    
    /* Evalua un punto el plano:
       O si esta dentro del plano
       + si esta encima
       - si esta debajo*/
    
    public double ver(int[] aux){
        double resultado = 0;
        
        for(int i = 0; i < aux.length; i++)
            resultado += puntos[i] * aux[i];
        
        return resultado += D;

    }
    
}
