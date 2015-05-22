
/*
    fecha:          14-Abil-2014       
    descripcion:    creacion funcion actualizar semaforo, falta implementar
                    conteo para la cola, e ingresar los carros a cada via
                
    fecha:          15-Abil-2014       
    descripcion:    Implementacion actualizar cola, preguntar por que los 
                    arrayList para la colas y como inserto un vehiculo
                    en los corredore viales
           
    fecha:          19-Abil-2014       
    descripcion:    Creacion e implementacion metodo asignarCarrilNS

*/


package Simulacion;

import Modelo.Seccion;
import Modelo.Vehiculo;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author sergio
 */
public class Ambiente {
    
    int espacio = 0;
    
    static Seccion objSeccionEmpty = new Seccion(true, null, 2);
    static Seccion objSeccionNoDesvio = new Seccion(true, null, -2);
    static Seccion objSeccionSemaforo = new Seccion(true, null, -1);
    static Seccion objSeccionMasivo = new Seccion(true, null, 4);
    static Seccion objSeccionCarro = new Seccion(true, null, 3);
    static Seccion objSeccionSiDesvio = new Seccion(true, null, 0);
    
    static int contadorSemaforo = 0;
    static boolean isVerde = false;
    static int indexCola = 0;
    
    // corredores viales
    private static Seccion [][] corredorNorteSur;
    private static Seccion [][] corredorSurNorte;
    
    // colas corredor norte sur
    private static ArrayList cola1CorredorNS;
    private static ArrayList cola2CorredorNS;
    private static ArrayList cola3CorredorNS;
    private static ArrayList cola4CorredorNS;
        
    // colas corredor sur norte
    private static ArrayList cola1CorredorSN;
    private static ArrayList cola2CorredorSN;
    private static ArrayList cola3CorredorSN;
    private static ArrayList cola4CorredorSN;
    private static ArrayList cola5CorredorSN;
    private static ArrayList colaDesvioCorredorSN;
    
    /* PENDIENTE: oreja inicial corredor SN se necesita cola?
       en el desvio tambien se necesita cola?
        que tipos de carros pueden entrar al desvio? --> vehiculos
    */
    
