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
                
    autor:          Jhon Edison Gomez      
    fecha:          15-Abil-2014       
    descripcion:    Implementacion actualizar cola, preguntar por que los 
                    arrayList para la colas y como inserto un vehiculo
                    en los corredore viales
           

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
        que tipos de carros pueden entrar al desvio? --> vehiculos
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
        actualizarCola();
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
    /*funcion para contar cuanta cola o cuantos carros quedan en 
    en un semaforo en rojo    */
    public static int actualizarCola(){
    
        //corredor norte sur
        int totalNSCarril1 = 0;
        int totalNSCarril2 = 0;
        int totalNSCarril3 = 0;
        int totalNSCarril4 = 0;    
        
        //corredor sur norte
        int totalSNCarril1 = 0;
        int totalSNCarril2 = 0;
        int totalSNCarril3 = 0;
        int totalSNCarril4 = 0;       
        
        //totales
        int total   = 0;
        int totalNS = 0;
        int totalSN = 0; 
    
        for(int x = 1 ; x < corredorNorteSur.length; x++){
            
            for(int y = 179; y >0 ; y--){
                System.out.println(y +"y " + corredorNorteSur[x][y]);
                if(corredorNorteSur[x][y] ==1){
                    totalNSCarril1++;
                    System.out.println(y +"y " + corredorNorteSur[x][y]);
                    
                }
                //System.out.println(y +" " + corredorNorteSur[x][y]);
            }
            for(int a = 179; a > 0; a--){
                System.out.println(a +"a " + corredorNorteSur[x][a]);
                if(corredorNorteSur[x][a] ==1){
                    totalNSCarril2++;
                    
                }
            }
            
            for(int b = 179; b > 0; b--){
                System.out.println(b +"b " + corredorNorteSur[x][b]);
                if(corredorNorteSur[x][b] ==1){
                    totalNSCarril3++;
                    
                }
            }
            
            for(int c = 179; c > 0; c--){
                System.out.println(c +"c " + corredorNorteSur[x][c]);
                if(corredorNorteSur[x][c] ==1){
                    totalNSCarril4++;
                    
                }
            }            
        }// cierre for x
        
        for(int x = 0 ; x < corredorSurNorte.length; x++){
            
            for(int y = 179; y >0 ; y--){
                if(corredorSurNorte[x][y] ==1){
                    totalSNCarril1++;
                }
                //System.out.println(y +" " + corredorNorteSur[x][y]);
            }
            for(int a = 179; a > 0; a--){
                if(corredorSurNorte[x][a] ==1){
                    totalSNCarril2++;
                }
            }
            
            for(int b = 179; b > 0; b--){
                if(corredorSurNorte[x][b] ==1){
                    totalSNCarril3++;
                }
            }
            
            for(int c = 179; c > 0; c--){
                if(corredorSurNorte[x][c] ==1){
                    totalSNCarril4++;
                }
            }            
        }// cierre for x
        
        //obtemos los totales
        totalNS = totalNSCarril1 + totalNSCarril2 + totalNSCarril3 + totalNSCarril4;
        totalSN = totalSNCarril1 + totalSNCarril2 + totalSNCarril3 + totalSNCarril4;
        
        //total de todos los autos que estan en la cola en los corredores
        total = totalNS +  totalSN;
        return total;
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
