/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        /*
        SIMULACION
        */
        Vehiculo v;
        int reloj = 0;
        while (reloj++ < tiempo) {
            //System.out.println(reloj);
            // corredor SN
            // generar aleatoriamente un vehiculo
            v = generarVehiculo(horario, probabilidadesSN);
            
            // ubicarlo segun su tipoVehiculo en un carril
            // actualizar ambiente (semaforos, colas) -> aplicar reglas
            
            // corredor NS
            // generar aleatoriamente un vehiculo
            //v = generarVehiculo(horario, probabilidadesSN);
            //System.out.println("norte sur" + carros + " " + busetas + " " + camiones + " " + masivos);
            // ubicarlo segun su tipoVehiculo en un carril
            // actualizar ambiente (semaforos, colas) -> aplicar reglas
        }
        System.out.println("sur norte " + carros + " " + busetas + " " + camiones + " " + masivos);
    }
    
    private static Vehiculo generarVehiculo(int horario, double[][] probabilidades) {
        double aleatorio = Math.random();
        
        // condiciones para determinar el tipo de vehiculo de acuerdo al horario
        if(aleatorio <= probabilidades[horario][0]   && aleatorio >= probabilidades[horario][1]
                                                     && aleatorio >= probabilidades[horario][2]
                                                     && aleatorio >= probabilidades[horario][3]) {
            carros++;
            return new Carro(1,1);
        }
        else if (aleatorio <= probabilidades[horario][1]    && aleatorio >= probabilidades[horario][2]
                                                            && aleatorio >= probabilidades[horario][3]) {
            busetas++;
            return new Buseta(2,2);
        }
        else if (aleatorio <= probabilidades[horario][2] && aleatorio >= probabilidades[horario][3]) {
            camiones++;
            return new Camion(3,3);
        }
        else if (aleatorio <= probabilidades[horario][3]) {
            masivos++;
            return new Masivo(4,4);
        }
        /*else {
            generarVehiculo(horario, probabilidades);
        }*/
        return null;
    }
}
