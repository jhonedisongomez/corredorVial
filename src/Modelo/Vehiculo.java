/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author sergio
 */
public abstract class Vehiculo {
    
    private int idTipoVehiculo;
    private int longitudVehiculo;
    /* array de velocidades
        Velocidad 4 : 4 casillas/s = 66 km/h
        Velocidad 3 : 3 casillas/s = 50 km/h
        Velocidad 2 : 2 Casillas/s = 33 km/h
        Velocidad 1 : 1 Casilla/s  = 17 Km/h
    */
    private static final int[] velocidades = {1, 2, 3, 4, 5};

    public Vehiculo(int idTipoVehiculo, int longitudVehiculo) {
        this.idTipoVehiculo = idTipoVehiculo;
        this.longitudVehiculo = longitudVehiculo;
    }
    
    /**
     * @return the idTipoVehiculo
     */
    public int getIdTipoVehiculo() {
        return idTipoVehiculo;
    }

    /**
     * @return the longitudVehiculo
     */
    public int getLongitudVehiculo() {
        return longitudVehiculo;
    }
    
    /**
     *
     */
    public void avanzarFrente() {
        
    }
    
    /**
     *
     */
    public void adelantarDerecha() {
    
    }
    
    public void adelantarIzquierda() {
        
    }
    
    //todos menos el masivo
    public void tomarCuadra() {
        
    }
    
    public void frenar() {
        
    }
    
    /**
     carros
     */
    public void tomarDesvio() {
    
    }
}
