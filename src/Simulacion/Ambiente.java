
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

import Modelo.Vehiculo;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author sergio
 */
public class Ambiente {
    
    int espacio = 0;
    //tipos de vehiculo para la el tipo de disponibilidad del carril
    Vehiculo objVehiculoVacio = new Vehiculo(0, 0, 0, 0); 
    Vehiculo objVehiculoSemaforo = new Vehiculo(0, 0, -1, 0);
    Vehiculo objVehiculoDesvio = new Vehiculo(0, 0, -2, 0);
    
    
    // corredores viales
    private static Vehiculo[][] corredorNorteSur;
    private static Vehiculo [][] corredorSurNorte;
    
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
        corredorNorteSur = new Vehiculo[5][180];
        // carril 3 -> -2 hasta casilla 52
        corredorSurNorte = new Vehiculo[4][180];
            
        //todos tienen por defecto un vehiculo vacio
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 180; j++){
                
                if(i< 4){
                    corredorSurNorte[i][j] = objVehiculoVacio;
                }
                
                corredorNorteSur[i][j] = objVehiculoVacio;
                
                
            }
        }
        
        // Inicializar los dos corredores
        // i filas, j columnas
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 180; j++) {
                if (i == 0) {// se setean los limites del corredor norte sur y los cruces
                    if ( j == (40 - 1) || j == (62 -1) || j == (101 - 1) || j == (144 - 1) )
                        corredorNorteSur[0][j] = objVehiculoVacio;
                    else // valor no valido para posicionar un vehiculo
                        corredorNorteSur[0][j] = objVehiculoDesvio;
                }
                else if (i == 2 && j < 52) { // tercer carril Sur - Norte, no existe
                    corredorSurNorte[i][j] = objVehiculoDesvio;
                }
                else if (i < 4 && j < 179) {
                    corredorSurNorte[i][j] = objVehiculoVacio;
                    corredorNorteSur[i][j] = objVehiculoVacio;
                    
                }// indicadores de semaforos
                else if ( (i == 1 || i == 2 || i == 3) && j == 179) {
                    corredorSurNorte[i][j] = objVehiculoSemaforo;
                    corredorNorteSur[i + 1][j] = objVehiculoSemaforo;
                    
                }
                else if (j < 179) { // para el cuarto carril
                    corredorNorteSur[i][j] = objVehiculoVacio;
                }
            }
        }
        
        /**/
        System.out.println("CORREDOR NORTE - SUR");
        for (int i = 0; i < 180; i++) {
            for (int j = 0; j < 5; j++) {
                espacio = corredorNorteSur[j][i].getEspacio();
                //JOptionPane.showMessageDialog(null, corredorNorteSur[j][i].getEspacio());
                System.out.print(espacio + " ");
            }
            System.out.println("L" + (i+1));
        }
        
        System.out.println("\nCORREDOR SUR - NORTE");
        for (int i = 0; i < 180; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(corredorSurNorte[j][i].getEspacio() + " ");
            }
            System.out.println("L" + (i + 1));
            
        }
        
    }
    
    /**
     * @param objVehiculo  : el carro generado por el controlador
     * @param tiempo : tiempo en el cual se esta ejecutando la simulacion
     **/
    public static void actualizarAmbiente(Vehiculo objVehiculo, int tiempo){
        
        int tipoVehiculo = objVehiculo.getIdTipoVehiculo();
        //boolean recorrer = true;
        
        //para implementar los tiempos
        if(tiempo !=0){
            
            //recorre los carriles para ir ocupando los espacios con los carros
            for(int carrIx =3; carrIx >=0;carrIx--){        //recorre los carriles
                for(int posIx = 178 ; posIx >=0; posIx--){  //para el tamaño del corredor       

                    //implementacion de las reglas

                    //adelantar al frente
                    if(carrIx+1 <= 4 && posIx+1 < 179){

                        if(corredorNorteSur[carrIx][posIx].getEspacio()> 0 && corredorNorteSur[carrIx][posIx+1].getEspacio()== 0
                                && (corredorNorteSur[carrIx+1][posIx].getEspacio() > 0 || corredorNorteSur[carrIx-1][posIx].getEspacio() == -2 )){
                           
                            
                            Vehiculo.avanzarFrente(corredorNorteSur, carrIx, posIx);

                        //para los cruces de los carriles        
                        }else{
                            
                            if(carrIx == 2 || carrIx == 1){
                                
                                if(corredorNorteSur[2][posIx].getEspacio() > 0 &&  corredorNorteSur[1][posIx+1].getEspacio() == 0 
                                    &&  corredorNorteSur[1][posIx].getEspacio() == 0  && corredorNorteSur[2][posIx+1].getEspacio() > 0){

                                    Vehiculo.adelantarDerecha(corredorNorteSur, carrIx, posIx);

                                //cruce izquierda    
                                }else if(corredorNorteSur[1][posIx].getEspacio() > 0 &&  corredorNorteSur[2][posIx+1].getEspacio() == 0 
                                    &&  corredorNorteSur[2][posIx].getEspacio() == 0  && corredorNorteSur[1][posIx+1].getEspacio() > 0){

                                    Vehiculo.adelantarIzquierda(corredorNorteSur, carrIx, posIx);

                                }    
                            }
                        }
                    }
                }
            }            
        }
        
        //comienzo de los carriles
        switch(tipoVehiculo){
                
            case 4:
                if(corredorNorteSur[4][0].getEspacio() == 0 && corredorNorteSur[4][1].getEspacio() == 0 
                        && corredorNorteSur[4][2].getEspacio() == 0 && corredorNorteSur[4][3].getEspacio() == 0){
                    
                    corredorNorteSur[4][0] = objVehiculo;
                    corredorNorteSur[4][1] = objVehiculo;
                    corredorNorteSur[4][2] = objVehiculo;
                    corredorNorteSur[4][3] = objVehiculo;
                    System.out.println("comiezo carril 4");
                    //recorrer = false;
                }
                
            break;    

            case 3:
                
                if(corredorNorteSur[2][0].getEspacio() == 0 && corredorNorteSur[2][1].getEspacio() == 0 && corredorNorteSur[2][2].getEspacio() == 0){
                   
                   corredorNorteSur[2][0] = objVehiculo;
                   corredorNorteSur[2][1] = objVehiculo;
                   corredorNorteSur[2][2] = objVehiculo;
                   System.out.println("comiezo carril 2");
                   //recorrer = false;
                   
                }else if(corredorNorteSur[1][0].getEspacio() == 0 && corredorNorteSur[1][1].getEspacio() == 0 && corredorNorteSur[1][2].getEspacio() == 0){
                    
                    corredorNorteSur[1][0] = objVehiculo;
                    corredorNorteSur[1][1] = objVehiculo;
                    corredorNorteSur[1][2] = objVehiculo;
                    System.out.println("comiezo carril 1");
                    //recorrer = false;
                }
                
            break;

            case 2:
                if(corredorNorteSur[2][0].getEspacio() == 0 && corredorNorteSur[2][1].getEspacio() == 0){
                   
                   corredorNorteSur[2][0] = objVehiculo;
                   corredorNorteSur[2][1] = objVehiculo;
                   System.out.println("comiezo carril 2");
                   //recorrer = false;
                   
                }else if(corredorNorteSur[1][0].getEspacio() == 0 && corredorNorteSur[1][1].getEspacio() == 0){
                    
                    corredorNorteSur[1][0] = objVehiculo;
                    corredorNorteSur[1][1] = objVehiculo;
                    System.out.println("comiezo carril 1");
                    //recorrer = false;
                }
                
            break;
            
            //para carros    
            case 1:
                
                //para el unico carril
                if(corredorNorteSur[3][0].getEspacio() == 0){
                   
                    corredorNorteSur[3][0] = objVehiculo;
                    System.out.println("comiezo carril 3");
                    //recorrer = false;
                
                //para el carril 1    
                }else if(corredorNorteSur[2][0].getEspacio() == 0){
                    
                    corredorNorteSur[2][0] = objVehiculo;
                    System.out.println("comiezo carril 2");
                    //recorrer = false;
                
                //para el carril 2    
                }else if(corredorNorteSur[1][0].getEspacio() == 0){
                    
                    corredorNorteSur[1][0] = objVehiculo;
                    System.out.println("comiezo carril 1");
                    //recorrer = false;
                }
                
            break;     

        }// cierre de switch
        
        //revisar la ultima posicion
        if(corredorNorteSur[4][178].getEspacio() > 0){
            
            corredorNorteSur[4][178] = objVehiculo;
            System.out.println("salio del corredor");
            JOptionPane.showMessageDialog(null, "salio 4");
        }        

        if(corredorNorteSur[3][178].getEspacio() > 0){
            
            corredorNorteSur[3][178] = objVehiculo;
            System.out.println("salio del corredor 3");
            
        }
        
        if(corredorNorteSur[2][178].getEspacio() >0 ){
            
            corredorNorteSur[2][178] = objVehiculo;
            System.out.println("salio del corredor");
            JOptionPane.showMessageDialog(null, "salio 2");    
        }
        
        if(corredorNorteSur[1][178].getEspacio() > 0){
            
            corredorNorteSur[1][178] = objVehiculo;
            System.out.println("salio del corredor");
            JOptionPane.showMessageDialog(null, "salio 1");    
        }
        
         
    }//cierre del metodo
    
    /*SUB-FUNCIONES PARA UTILIZAR EL AMBIENTE*/
    
}
