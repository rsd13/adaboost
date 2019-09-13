/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Práctica2SI;

import java.util.ArrayList;

/**
 *
 * @author rafa
 */
public class ClasificadorDebil 
{

    // Mejor hiperplano del clasificador
    private Hiperplano hiperplano;
    // Valor de confianza para este clasificador
    private double valorConfianza;
    private double error;
    
    public ClasificadorDebil(){
        hiperplano = new Hiperplano();
        error = 0;
        valorConfianza = 0;
    }

    // constructor de copia
    public ClasificadorDebil(ClasificadorDebil debil){
        this.hiperplano = debil.hiperplano;
        this.valorConfianza = debil.valorConfianza;
        this.error = debil.error;
    }

    
    public void setHiperplano(Hiperplano r_hiperplano){
        hiperplano = r_hiperplano;
    }
    
    /*public void setHiperplano(Hiperplano r_hiperplano){
        mejor = r_hiperplano;
    }*/
    
    public void setError(double p_error){ error = p_error;}
    public double getValorConfianza() { return valorConfianza; }
    public Hiperplano getHiperplano() { return hiperplano; }
    public double getError() { return error; }
    
    // si es menor que cero es no cara
    // si es mayor que cero es cara
    
    public int mirar( Cara c){
        int result = 0;
        
        if(hiperplano.ver(c.getData()) < 0.0) result = -1;
        else result =  1;
        
        return result;
    }
    
    // Clasifico el conjunto de aprendijaze y encuentro el mejor
    
    public void calcularError(ArrayList<Cara> listaCaras){
        error = 0;
        for(int i = 0; i < listaCaras.size();i++){
            Cara cara = listaCaras.get(i);
            if(cara.getTipo() != mirar(cara))
                 error+= cara.getPeso();
            
        }
        // ht: αt= ½ Ln(1-εt/εt)
        valorConfianza = 0.5 * Math.log((1 - error)/error);
        
                
        //double erroresTotales = error/listaCaras.size();
        //System.out.println(erroresTotales*100);
        
    }
    
    public void entrenar(ArrayList<Cara> listaCaras,int x){
        ClasificadorDebil mejor = new ClasificadorDebil();
        //para que entre en el primero si o si
        mejor.setError(Double.MAX_VALUE);
        //ClasificadorDebil mejor = null;
        for(int i = 0; i < x;i++){
            ClasificadorDebil aux = new ClasificadorDebil();
            aux.calcularError(listaCaras);
            if(aux.getError() < mejor.getError()){
                mejor = aux;
                hiperplano = mejor.hiperplano;
                valorConfianza = mejor.valorConfianza;
                error = mejor.error;
            }
        }
    }
    
    
}
