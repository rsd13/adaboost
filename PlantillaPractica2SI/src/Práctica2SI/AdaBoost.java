/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Práctica2SI;
import java.util.ArrayList;
/**
 *
 * @author rafa
 */
public class AdaBoost {
    
    // x es numero de iteraciones
    public static ClasificadorFuerte adaBoost(int numClasificadores, ArrayList<Cara>listaCaras, int x,
    double test){
        double porcentaje = 0;

        // iniciazlizo los pesos
        
        for(int i = 0; i <listaCaras.size();i++){
            Cara cara = listaCaras.get(i);
            cara.setPeso((double) 1.0/listaCaras.size());
        }
        
        // creo el clasificador fuerte
        ClasificadorFuerte fuerte = new ClasificadorFuerte();
        for(int i = 0; i < x; i++){
            //entremaos el mejor y recojo el confianza
            ClasificadorDebil mejor = new ClasificadorDebil();
            mejor.entrenar(listaCaras,numClasificadores);
            fuerte.IntroducirFuerte(mejor);
            //System.out.println(mejor.getError());
            double valorConfianza = mejor.getValorConfianza();
            
            // actualizo la distribucion de pesos
            double Z = 0;
            for(int j = 0; j <listaCaras.size();j++){
                Cara cara = listaCaras.get(j);
                Z += cara.getPeso();
            }
                
            for(int j = 0; j <listaCaras.size();j++){
                Cara cara = listaCaras.get(j);
                double actualizar;
                if(mejor.mirar(cara) != cara.getTipo())
                    actualizar = Math.pow(Math.E,valorConfianza);
                else
                    actualizar = Math.pow(Math.E,-valorConfianza);
                cara.setPeso(cara.getPeso() * actualizar / Z);
            }
            // miro los aciertos que tiene.
            
            double aciertos = 0;
            for(int j = 0; j <listaCaras.size();j++){
                Cara cara = listaCaras.get(j);
                if(fuerte.determinarCara(cara) == cara.getTipo()) aciertos++;
            }
            //guardo el porcentaje
            porcentaje = (100.0 * aciertos/listaCaras.size());
            fuerte.IntroducirPorcentaje(porcentaje);
        }
        
	return fuerte;
    }
    
}
