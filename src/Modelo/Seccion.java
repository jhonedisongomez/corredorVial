package Modelo;

import Modelo.Vehiculo;

public class Seccion {
	
    //para verificar si esta null
    private boolean	isEmpty;

    //vehiculo con el que se trabajara
    private Vehiculo objVehiculo;

    /**
     *  0 => estado inicial
     *  1 => carril solo auto 
     *  2 => carril combinado
     *  4 => carril mio
     * -1 => semaforo
     * -2 => no se puede hacer desvio
     *  
     **/
    private int tipoSeccion;

    //metodo constructor
    public Seccion(boolean isEmpty, Vehiculo objVehiculo, int tipoSeccion) {

        this.isEmpty = isEmpty;
        this.objVehiculo = objVehiculo;
        this.tipoSeccion = tipoSeccion;

    }

    public boolean isEmpty() {
            return isEmpty;
    }

    public void setEmpty(boolean isEmpty) {
            this.isEmpty = isEmpty;
    }

    public Vehiculo getObjVehiculo() {
            return objVehiculo;
    }

    public void setObjVehiculo(Vehiculo objVehiculo) {
            this.objVehiculo = objVehiculo;
    }

    public int getTipoSeccion() {
            return tipoSeccion;
    }

    public void setTipoSeccion(int tipoSeccion) {
            this.tipoSeccion = tipoSeccion;
    }

}
