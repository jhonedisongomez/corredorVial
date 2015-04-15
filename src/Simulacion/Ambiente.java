/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */

/*
    autor:          Jhon Edison Gomez      
    fecha:          14-Abil-2014       
    descripcion:    creacion funcion actualizar semaforo, falta implementar
                    conteo para la cola, e ingresar los carros a cada via
                


*/


package Simulacion;

import java.util.ArrayList;

/**
 *
 * @author sergio
 */
public class Ambiente {
    
    // corredores viales
    private static int[][] corredorNorteSur;
    private static int [][] corredorSurNorte;
    
    // colas corredor norte sur
    private ArrayList cola1CorredorNS;
    private ArrayList cola2CorredorNS;
    private ArrayList cola3CorredorNS;
    private ArrayList cola4CorredorNS;
    private ArrayList cola5CorredorNS;
    private ArrayList colaCruce1CorredorNS;
    private ArrayList colaCruce2CorredorNS;
    private ArrayList colaCruce3CorredorNS;
    private ArrayList colaCruce4CorredorNS;
    
    // colas corredor sur norte
    private ArrayList cola1CorredorSN;
    private ArrayList cola2CorredorSN;
    private ArrayList cola3CorredorSN;
    private ArrayList cola4CorredorSN;
    private ArrayList cola5CorredorSN;
    private ArrayList colaDesvioCorredorSN;
    
    /* PENDIENTE: oreja inicial corredor SN se necesita cola?
       en el desvio tambien se necesita cola?
        que tipos de carros pueden entrar al desvio?
    */
    
    public Ambiente() {
        corredorNorteSur = new int[5][180];
        // carril 3 -> -2 hasta casilla 52
        corredorSurNorte = new int[4][180];
        
        // Inicializar los dos corredores
        // i filas, j columnas
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 180; j++) {
                if (i == 0) {// se setean los limites del corredor norte sur y los cruces
                    if ( j == (40 - 1) || j == (62 -1) || j == (101 - 1) || j == (144 - 1) )
                        corredorNorteSur[0][j] = 0;
                    else // valor no valido para posicionar un vehiculo
                        corredorNorteSur[0][j] = -2;
                }
                else if (i == 2 && j < 52) { // tercer carril Sur - Norte, no existe
                    corredorSurNorte[i][j] = -2;
                }
                else if (i < 4 && j < 179) {
                    corredorSurNorte[i][j] = 0;
                    corredorNorteSur[i][j] = 0;
                    
                }// indicadores de semaforos
                else if ( (i == 1 || i == 2 || i == 3) && j == 179) {
                    corredorSurNorte[i][j] = -1;
                    corredorNorteSur[i + 1][j] = -1;
                    
                }
                else if (j < 179) { // para el cuarto carril
                    corredorNorteSur[i][j] = 0;
                }
            }
        }
        
        /**/
        System.out.println("CORREDOR NORTE - SUR");
        for (int i = 0; i < 180; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(corredorNorteSur[j][i] + " ");
            }
            System.out.println("L" + (i+1));
        }
        
        //semaforo en rojo
        ActualizarSemaforo(false);

        System.out.println("\nCORREDOR SUR - NORTE");
        for (int i = 0; i < 180; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(corredorSurNorte[j][i] + " ");
            }
            System.out.println("L" + (i + 1));
            
        }
        
        //semaforo en rojo
        ActualizarSemaforo(false);
        
    }
    
    /*
    funcion para cambiar el semaforo automaticamente
    la luz del semaforo cambia despues de terminar
    el temporizador

    true    = verde
    false   = rojo    
    
    */
    public void ActualizarSemaforo(boolean semaforo){

        if(semaforo){
        
            for(int conteo = 5; conteo >= 0; conteo--){
            
                //para cambiar semaforo a rojo
                if(conteo == 0){
                    semaforo = false;
                }

                System.out.println(semaforo+" " + conteo);
                timer();
                
            }
            
        }else{

            //para cambiar a rojo
            for(int conteo = 0 ; conteo<= 5;conteo++){

                if(conteo == 5){
                    semaforo = true;
                }            

                System.out.println(semaforo+" " + conteo);
                timer();
            }
        
        }
                
    }
    
    public void actualizarCola(){
        
        
    }
    
    // para darle un tiempod de un segundo al for del cambio del semaforo
    public static void timer() {
     
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
     
    }     
    
}