    public Ambiente() {
        corredorNorteSur = new Seccion[5][180];
        // carril 3 -> -2 hasta casilla 52
        corredorSurNorte = new Seccion[4][180];
        
        cola1CorredorNS = new ArrayList();
        cola2CorredorNS = new ArrayList();
        cola3CorredorNS = new ArrayList();
        cola4CorredorNS = new ArrayList();
        
        
        cola1CorredorSN = new ArrayList();
        cola2CorredorSN = new ArrayList();
        cola3CorredorSN = new ArrayList();
        cola4CorredorSN = new ArrayList();
            
        //todos tienen por defecto un vehiculo vacio
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 180; j++){
                
                if(i< 4){
                    corredorSurNorte[i][j] = objSeccionEmpty;
                }
                
                corredorNorteSur[i][j] = objSeccionEmpty;
                
            }
        }
        
        // Inicializar los dos corredores
        // i filas, j columnas
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 180; j++) {
                if (i == 0) {// se setean los limites del corredor norte sur y los cruces
                    if ( j == (40 - 1) || j == (62 -1) || j == (101 - 1) || j == (144 - 1) )
                        corredorNorteSur[0][j] = objSeccionSiDesvio;
                    else // valor no valido para posicionar un vehiculo
                        corredorNorteSur[0][j] = objSeccionNoDesvio;
                }
                else if (i == 2 && j < 52) { // tercer carril Sur - Norte, no existe
                    corredorSurNorte[i][j] = objSeccionNoDesvio;
                }
                
                else if(i == 2 && j <179){
                    corredorSurNorte[i][j] = objSeccionCarro;
                    
                    
                }else if(i == 3 && j <179){
                    corredorNorteSur[i][j] = objSeccionCarro;
                }
                
                else if (i < 4 && j < 179) {
                    corredorSurNorte[i][j] = objSeccionEmpty;
                    corredorNorteSur[i][j] = objSeccionEmpty;
                    
                }// indicadores de semaforos
                else if ( (i == 1 || i == 2 || i == 3) && j == 179) {
                    corredorSurNorte[i][j] = objSeccionSemaforo;
                    corredorNorteSur[i + 1][j] = objSeccionSemaforo;
                    
                }else if (j < 179) { // para el cuarto carril
                    corredorSurNorte [3][j] = objSeccionMasivo;
                    corredorNorteSur[i][j] = objSeccionMasivo;

                }
            }
        }
        
        /**/
        System.out.println("CORREDOR NORTE - SUR");
        for (int i = 0; i < 180; i++) {
            for (int j = 0; j < 5; j++) {
                espacio = corredorNorteSur[j][i].getTipoSeccion();
                ////JOptionPane.showMessageDialog(null, corredorNorteSur[j][i].getEspacio());
                System.out.print(espacio + " ");
            }
            System.out.println("L" + (i+1));
        }
        
        System.out.println("\nCORREDOR SUR - NORTE");
        for (int i = 0; i < 180; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(corredorSurNorte[j][i].getTipoSeccion() + " ");
            }
            System.out.println("L" + (i + 1));
            
        }
        
    }
    
    /**
     * @param objVehiculo  : el carro generado por el controlador
     * @param tiempo : tiempo en el cual se esta ejecutando la simulacion
     * @param corredor : nombre del corredor
     **/
    public static void actualizarAmbiente(Vehiculo objVehiculo, int tiempo,String corredor){
        
        //define el tipo de vehiculo 
        int tipoVehiculo = objVehiculo.getIdTipoVehiculo();
        
        //las secciones ocupadas por los vehiculos
        Seccion seccionMixta = new Seccion(false, objVehiculo, 2);
        Seccion seccionCarro = new Seccion(false, objVehiculo, 3);
        Seccion seccionMasivo = new Seccion(false, objVehiculo, 4);
        
        
       //este es el contador del semaforo, esta a 4 segundos  
        if(contadorSemaforo == 4){
            
            if(isVerde){
                
                corredorNorteSur[1][179] = objSeccionSemaforo;
                corredorNorteSur[2][179] = objSeccionSemaforo;
                corredorNorteSur[3][179] = objSeccionSemaforo;
                corredorNorteSur[4][179] = objSeccionSemaforo;
                
                int[] colasNS;        
                colasNS = conteoColas(corredorNorteSur);
                
                if(colasNS[1] != 0){
                    
                    cola1CorredorNS.add(indexCola, colasNS[1]);
                }
                
                if(colasNS[2] != 0){
                    
                    cola2CorredorNS.add(indexCola, colasNS[2]);
                }
                
                if(colasNS[3] != 0){
                    
                    cola3CorredorNS.add(indexCola, colasNS[3]);
                }
                
                if(colasNS[4] != 0){
                    cola4CorredorNS.add(indexCola, colasNS[4]);
                }
                
                //corredores sur norte
                int[] colasSN;
                
                colasSN = conteoColas(corredorSurNorte);
                
                if(colasSN[0] != 0){
                    
                    cola1CorredorSN.add(indexCola, colasSN[1]);
                }
                
                if(colasSN[1] != 0){
                    
                    cola2CorredorSN.add(indexCola, colasSN[2]);
                }
                
                if(colasSN[2] != 0){
                    
                    cola3CorredorSN.add(indexCola, colasSN[3]);
                }
                
                if(colasSN[3] != 0){
                    cola4CorredorSN.add(indexCola, colasSN[4]);
                }
                
                indexCola++;
                isVerde = false;
                contadorSemaforo = 0;

            }else {
                
                corredorNorteSur[1][179] = objSeccionEmpty;
                corredorNorteSur[2][179] = objSeccionEmpty;
                corredorNorteSur[3][179] = objSeccionCarro;
                corredorNorteSur[4][179] = objSeccionMasivo;
                
                isVerde = true;
                contadorSemaforo = 0;
            }
            
            
        }else {
            
            contadorSemaforo++;
        }
        
        //condicion para evaluar el nombre del corredor
        if(corredor.equals("norteSur")){
            
            //terminamos corredor
            if(!corredorNorteSur[1][178].isEmpty() && isVerde){
                
                switch(corredorNorteSur[1][178].getObjVehiculo().getIdTipoVehiculo()){
                    
                    case 1:
                        
                        corredorNorteSur[1][178] = objSeccionEmpty;
                    break;
                        
                        
                    case 2:
                        
                        corredorNorteSur[1][178] = objSeccionEmpty;
                        corredorNorteSur[1][177] = objSeccionEmpty;
                        
                    break;
                        
                    case 3:
                        
                        corredorNorteSur[1][178] = objSeccionEmpty;
                        corredorNorteSur[1][177] = objSeccionEmpty;
                        corredorNorteSur[1][176] = objSeccionEmpty;
                        
                    break;    
                }
             
            }
            
            if(!corredorNorteSur[2][178].isEmpty() && isVerde){
                
                switch(corredorNorteSur[2][178].getObjVehiculo().getIdTipoVehiculo()){
                    
                    case 1:
                        
                        corredorNorteSur[2][178] = objSeccionEmpty;
                    break;
                        
                        
                    case 2:
                        
                        corredorNorteSur[2][178] = objSeccionEmpty;
                        corredorNorteSur[2][177] = objSeccionEmpty;
                        
                    break;
                        
                    case 3:
                        
                        corredorNorteSur[2][178] = objSeccionEmpty;
                        corredorNorteSur[2][177] = objSeccionEmpty;
                        corredorNorteSur[2][176] = objSeccionEmpty;
                        
                    break;    
                }
                
            }
            
            if(!corredorNorteSur[3][178].isEmpty() && isVerde){
                
                corredorNorteSur[3][178] = objSeccionEmpty;
                
            }
            
            if(!corredorNorteSur[4][178].isEmpty() && isVerde){
                
                corredorNorteSur[4][178] = objSeccionEmpty;
                corredorNorteSur[4][177] = objSeccionEmpty;
                corredorNorteSur[4][176] = objSeccionEmpty;
                corredorNorteSur[4][175] = objSeccionEmpty;
                
            }
                
            //avanzamos los carros
            if(tiempo > 0 ){
                
                for(int posIx = 178; posIx >=0; posIx--){
                    
                    //if(posIx+4 <= 178 && posIx-3 >= 0){
                    
                        //los numeros son los tipos de carriles
                        if(!corredorNorteSur[1][posIx].isEmpty()){

                            Vehiculo  VehiculoActual1 = corredorNorteSur[1][posIx].getObjVehiculo();

                            int tipoVehiculo1 = VehiculoActual1.getIdTipoVehiculo();

                            switch(tipoVehiculo1){

                                case 1:

                                    //desvio
                                    if(corredorNorteSur[0][posIx+1].getTipoSeccion() == 0 && !corredorNorteSur[1][posIx+1].isEmpty()
                                            && !corredorNorteSur[2][posIx].isEmpty()){

                                        corredorNorteSur[1][posIx] = objSeccionEmpty;

                                    //adelante
                                    }else if(corredorNorteSur[1][posIx+1].isEmpty() && (corredorNorteSur[2][posIx].isEmpty()
                                            || !corredorNorteSur[2][posIx].isEmpty())){

                                        corredorNorteSur[1][posIx+1] = seccionMixta;
                                        corredorNorteSur[1][posIx] = objSeccionEmpty;
                                        
                                    //cruce
                                    }else if(!corredorNorteSur[1][posIx+1].isEmpty() && corredorNorteSur[2][posIx+1].isEmpty()
                                            && corredorNorteSur[2][posIx].isEmpty()){

                                        corredorNorteSur[2][posIx+1] = seccionMixta;
                                        corredorNorteSur[1][posIx] = objSeccionEmpty;
                                        
                                    }

                                break;

                                case 2:

                                    //desvio
                                    if(corredorNorteSur[0][posIx+1].getTipoSeccion() == 0 ){

                                        corredorNorteSur[1][posIx] = objSeccionEmpty;
                                        corredorNorteSur[1][posIx-1] = objSeccionEmpty;    


                                    //adelante
                                        }else if(corredorNorteSur[1][posIx+1].isEmpty() && corredorNorteSur[1][posIx+2].isEmpty()
                                            && !corredorNorteSur[2][posIx].isEmpty()){

                                        corredorNorteSur[1][posIx+1] = seccionMixta;
                                        corredorNorteSur[1][posIx+2] = seccionMixta;
                                       
                                        corredorNorteSur[1][posIx] = objSeccionEmpty;
                                        corredorNorteSur[1][posIx-1] = objSeccionEmpty;
                                        
                                    //cruce
                                    }else if(!corredorNorteSur[1][posIx+1].isEmpty() && corredorNorteSur[2][posIx+1].isEmpty()
                                            && corredorNorteSur[2][posIx+2].isEmpty() && corredorNorteSur[2][posIx].isEmpty()){

                                        corredorNorteSur[2][posIx+1] = seccionMixta;
                                        corredorNorteSur[2][posIx+2] = seccionMixta;

                                        corredorNorteSur[1][posIx] = objSeccionEmpty;
                                        corredorNorteSur[1][posIx-1] = objSeccionEmpty;
                                        
                                    }

                                break;

                                case 3:

                                    //desvio
                                    if(corredorNorteSur[0][posIx+1].getTipoSeccion() == 0 ){

                                        corredorNorteSur[1][posIx] = objSeccionEmpty;
                                        corredorNorteSur[1][posIx-1] = objSeccionEmpty;
                                        corredorNorteSur[1][posIx-2] = objSeccionEmpty;

                                    //adelante
                                    }else if(corredorNorteSur[1][posIx+1].isEmpty() && corredorNorteSur[1][posIx+2].isEmpty()
                                            && corredorNorteSur[1][posIx+3].isEmpty() && !corredorNorteSur[2][posIx].isEmpty()){

                                        corredorNorteSur[1][posIx+1] = seccionMixta;
                                        corredorNorteSur[1][posIx+2] = seccionMixta;
                                        corredorNorteSur[1][posIx+3] = seccionMixta;

                                        corredorNorteSur[1][posIx] = objSeccionEmpty;
                                        corredorNorteSur[1][posIx-1] = objSeccionEmpty;
                                        corredorNorteSur[1][posIx-2] = objSeccionEmpty;

                                    //cruce
                                    }else if(!corredorNorteSur[1][posIx+1].isEmpty() && corredorNorteSur[2][posIx+1].isEmpty()
                                            && corredorNorteSur[2][posIx+2].isEmpty()&& corredorNorteSur[2][posIx+3].isEmpty()
                                            && corredorNorteSur[2][posIx].isEmpty()){

                                        corredorNorteSur[2][posIx+1] = seccionMixta;
                                        corredorNorteSur[2][posIx+2] = seccionMixta;
                                        corredorNorteSur[2][posIx+3] = seccionMixta;

                                        corredorNorteSur[1][posIx] = objSeccionEmpty;
                                        corredorNorteSur[1][posIx-1] = objSeccionEmpty;
                                        corredorNorteSur[1][posIx-2] = objSeccionEmpty;
                                        
                                    }

                                break;    

                            }

                        }

                        if(!corredorNorteSur[2][posIx].isEmpty()){

                            Vehiculo  VehiculoActual2 = corredorNorteSur[2][posIx].getObjVehiculo();

                            int tipoVehiculo2 = VehiculoActual2.getIdTipoVehiculo();

                            switch(tipoVehiculo2){

                                case 1:

                                    if(corredorNorteSur[2][posIx+1].isEmpty() && corredorNorteSur[1][posIx].isEmpty()){

                                        corredorNorteSur[2][posIx+1] = seccionMixta;
                                        corredorNorteSur[2][posIx] = objSeccionEmpty;
                                        
                                    //cruce
                                    }else if(!corredorNorteSur[2][posIx+1].isEmpty() && corredorNorteSur[1][posIx+1].isEmpty()
                                            && corredorNorteSur[1][posIx].isEmpty()){

                                        corredorNorteSur[1][posIx+1] = seccionMixta;
                                        corredorNorteSur[2][posIx] = objSeccionEmpty;

                                    }

                                break;

                                case 2:


                                    if(corredorNorteSur[2][posIx+1].isEmpty() && corredorNorteSur[2][posIx+2].isEmpty()
                                            && !corredorNorteSur[1][posIx].isEmpty()){

                                        corredorNorteSur[1][posIx+1] = seccionMixta;
                                        corredorNorteSur[1][posIx+2] = seccionMixta;

                                        corredorNorteSur[2][posIx] = objSeccionEmpty;
                                        corredorNorteSur[2][posIx-1] = objSeccionEmpty;

                                        
                                    //cruce
                                    }else if(!corredorNorteSur[1][posIx+1].isEmpty() && corredorNorteSur[2][posIx+1].isEmpty()
                                            && corredorNorteSur[2][posIx+2].isEmpty() && corredorNorteSur[2][posIx].isEmpty()){

                                        corredorNorteSur[2][posIx+1] = seccionMixta;
                                        corredorNorteSur[2][posIx+2] = seccionMixta;

                                        corredorNorteSur[1][posIx] = objSeccionEmpty;
                                        corredorNorteSur[1][posIx-1] = objSeccionEmpty;
                                        
                                    }


                                break;

                                case 3:

                                    //adelante
                                    if(corredorNorteSur[2][posIx+1].isEmpty() && corredorNorteSur[2][posIx+2].isEmpty()
                                            && corredorNorteSur[2][posIx+3].isEmpty()){

                                        corredorNorteSur[2][posIx+1] = seccionMixta;
                                        corredorNorteSur[2][posIx+2] = seccionMixta;
                                        corredorNorteSur[2][posIx+3] = seccionMixta;

                                        corredorNorteSur[2][posIx] = objSeccionEmpty;
                                        corredorNorteSur[2][posIx-1] = objSeccionEmpty;
                                        corredorNorteSur[2][posIx-2] = objSeccionEmpty;

                                    //cruce    
                                    }else if(!corredorNorteSur[2][posIx+1].isEmpty() && corredorNorteSur[1][posIx+1].isEmpty()
                                            && corredorNorteSur[1][posIx+2].isEmpty() && corredorNorteSur[1][posIx+3].isEmpty()
                                            && corredorNorteSur[1][posIx].isEmpty()){

                                        corredorNorteSur[1][posIx+1] = seccionMixta;
                                        corredorNorteSur[1][posIx+2] = seccionMixta;
                                        corredorNorteSur[1][posIx+3] = seccionMixta;

                                        corredorNorteSur[2][posIx] = objSeccionEmpty;
                                        corredorNorteSur[2][posIx-1] = objSeccionEmpty;
                                        corredorNorteSur[2][posIx-2] = objSeccionEmpty;
                                        
                                    }

                                break;    

                            }

                        }

                        if(!corredorNorteSur[3][posIx].isEmpty()){
                                
                            corredorNorteSur[3][posIx+1] = seccionCarro;
                            corredorNorteSur[3][posIx] = objSeccionCarro;
                            
                        }

                        if(!corredorNorteSur[4][posIx].isEmpty() ){

                            corredorNorteSur[4][posIx+1] = seccionMasivo;
                            corredorNorteSur[4][posIx+2] = seccionMasivo;
                            corredorNorteSur[4][posIx+3] = seccionMasivo;
                            corredorNorteSur[4][posIx+4] = seccionMasivo;

                            corredorNorteSur[4][posIx] = objSeccionMasivo;
                            corredorNorteSur[4][posIx-1] = objSeccionMasivo;
                            corredorNorteSur[4][posIx-2] = objSeccionMasivo;
                            corredorNorteSur[4][posIx-3] = objSeccionMasivo;
                            
                        }
                    //}
                }    
            }
            
            // comenzamos los carros para el carril 1
            switch(tipoVehiculo){
                    
                case 1:

                    if(corredorNorteSur[1][0].isEmpty()){

                        corredorNorteSur[1][0] = seccionMixta; 


                    }else if (!corredorNorteSur[2][0].isEmpty()){

                        corredorNorteSur[2][0] = seccionMixta;

                    }else if (!corredorNorteSur[3][0].isEmpty()){

                        corredorNorteSur[2][0] = seccionCarro;
                    }

                break;    

                case 2:

                    if(corredorNorteSur[1][0].isEmpty() && corredorNorteSur[1][1].isEmpty()){

                        corredorNorteSur[1][0] = seccionMixta; 
                        corredorNorteSur[1][1] = seccionMixta; 

                    }else if(corredorNorteSur[2][0].isEmpty() && corredorNorteSur[2][1].isEmpty()){

                        corredorNorteSur[2][0] = seccionMixta; 
                        corredorNorteSur[2][1] = seccionMixta;

                    }

                break;

                case 3:

                    if(corredorNorteSur[1][0].isEmpty() && corredorNorteSur[1][1].isEmpty() && corredorNorteSur[1][2].isEmpty()){

                        corredorNorteSur[1][0] = seccionMixta; 
                        corredorNorteSur[1][1] = seccionMixta;
                        corredorNorteSur[1][2] = seccionMixta;

                    }else if(corredorNorteSur[2][0].isEmpty() && corredorNorteSur[2][1].isEmpty() && corredorNorteSur[2][2].isEmpty()){

                        corredorNorteSur[2][0] = seccionMixta; 
                        corredorNorteSur[2][1] = seccionMixta;
                        corredorNorteSur[2][2] = seccionMixta;

                    }

                break;

                case 4:

                    if(corredorNorteSur[4][0].isEmpty() && corredorNorteSur[4][1].isEmpty() && corredorNorteSur[4][2].isEmpty() && corredorNorteSur[4][3].isEmpty()){

                        corredorNorteSur[4][0] = seccionMasivo; 
                        corredorNorteSur[4][1] = seccionMasivo;
                        corredorNorteSur[4][2] = seccionMasivo;
                        corredorNorteSur[4][3] = seccionMasivo;
                    }

                break;    

            }//close switch
            
        }else{
        
        //                //corredor sur norte
                if(!corredorSurNorte[3][178].isEmpty()){
                    
                    corredorSurNorte[3][178] = objSeccionMasivo;
                    corredorSurNorte[3][177] = objSeccionMasivo;
                    corredorSurNorte[3][176] = objSeccionMasivo;
                    corredorSurNorte[3][175] = objSeccionMasivo;

                }

                if(!corredorSurNorte[2][178].isEmpty()){
                    corredorSurNorte[2][178] = objSeccionCarro;

                }

                if(!corredorSurNorte[1][178].isEmpty()){
                    
                    int tipo = corredorSurNorte[3][178].getObjVehiculo().getIdTipoVehiculo();
                    
                    switch(tipo){
                        
                        case 1:
                            corredorSurNorte[1][178] = objSeccionEmpty;
                        break;
                            
                        case 2:
                            
                            corredorSurNorte[1][178] = objSeccionEmpty;
                            corredorSurNorte[1][177] = objSeccionEmpty;
                            
                        break;
                            
                        case 3:
                            
                            corredorSurNorte[1][178] = objSeccionEmpty;
                            corredorSurNorte[1][177] = objSeccionEmpty;
                            corredorSurNorte[1][176] = objSeccionEmpty;
                        break;    
                    }
                }

                if(!corredorSurNorte[0][178].isEmpty()){
                    
                    int tipo = corredorSurNorte[0][178].getObjVehiculo().getIdTipoVehiculo();
                    
                    switch(tipo){
                        
                        case 1:
                            corredorSurNorte[0][178] = objSeccionEmpty;
                        break;
                            
                        case 2:
                            
                            corredorSurNorte[0][178] = objSeccionEmpty;
                            corredorSurNorte[0][177] = objSeccionEmpty;
                            
                        break;
                            
                        case 3:
                            
                            corredorSurNorte[0][178] = objSeccionEmpty;
                            corredorSurNorte[0][177] = objSeccionEmpty;
                            corredorSurNorte[0][176] = objSeccionEmpty;
                        break;    
                    }
                    
                    
                }

                //IMPLEMENTACION DE REGLAS para corredor sur norte  

                for(int posIx = 179; posIx >= 0; posIx--){

                    if(!corredorSurNorte[0][posIx].isEmpty()){
                        int tipoVehCurr1 = corredorSurNorte[0][posIx].getObjVehiculo().getIdTipoVehiculo();

                        //Seccion seccionMixta = new Seccion(true, objVehiculo, 2);
                        //Seccion movSoloCar = new Seccion(true, objVehiculo, 3);
                        //Seccion movSoloMasivo = new Seccion(true, objVehiculo, 4);

                        switch(tipoVehCurr1){

                            case 1:

                                if(corredorSurNorte[0][posIx+1].isEmpty() && corredorSurNorte[1][posIx].isEmpty()
                                    && corredorSurNorte[2][posIx+1].getTipoSeccion() == -2){

                                    corredorSurNorte[0][posIx+1] = seccionMixta;
                                    corredorSurNorte[0][posIx] = objSeccionEmpty; 
                                    System.out.println("adelanto");

                                }

                                //adelantar al frente
                                else if(corredorSurNorte[0][posIx+1].isEmpty() && !corredorSurNorte[1][posIx].isEmpty() 
                                        && corredorSurNorte[2][posIx+1].getTipoSeccion() == -2){

                                    corredorSurNorte[0][posIx+1] = seccionMixta;
                                    corredorSurNorte[0][posIx] = objSeccionEmpty; 

                                //cruzar    
                                }else if(corredorSurNorte[1][posIx+1].isEmpty() && !corredorSurNorte[0][posIx+1].isEmpty() 
                                        && corredorSurNorte[2][posIx+1].getTipoSeccion() == -2){

                                    corredorSurNorte[1][posIx+1]= seccionMixta;
                                    corredorSurNorte[0][posIx] = objSeccionEmpty;

                                }

                            break;

                            case 2:

                                if(corredorSurNorte[0][posIx+1].isEmpty() && corredorSurNorte[0][posIx+2].isEmpty() && corredorSurNorte[1][posIx].isEmpty()
                                    && corredorSurNorte[2][posIx+1].getTipoSeccion() == -2){

                                    corredorSurNorte[0][posIx+1] = seccionMixta;
                                    corredorSurNorte[0][posIx+2] = seccionMixta;

                                    corredorSurNorte[0][posIx-1] = objSeccionEmpty;
                                    corredorSurNorte[0][posIx] = objSeccionEmpty; 
                                    System.out.println("adelanto");

                                }

                                //adelantar al frente
                                else if(corredorSurNorte[0][posIx+1].isEmpty() && corredorSurNorte[0][posIx+2].isEmpty() && !corredorSurNorte[1][posIx].isEmpty() 
                                        && corredorSurNorte[2][posIx+1].getTipoSeccion() == -2){

                                    corredorSurNorte[0][posIx+1] = seccionMixta;
                                    corredorSurNorte[0][posIx+2] = seccionMixta;

                                    corredorSurNorte[1][posIx] = objSeccionEmpty;
                                    corredorSurNorte[1][posIx-1] = objSeccionEmpty; 

                                //cruzar    
                                }else if(corredorSurNorte[0][posIx+1].isEmpty() && corredorSurNorte[0][posIx+2].isEmpty() && !corredorSurNorte[1][posIx].isEmpty() 
                                        && corredorSurNorte[2][posIx+1].getTipoSeccion() == -2){

                                    corredorSurNorte[1][posIx+1]= seccionMixta;
                                    corredorSurNorte[1][posIx+2]= seccionMixta;

                                    corredorSurNorte[0][posIx] = objSeccionEmpty;
                                    corredorSurNorte[0][posIx-1] = objSeccionEmpty;

                                }                            
                            break;

                            case 3:


                                if(corredorSurNorte[0][posIx+1].isEmpty() && corredorSurNorte[0][posIx+2].isEmpty() && corredorSurNorte[0][posIx+3].isEmpty() &&
                                  corredorSurNorte[1][posIx].isEmpty()  && corredorSurNorte[2][posIx+1].getTipoSeccion() == -2){

                                    corredorSurNorte[1][posIx+1] = seccionMixta;
                                    corredorSurNorte[1][posIx+2] = seccionMixta;
                                    corredorSurNorte[1][posIx+3] = seccionMixta;

                                    corredorSurNorte[1][posIx] = objSeccionEmpty; 
                                    corredorSurNorte[1][posIx-1] = objSeccionEmpty; 
                                    corredorSurNorte[1][posIx-2] = objSeccionEmpty; 
                                    System.out.println("adelanto");

                                }

                                //adelantar al frente
                                else if(corredorSurNorte[1][posIx+1].isEmpty() && corredorSurNorte[1][posIx+2].isEmpty() && corredorSurNorte[1][posIx+3].isEmpty()  
                                        && corredorSurNorte[0][posIx+1].getTipoSeccion() == -2 && !corredorSurNorte[2][posIx].isEmpty()){

                                    corredorSurNorte[1][posIx+1] = seccionMixta;
                                    corredorSurNorte[1][posIx+2] = seccionMixta;
                                    corredorSurNorte[1][posIx+3] = seccionMixta;

                                    corredorSurNorte[1][posIx] = objSeccionEmpty;
                                    corredorSurNorte[1][posIx-1] = objSeccionEmpty;
                                    corredorSurNorte[1][posIx-1] = objSeccionEmpty;

                                //cruzar    
                                }else if(corredorSurNorte[2][posIx+1].isEmpty() && corredorSurNorte[2][posIx+2].isEmpty() && corredorSurNorte[2][posIx+3].isEmpty() && !corredorSurNorte[1][posIx+1].isEmpty() 
                                        && corredorSurNorte[0][posIx+1].getTipoSeccion() == -2){

                                    corredorSurNorte[2][posIx+1]= seccionMixta;
                                    corredorSurNorte[2][posIx+2]= seccionMixta;
                                    corredorSurNorte[2][posIx+3]= seccionMixta;

                                    corredorSurNorte[1][posIx] = objSeccionEmpty;
                                    corredorSurNorte[1][posIx-1] = objSeccionEmpty;
                                    corredorSurNorte[1][posIx-2] = objSeccionEmpty;

                                }

                            break;    

                        }

                    }

                    if(!corredorSurNorte[1][posIx].isEmpty()){
                        int tipoVehCurr2 = corredorSurNorte[2][posIx].getObjVehiculo().getIdTipoVehiculo();

                        //Seccion seccionMixta = new Seccion(true, objVehiculo, 2);
                        

                        switch(tipoVehCurr2){

                            case 1:

                                if(corredorSurNorte[1][posIx+1].isEmpty() && corredorSurNorte[1][posIx].isEmpty()
                                    && corredorSurNorte[3][posIx+1].getTipoSeccion() == 3){

                                    corredorSurNorte[2][posIx+1] = seccionMixta;
                                    corredorSurNorte[2][posIx] = objSeccionEmpty; 
                                    System.out.println("adelanto");

                                }

                                //adelantar al frente
                                else if(corredorSurNorte[2][posIx+1].isEmpty() && !corredorSurNorte[1][posIx].isEmpty() 
                                        && corredorSurNorte[3][posIx+1].getTipoSeccion() == 3){

                                    corredorSurNorte[2][posIx+1] = seccionMixta;
                                    corredorSurNorte[2][posIx] = objSeccionEmpty; 

                                //cruzar    
                                }else if(corredorSurNorte[1][posIx+1].isEmpty() && !corredorSurNorte[2][posIx+1].isEmpty() 
                                        && corredorSurNorte[3][posIx+1].getTipoSeccion() == -2){

                                    corredorSurNorte[1][posIx+1]= seccionMixta;
                                    corredorSurNorte[2][posIx] = objSeccionEmpty;

                                }

                            break;

                            case 2:

                                if(corredorSurNorte[2][posIx+1].isEmpty() && corredorSurNorte[2][posIx+2].isEmpty() && corredorSurNorte[1][posIx].isEmpty()
                                    && corredorSurNorte[3][posIx+1].getTipoSeccion() == 3){

                                    corredorSurNorte[1][posIx+1] = seccionMixta;
                                    corredorSurNorte[1][posIx+2] = seccionMixta;

                                    corredorSurNorte[1][posIx-1] = objSeccionEmpty;
                                    corredorSurNorte[1][posIx] = objSeccionEmpty; 
                                    System.out.println("adelanto");

                                }

                                //adelantar al frente
                                else if(corredorSurNorte[2][posIx+1].isEmpty() && corredorSurNorte[2][posIx+2].isEmpty() && !corredorSurNorte[1][posIx].isEmpty() 
                                        && corredorSurNorte[3][posIx+1].getTipoSeccion() == 3){

                                    corredorSurNorte[2][posIx+1] = seccionMixta;
                                    corredorSurNorte[2][posIx+2] = seccionMixta;

                                    corredorSurNorte[2][posIx] = objSeccionEmpty;
                                    corredorSurNorte[2][posIx] = objSeccionEmpty; 

                                //cruzar    
                                }else if(corredorSurNorte[1][posIx+1].isEmpty() && corredorSurNorte[1][posIx+2].isEmpty() && !corredorSurNorte[2][posIx+1].isEmpty() 
                                        && corredorSurNorte[3][posIx+1].getTipoSeccion() == 3){

                                    corredorSurNorte[1][posIx+1]= seccionMixta;
                                    corredorSurNorte[1][posIx+2]= seccionMixta;

                                    corredorSurNorte[2][posIx] = objSeccionEmpty;
                                    corredorSurNorte[2][posIx-1] = objSeccionEmpty;

                                }                            
                            break;

                            case 3:


                                if(corredorSurNorte[2][posIx+1].isEmpty() && corredorSurNorte[2][posIx+2].isEmpty() && corredorSurNorte[2][posIx+3].isEmpty() &&
                                  corredorSurNorte[1][posIx].isEmpty()  && corredorSurNorte[3][posIx+1].getTipoSeccion() == 3){

                                    corredorSurNorte[2][posIx+1] = seccionMixta;
                                    corredorSurNorte[2][posIx+2] = seccionMixta;
                                    corredorSurNorte[2][posIx+3] = seccionMixta;

                                    corredorSurNorte[2][posIx] = objSeccionEmpty; 
                                    corredorSurNorte[2][posIx-1] = objSeccionEmpty; 
                                    corredorSurNorte[2][posIx-2] = objSeccionEmpty; 
                                    System.out.println("adelanto");

                                }

                                //adelantar al frente
                                else if(corredorSurNorte[2][posIx+1].isEmpty() && corredorSurNorte[2][posIx+2].isEmpty() && corredorSurNorte[2][posIx+3].isEmpty()  
                                        && corredorSurNorte[0][posIx+1].getTipoSeccion() == -2 && !corredorSurNorte[2][posIx].isEmpty()){

                                    corredorSurNorte[2][posIx+1] = seccionMixta;
                                    corredorSurNorte[2][posIx+2] = seccionMixta;
                                    corredorSurNorte[2][posIx+3] = seccionMixta;

                                    corredorSurNorte[2][posIx] = objSeccionEmpty;
                                    corredorSurNorte[2][posIx-1] = objSeccionEmpty;
                                    corredorSurNorte[2][posIx-1] = objSeccionEmpty;

                                //cruzar    
                                }else if(corredorSurNorte[1][posIx+1].isEmpty() && corredorSurNorte[1][posIx+2].isEmpty() && corredorSurNorte[1][posIx+3].isEmpty() && !corredorSurNorte[2][posIx+1].isEmpty() 
                                        && corredorSurNorte[3][posIx+1].getTipoSeccion() == 3){

                                    corredorSurNorte[1][posIx+1]= seccionMixta;
                                    corredorSurNorte[1][posIx+2]= seccionMixta;
                                    corredorSurNorte[1][posIx+3]= seccionMixta;

                                    corredorSurNorte[0][posIx] = objSeccionEmpty;
                                    corredorSurNorte[0][posIx-1] = objSeccionEmpty;
                                    corredorSurNorte[0][posIx-2] = objSeccionEmpty;

                                }else if(corredorSurNorte[0][posIx+1].isEmpty() && corredorSurNorte[0][posIx+2].isEmpty() && corredorSurNorte[0][posIx+3].isEmpty() && !corredorSurNorte[1][posIx+1].isEmpty() 
                                        && corredorSurNorte[2][posIx+1].getTipoSeccion() == -2){
                                    
                                    corredorSurNorte[0][posIx+1]= seccionMixta;
                                    corredorSurNorte[0][posIx+2]= seccionMixta;
                                    corredorSurNorte[0][posIx+3]= seccionMixta;

                                    corredorSurNorte[1][posIx] = objSeccionEmpty;
                                    corredorSurNorte[1][posIx-1] = objSeccionEmpty;
                                    corredorSurNorte[1][posIx-2] = objSeccionEmpty;
                                }

                            break;

                        }
                    }
                    
                    if(!corredorSurNorte[2][posIx].isEmpty()){
                        
                        Seccion movCar = new Seccion(true, objVehiculo, 3);
                        corredorSurNorte[2][posIx+1] = movCar;
                        corredorSurNorte[2][posIx] = objSeccionCarro;        
                        
                    }
                    
                    if(!corredorSurNorte[3][posIx].isEmpty()){

                        Seccion movMasivo = new Seccion(true, objVehiculo, 4);
                        
                        corredorSurNorte[3][posIx+1] = movMasivo;
                        corredorSurNorte[3][posIx+2] = movMasivo;
                        corredorSurNorte[3][posIx+3] = movMasivo;
                        corredorSurNorte[3][posIx+4] = movMasivo;
                                
                        corredorSurNorte[3][posIx] = objSeccionMasivo;
                        corredorSurNorte[3][posIx-1] = objSeccionMasivo;
                        corredorSurNorte[3][posIx-2] = objSeccionMasivo;
                        corredorSurNorte[3][posIx-3] = objSeccionMasivo;                               
                    }
                }//close for
        
        
        }//cierre validacion nombre de corredor
        
    }//cierre metodo
    
    /*SUB FUNCIONES PARA EL AMBIENTE*/
    
    private static int [] conteoColas(Seccion [][] objSeccion){
        
       //int [] colasNS = new int[3];
        int [] colasNS = {0,0,0,0,0}; 
        int [] colasSN = new int[3];
        int colaIx = 178;
        int carrilIx = 1;
        int contadorVehiculosNS = 0;
       if(objSeccion.equals(corredorNorteSur)){
           
           while(carrilIx<5){
               contadorVehiculosNS = 0;
                while(colaIx >= 0 ){

                   if(!corredorNorteSur[carrilIx][colaIx].isEmpty()){

                        switch(corredorNorteSur[carrilIx][colaIx].getObjVehiculo().getIdTipoVehiculo()){

                            case 1:

                               contadorVehiculosNS++;
                               colaIx--;
                            break;    

                            case 2:

                               contadorVehiculosNS++;
                               colaIx--;
                               colaIx--;

                            break;

                            case 3:

                               contadorVehiculosNS++;
                               colaIx--;
                               colaIx--;
                               colaIx--;

                            break;  
                                
                        case 4:

                               contadorVehiculosNS++;
                               colaIx--;
                               colaIx--;
                               colaIx--;
                               colaIx--;

                            break;        
                        }

                       colasNS[carrilIx] = contadorVehiculosNS;

                   }else{
                   
                   colaIx--;
                   
                   }//cierre validacion linea 647 

               }//cierre while   
               
               carrilIx++;
           }
           
            
           
       }else {
           JOptionPane.showMessageDialog(null, "no son iguales");
       
       }
        
        
        return colasNS;
        
    }
    
    
}
