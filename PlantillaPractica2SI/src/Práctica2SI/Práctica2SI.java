

package Práctica2SI;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author dviejo
 */
public class Práctica2SI {

    private String rutaDir;
    private File []files;
    private int NUM_ITERACIONES; 
    private int NUM_CLASIFICADORES;
    private boolean VERBOSE;

    private double testRate;

    private ArrayList<Cara> listaAprendizaje;
    private ArrayList<Cara> listaTest;

    public Práctica2SI()
    {
        rutaDir = "";
        testRate = 0.2;
	NUM_ITERACIONES = 100;
	NUM_CLASIFICADORES = 1000;
        VERBOSE = false;
    }
    
    public void init()
    {
        int cont;
	int aciertos, clase;
	System.out.println("Sistemas Inteligentes. Segunda practica");
		
	getFileNames(rutaDir+"cara/");
	listaAprendizaje = new ArrayList<Cara>();
	for(cont=0;cont<files.length;cont++)
	{
		if(!files[cont].isDirectory())
		{
			listaAprendizaje.add(new Cara(files[cont],1));
		}
	}
	getFileNames(rutaDir+"noCara/");
	for(cont=0;cont<files.length;cont++)
	{
		if(!files[cont].isDirectory())
		{
			listaAprendizaje.add(new Cara(files[cont], -1));
		}
	}
	System.out.println(listaAprendizaje.size()+ " imágenes encontradas");
		
	//inicializamos las listas
        
	listaTest = new ArrayList<Cara>();
		
	//Separamos los conjuntos de aprendizaje y test
	CrearConjuntoAprendizajeyTest();
	System.out.println(listaAprendizaje.size()+" imagenes para aprendizaje, "+listaTest.size()+" imagenes para el test ("+(testRate*100)+"%)");

	//Comenzamos el aprendizaje
	long t1 = System.currentTimeMillis();
        //TODO Aquí debéis poner vuestra llamada al método de entrenamiento de AdaBoost

        ClasificadorFuerte cFuerte = null;
	cFuerte = AdaBoost.adaBoost(NUM_CLASIFICADORES, listaAprendizaje, NUM_ITERACIONES,testRate);
        long t2 = System.currentTimeMillis();
	long time;
      //fichero para mirar que hace cada iteracion, y los resultados finales
      // del test y aprendizaje
        FileWriter fichero = null;
        PrintWriter pw = null;
        double aux;
        try
        {
            fichero = new FileWriter("resultados.txt");
            pw = new PrintWriter(fichero);

            for(int i = 0; i < NUM_ITERACIONES;i++){
                aux = cFuerte.sacarProcentaje(i);
                pw.write(aux + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        pw.write("Los errores del aprendizaje son: \n");
        for(int i = 0; i < NUM_ITERACIONES;i++){
            aux = cFuerte.sacarProcentaje(i);
            pw.write(100.0 - aux + "\n");
        }
        
	time = t2 - t1;
	System.out.println("Tiempo empleado en el aprendizaje: "+((float)time/1000f)+" segundos");
	System.out.println("Número de clasificadores encontrados: " + NUM_CLASIFICADORES); //TODO añadir el valor
        
        
    
        
	//Test final
        if(VERBOSE)
        {
            aciertos = 0;
            for(int i = 0; i < listaAprendizaje.size();i++){
                Cara cara = listaAprendizaje.get(i);
                //clase = -1;  //TODO Cambiar -1 por la llamada a clasificar utilizando el clasificador fuerte
                        //de Adaboost para el ejemplo c
                if(cFuerte.determinarCara(cara) == cara.getTipo())
                    aciertos++;
            }
            System.out.println("APRENDIZAJE. Tasa de aciertos: "+((float)aciertos/(float)(listaAprendizaje.size())*100.0f)+"%");
            pw.write("El aprendizaje total es de: " + ((float)aciertos/(float)(listaAprendizaje.size())*100.0f) + "\n");
            
      
            // Parte TEST
            pw.write("------PARTE TEST-------\n");
            pw.write("Los fallos del test son: \n ");
            for(int i = 0; i < NUM_ITERACIONES;i++){
                double fallos = 0;
                for(int j = 0; j < listaTest.size();j++){
                    Cara cara = listaTest.get(j);
                    if (cFuerte.determinarCara(cara,i) != cara.getTipo())
			fallos++;
                }
                pw.write(((float)fallos/(float)(listaTest.size())*100.0f) + "\n");
            }
            
        }
        System.out.println("-----------------------------------------------------");
	//Comprobamos el conjunto de test
	aciertos = 0;

	for(int i = 0; i < listaTest.size();i++){
            Cara cara = listaTest.get(i);
		//clase = -1;  //TODO Cambiar -1 por la llamada a clasificar utilizando el clasificador fuerte
                            //de Adaboost para el ejemplo c
            if(cFuerte.determinarCara(cara) == cara.getTipo())
                aciertos++;
            //System.out.println(cFuerte.sacarError(i));

	}
	System.out.println("TEST. Tasa de aciertos: "+((float)aciertos/(float)(listaTest.size())*100.0f)+"%");
        pw.write("El test total es de: " + ((float)aciertos/(float)(listaTest.size())*100.0f) + "\n");
        //cierro el fichero
        pw.close();
    }
    
    /**
     * Selecciona al azar un subconjunto para Test. El resto compondrá el conjunto de aprendizaje
     */
    private void CrearConjuntoAprendizajeyTest()
    {
    	int totalTam = listaAprendizaje.size();
	int tamTest = (int)(totalTam * testRate);
	int cont;
	Random rnd = new Random(System.currentTimeMillis());
		
	for(cont=0;cont<tamTest;cont++)
	{
            listaTest.add(listaAprendizaje.remove(rnd.nextInt(totalTam)));
            totalTam--;
	}
    }
    
    public void getFileNames(String ruta)
    {
    	File directorio = new File(ruta);
	if (!directorio.isDirectory())
            throw new RuntimeException("La ruta debe ser un directorio");
	ImageFilter filtro = new ImageFilter();
	files = directorio.listFiles(filtro);
    }

	public void setRuta(String r)
	{
		rutaDir = r;
	}
	
	public void setRate(double t)
	{
		testRate = t;
	}
	
	public void setNumIteraciones(int t)
	{
		NUM_ITERACIONES = t;
	}
	public void setNumClasificadores(int c)
	{
		NUM_CLASIFICADORES = c;
	}

	public void setVerbose(boolean v)
	{
		VERBOSE = v;
	}
	
    
    /**
     * @param args the command line arguments
     */
        
          /**
     * Crea un vector con los valores minimos de todas las caras
     * @return aux
     */
    public int[] getMinimos(){
        int [] minimo = new int[listaAprendizaje.size()];
        for (int i = 0; i < listaAprendizaje.size(); i++){
            minimo[i] = listaAprendizaje.get(i).getMinimo();
        }
        return minimo;
    }
    
    /**
     * Crea un vector con los calores maximos de todas las caras
     * @return 
     */
   
    public static void main(String[] args) {
		int cont;
		Práctica2SI programa;
		String option;
		boolean maluso = true;
		int paso = 2;
		
		programa = new Práctica2SI();

		for(cont = 0; cont < args.length; cont+=paso)
		{
			option = args[cont];
			if(option.charAt(0) == '-')
			{
				switch(option.charAt(1))
				{
				case 'd':
					programa.setRuta(args[cont+1]);
					paso = 2;
                                        maluso = false;
					break;
				case 't':
					programa.setRate(Double.parseDouble(args[cont+1]));
					paso = 2;
					break;
				case 'T':
					programa.setNumIteraciones(Integer.parseInt(args[cont + 1]));
					paso = 2;
					break;
				case 'c':
					programa.setNumClasificadores(Integer.parseInt(args[cont + 1]));
					paso = 2;
					break;
				case 'v':
					programa.setVerbose(true);
					paso = 1;
					break;
				default:
					maluso = true;
				}
			}
			else maluso = true;
		}
		
		if(!maluso)
			programa.init();
		else
		{
			System.out.println("Lista de parametros incorrecta");
			System.out.println("Uso: java Practica2SI -d ruta [-t testrate] [-T maxT] [-c numClasificadores] [-v]");
		}
    }
}