/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
    
    fecha:          19-Abril-2015
    descripcion:    implementacion asignarCarrilNS desde el archivo ambiente


 */
package Simulacion;

import Modelo.Buseta;
import Modelo.Camion;
import Modelo.Carro;
import Modelo.Masivo;
import Modelo.Vehiculo;
import java.util.ArrayList;

/**
 *
 * @author sergio
 */
public class Controlador {
    
    static int carros=0, busetas=0, camiones=0, masivos=0;
    
    public static void main(int[] horarioGui, ArrayList vehiculosSimulacion){
        // configuracion de la simulacion
        Horario hr = new Horario(horarioGui);
        int[] tiemposSimulacion = Horario.getTiempoSimulacion();
        double[][] probabilidadesSN = Horario.getProbabilidadesSN();
        double[][] probabilidadesNS = Horario.getProbabilidadesNS();
        
        // se configura el ambiente con los datos de la interfaz
        Ambiente a = new Ambiente();
         
        // configurar colas, probabilidad de frenado
        //a.actualizarCola();
        
        // simulacion de los tres horarios, si el tiempo es cero, no devuelve nada
        for (int i = 0; i < tiemposSimulacion.length; i++) {
            System.out.println("Simulacion horario " + (i + 1));
            simular(i, tiemposSimulacion[i], vehiculosSimulacion, a, probabilidadesSN, probabilidadesNS);
        }
        
        // resultados
        // VELOCIDAD PROMEDIO DEL CORREDOR X FRANJA
        // PROMEDIO DE COLAS POR CARRIL X X FRANJA X CORREDOR
        // NUMERO DE VEHICULOS X CORREDOR X FRANJA
        
    }
    
    private static void simular(int horario, int tiempo, ArrayList tipoVehiculo, Ambiente a, double[][] probabilidadesSN, double[][] probabilidadesNS) {
        /**
        SIMULACION
        */
        
        //guardar objetos en vectores para distinguir los carros de los corredores
        //Vector<Carro> vehiculoNSVector = new Vector<Carro>();
        //Vector<Vehiculo> vehiculoSNVector = new Vector<Vehiculo>();
        
        int [][] datosNS = new int[tiempo][1];
        
        //corredor norte sur
        int carroNS=0, busetaNS=0, camionNS=0, masivoNS=0;
        int carroSN=0, busetaSN=0, camionSN=0, masivoSN=0;
        Vehiculo vehiculoNS;
        Vehiculo vehiculoSN;
        //int reloj = 0;
        
            
        for(int reloj = 0; reloj < tiempo; reloj++) {
        //System.out.println(reloj);
        // corredor SN
        // generar aleatoriamente un vehiculo
        vehiculoSN = generarVehiculo(horario, probabilidadesSN);

        if(vehiculoSN != null){
            switch(vehiculoSN.getIdTipoVehiculo()){

                case 1:
                    carroSN++;
                break;    

                case 2:
                    busetaSN++;
                break;

                case 3:
                    camionSN++;
                break;

                case 4:
                    masivoSN++;
                break;    

            }//cierre switch    

            //datosNS [reloj][x] = vehiculoSN.getIdTipoVehiculo();
            Ambiente.actualizarAmbiente(vehiculoSN,reloj);
        }//cierre condicion null

        // ubicarlo segun su tipoVehiculo en un carril
        // actualizar ambiente (semaforos, colas) -> aplicar reglas
        
        
        // corredor NS
        // generar aleatoriamente un vehiculo
            vehiculoNS = generarVehiculo(horario, probabilidadesNS);
            //vehiculoSNVector.add(reloj, vehiculoNS
            if(vehiculoNS != null){

                switch(vehiculoNS.getIdTipoVehiculo()){

                    case 1:
                        carroNS++;
                    break;    

                    case 2:
                        busetaNS++;
                    break;

                    case 3:
                        camionNS++;
                    break;

                    case 4:
                        masivoNS++;
                    break;     

                }                
            }
         // ubicarlo segun su tipoVehiculo en un carril
        // actualizar ambiente (semaforos, colas) -> aplicar reglas   

        }
        
//        int [][] actualizacion = a.asignarCarrilNS(datosNS);  
//        for(int x = 0 ; x < actualizacion.length ; x++){
//            for(int j = 0;j < actualizacion[x].length; j++){
//                    System.out.print(actualizacion[x][j] + " ");
//                //System.out.print(actualizacion[x][j]);
//            
//            }
//            System.out.println("L" + (x + 1));
//        }
        
        //System.out.println("corredor SN"+vehiculoSNVector.size());
        //System.out.println("corredor NS"+vehiculoNSVector.size());
        
        //muestra los valores para el corredor norte sur
        System.out.println("norte sur " + carroNS + " " + busetaNS + " " + camionNS + " " + masivoNS);        
        //muestra los valores para el corredor sur norte
        System.out.println("sur norte " + carroSN + " " + busetaSN + " " + camionSN + " " + masivoSN);
        
    }
    
    private static Vehiculo generarVehiculo(int horario, double[][] probabilidades) {
        double aleatorio = Math.random();
        
        // condiciones para determinar el tipo de vehiculo de acuerdo al horario
        if(aleatorio <= probabilidades[horario][0]   && aleatorio >= probabilidades[horario][1]
                                                     && aleatorio >= probabilidades[horario][2]
                                                     && aleatorio >= probabilidades[horario][3]) {
            carros++;
            return new Carro(1,1,1,0);
        }
        else if (aleatorio <= probabilidades[horario][1]    && aleatorio >= probabilidades[horario][2]
                                                            && aleatorio >= probabilidades[horario][3]) {
            busetas++;
            return new Buseta(2,2,2,0);
        }
        else if (aleatorio <= probabilidades[horario][2] && aleatorio >= probabilidades[horario][3]) {
            camiones++;
            return new Camion(3,3,3,0);
        }
        else if (aleatorio <= probabilidades[horario][3]) {
            masivos++;
            return new Masivo(4,4,4,0);
        }
        /*else {
            generarVehiculo(horario, probabilidades);
        }*/
        return null;
    }
}
