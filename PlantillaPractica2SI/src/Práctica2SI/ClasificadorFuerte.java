/*S
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Práctica2SI;

import java.util.ArrayList;

/**
 *
 * @author rafa
 */
public class ClasificadorFuerte {
    // Se compone de un array de ClasificadoresDebiles
    private ArrayList<ClasificadorDebil> debiles;
    // atributo aparte, donde guardo el porcentaje de cada iteracipn
    private ArrayList<Double> porcentaje;
   // creo un clasificador debil
    public ClasificadorFuerte() {
        debiles = new ArrayList<ClasificadorDebil>();
        porcentaje = new ArrayList<Double>();
    }
    // cewo un xlasificador fuerte
    public void IntroducirFuerte(ClasificadorDebil debil){
       debiles.add(debil);
    }
    
    public void IntroducirPorcentaje(double p){
       porcentaje.add(p);
    }
    
    //dado una posicion, saca el porcentaje
    
    public double sacarProcentaje(int i){
        double result;
        
        return result = porcentaje.get(i);
  
    }
    
    public ClasificadorDebil sacarClasificador(int i){
        return debiles.get(i);
  
    }
    
    public double sacarError(int i){
        double result;
        
        result = debiles.get(i).getError();
        return result;
  
    }
    
    // Comprueba si es cara o no
    public int determinarCara(Cara c){
        
        double result = 0;
        int esCara = 0;
 

        ClasificadorDebil cDebil = new ClasificadorDebil();
        for(int i = 0; i< debiles.size();i++){
            cDebil = debiles.get(i);
            result += cDebil.getValorConfianza() * cDebil.mirar(c);
        }

         
        if(result < 0.0) esCara =  -1;
        else  esCara =  1;
        
        return esCara;
    }
    
    public int determinarCara(Cara c,int j){
        
        double result = 0;
        int esCara = 0;

        ClasificadorDebil cDebil = new ClasificadorDebil();
        for(int i = 0; i< debiles.size();i++){
            cDebil = debiles.get(i);
            result += cDebil.getValorConfianza() * cDebil.mirar(c);
            j--;
            if(j <= 0) break;
        }

         
        if(result < 0.0) esCara =  -1;
        else  esCara =  1;
        
        return esCara;
    }
    
    public double sacarError(Cara c,ClasificadorDebil cDebil){
        double auxError = 0;
        
        if(c.getTipo() != cDebil.mirar(c))
            auxError+= c.getPeso();
            
        return auxError;
    }
    
    
}
