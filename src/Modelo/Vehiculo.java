/**
 * fecha        : 30-mayo-2015
 * descripcion  : Implementacion algunos metodos para el movimiento de los carros 
 * 
 * 
 **/
package Modelo;

import javax.swing.JOptionPane;

/**
 *
 * @author sergio
 */
public class Vehiculo {
    
    private int idTipoVehiculo;
    private int longitudVehiculo;
    private int espacio;
    
    //las velocidades iran de 0 a 4
    private int velocidad;
    /* array de velocidades
        Velocidad 4 : 4 casillas/s = 66 km/h
        Velocidad 3 : 3 casillas/s = 50 km/h
        Velocidad 2 : 2 Casillas/s = 33 km/h
        Velocidad 1 : 1 Casilla/s  = 17 Km/h
    */
    //private static final int[] velocidades = {1, 2, 3, 4, 5};

    public Vehiculo(int idTipoVehiculo, int longitudVehiculo, int espacio,int velocidad) {
        
        this.idTipoVehiculo = idTipoVehiculo;
        this.longitudVehiculo = longitudVehiculo;
        this.espacio = espacio;
        this.velocidad = velocidad;
    
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
     * @return the espacio
     */
    public int getEspacio() {
        return espacio;
    }

    /**
     * @return the espacio
     */
    
    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
    
    /**
     * @param vehiculo para referenciar el corredor
     */
    
    public static void avanzarFrente(Vehiculo[][] corredor,int carrIx,int posIx) {
        
        Vehiculo objVehiculoVacio = new Vehiculo(0, 0, 0, 0);
        int tipoVehiculo = corredor[carrIx][posIx].getIdTipoVehiculo();
        int velocidad = corredor[carrIx][posIx].getVelocidad();
                
        switch(tipoVehiculo){
            
            //los casos son los tipos de carros
            case 1:
                
                if(carrIx == 3){
                    
                    if(corredor[carrIx][posIx].getEspacio()== 1 && corredor[carrIx][posIx+1].getEspacio()== 0
                            && velocidad == 1){
                        
                        //ocupamos la posicion siguiente
                        corredor[carrIx][posIx+1]= corredor[carrIx][posIx];
                        
                        //aceleramos el vehiculo
                        corredor[carrIx][posIx].setVelocidad(velocidad+1);
                        
                        //desocupamos la posicion anterior
                        corredor[carrIx][posIx]= objVehiculoVacio;
                    
                    }else if(corredor[carrIx][posIx].getEspacio()== 1 && corredor[carrIx][posIx+2].getEspacio()== 0
                            && velocidad == 2){
                        
                         //ocupamos la posicion siguiente
                        corredor[carrIx][posIx+2]= corredor[carrIx][posIx];
                        
                        //aceleramos el vehiculo
                        corredor[carrIx][posIx].setVelocidad(velocidad+1);
                        
                        //desocupamos la posicion anterior
                        corredor[carrIx][posIx]= objVehiculoVacio;
                    
                    }else if(corredor[carrIx][posIx].getEspacio()== 1 && corredor[carrIx][posIx+3].getEspacio()== 0
                            && velocidad == 3){
                        
                        //ocupamos la posicion siguiente
                        corredor[carrIx][posIx+3]= corredor[carrIx][posIx];
                        
                        //aceleramos el vehiculo
                        corredor[carrIx][posIx].setVelocidad(velocidad+1);
                        
                        //desocupamos la posicion anterior
                        corredor[carrIx][posIx]= objVehiculoVacio;
                        
                    }else if(corredor[carrIx][posIx].getEspacio()== 1 && corredor[carrIx][posIx+4].getEspacio()== 0
                            && velocidad == 4){
                        
                        //ocupamos la posicion siguiente
                        corredor[carrIx][posIx+4]= corredor[carrIx][posIx];
                        
                        //aceleramos el vehiculo
                        corredor[carrIx][posIx].setVelocidad(velocidad+1);
                        
                        //desocupamos la posicion anterior
                        corredor[carrIx][posIx]= objVehiculoVacio;
                    
                    }
                        
                    
                }else{
                    
                    switch(velocidad){
                        
                        case 1:
                            corredor[carrIx][posIx+1]= corredor[carrIx][posIx];
                            
                            if(corredor[carrIx][posIx+2].getEspacio() == 0 && corredor[carrIx][posIx+1].getEspacio() == 0){
                                corredor[carrIx][posIx].setVelocidad(velocidad+1);
                            }
                            
                            corredor[carrIx][posIx]= objVehiculoVacio;
                        break;
                        
                        case 2:
                            if(corredor[carrIx][posIx+2].getEspacio()==0){
                                corredor[carrIx][posIx+2]= corredor[carrIx][posIx];
                            }
                            
                            if(corredor[carrIx][posIx+3].getEspacio() == 0 && corredor[carrIx][posIx+2].getEspacio() == 0){
                                corredor[carrIx][posIx].setVelocidad(velocidad+1);
                            }
                            
                            corredor[carrIx][posIx]= objVehiculoVacio;
          
                           break;
                        
                        case 3:
                            if(corredor[carrIx][posIx+3].getEspacio()== 0 && corredor[carrIx][posIx+2].getEspacio()==0){
                            
                                corredor[carrIx][posIx+3]= corredor[carrIx][posIx];
                            }
                        
                            if(corredor[carrIx][posIx+4].getEspacio() == 0 && corredor[carrIx][posIx+3].getEspacio() == 0
                                    && corredor[carrIx][posIx+2].getEspacio() == 0){
                                
                                corredor[carrIx][posIx].setVelocidad(velocidad+1);
                            }
                            
                            corredor[carrIx][posIx]= objVehiculoVacio;
                        
                        break;
                        
                        case 4:
                        
                            if(corredor[carrIx][posIx+4].getEspacio()== 0 && corredor[carrIx][posIx+3].getEspacio()==0 && corredor[carrIx][posIx+2].getEspacio()==0){
                                
                                corredor[carrIx][posIx+4]= corredor[carrIx][posIx];
                                corredor[carrIx][posIx]= objVehiculoVacio;
                            }
                            
                        break;
                        
                    }
                    
                    
                
                }
                
            break;
                
            case 2:
                    
                corredor[carrIx][posIx+1]= corredor[carrIx][posIx];
                corredor[carrIx][posIx+2]= corredor[carrIx][posIx];

                corredor[carrIx][posIx]= objVehiculoVacio;
                corredor[carrIx][posIx-1]= objVehiculoVacio;
            
            break;
                
            case 3:
                
                corredor[carrIx][posIx+1]= corredor[carrIx][posIx];
                corredor[carrIx][posIx+2]= corredor[carrIx][posIx];
                corredor[carrIx][posIx+3]= corredor[carrIx][posIx];

                corredor[carrIx][posIx]= objVehiculoVacio;
                corredor[carrIx][posIx-1]= objVehiculoVacio;
                corredor[carrIx][posIx-2]= objVehiculoVacio;
                
                
            break;
                
            case 4:
                
                corredor[carrIx][posIx+1]= corredor[carrIx][posIx];
                corredor[carrIx][posIx+2]= corredor[carrIx][posIx];
                corredor[carrIx][posIx+3]= corredor[carrIx][posIx];
                corredor[carrIx][posIx+4]= corredor[carrIx][posIx];

                corredor[carrIx][posIx]= objVehiculoVacio;
                corredor[carrIx][posIx-1]= objVehiculoVacio;
                corredor[carrIx][posIx-2]= objVehiculoVacio;
                corredor[carrIx][posIx-3]= objVehiculoVacio;
                
            break;    
                
        }
        
        System.out.println("adelanto");

    }        
        
    /**
     *
     */
    public static void adelantarDerecha(Vehiculo [][]corredor,int carrIx, int posIx) {
        
        Vehiculo objVehiculoVacio = new Vehiculo(0, 0, 0, 0);
        
        switch(corredor[carrIx][posIx].getIdTipoVehiculo()){
            
            case 1:
                
                corredor[1][posIx+1] = corredor[2][posIx];
                corredor[2][posIx] = objVehiculoVacio;

            break;
                
            case 2:
                    
                corredor[1][posIx+1] = corredor[2][posIx];
                corredor[1][posIx+2] = corredor[2][posIx];

                corredor[2][posIx-1] = objVehiculoVacio;
                corredor[2][posIx] = objVehiculoVacio;
                
            break;
            
            case 3:
                    
                corredor[1][posIx+1] = corredor[2][posIx];
                corredor[1][posIx+2] = corredor[2][posIx];
                corredor[1][posIx+3] = corredor[2][posIx];

                corredor[2][posIx-2] = objVehiculoVacio;
                corredor[2][posIx-1] = objVehiculoVacio;
                corredor[2][posIx] = objVehiculoVacio;
                
            break;
                
        }
        System.out.println("Cruzo derecha");
    }
    
    public static void adelantarIzquierda(Vehiculo [][]corredor,int carIx, int posIx) {
        
        Vehiculo objVehiculoVacio = new Vehiculo(0, 0, 0, 0);
        
        switch(corredor[carIx][posIx].getIdTipoVehiculo()){
            
            case 1:
                    
                corredor[2][posIx+1] = corredor[1][posIx];
                corredor[1][posIx] = objVehiculoVacio;
                    
            break;    
            
            case 2:
                
                corredor[2][posIx+1] = corredor[1][posIx];
                corredor[2][posIx+2] = corredor[1][posIx];

                corredor[1][posIx-1] = objVehiculoVacio;
                corredor[1][posIx] = objVehiculoVacio;
                
            break;
            
            case 3:

                corredor[2][posIx+1] = corredor[1][posIx];
                corredor[2][posIx+2] = corredor[1][posIx];
                corredor[2][posIx+3] = corredor[1][posIx];

                corredor[1][posIx-2] = objVehiculoVacio;
                corredor[1][posIx-1] = objVehiculoVacio;
                corredor[1][posIx] = objVehiculoVacio;
                    
            break;
                    
        }
        
        if(corredor[2][178].getEspacio() > 0){
           System.out.println("Cruzo izquierda"); 
        }
        
    }
    
    //todos menos el masivo
    public static void tomarCuadra(Vehiculo [][]corredor,int carrIx, int posIx) {
        
        Vehiculo objVehiculoVacio = new Vehiculo(0, 0, 0, 0);
        
        if(corredor[carrIx][posIx].getEspacio() < 4 && corredor[carrIx][posIx].getEspacio()>0){
            
            int tipoCarro = corredor[carrIx][posIx].getIdTipoVehiculo();
            
            if(corredor[1][posIx].getEspacio()>0 && corredor[0][posIx+1].getEspacio() ==0){
                
                //solo para desocupar los carriles
                switch(tipoCarro){
                    
                    case 1:
                        
                        corredor[1][posIx] = objVehiculoVacio;
                        
                    break;
                        
                    case 2:
                    
                        corredor[1][posIx] = objVehiculoVacio;
                        corredor[1][posIx-1] = objVehiculoVacio;
                        
                    break;
                        
                    case 3:
                        
                        corredor[1][posIx] = objVehiculoVacio;
                        corredor[1][posIx-1] = objVehiculoVacio;
                        corredor[1][posIx-3] = objVehiculoVacio;
                    
                    break;    
                    
                }//cierre switch
                JOptionPane.showMessageDialog(null, "tomo cuadra");
            }//cierre condicional
        }//cierre condicional
    }//cierre metodo
    
    private void frenar() {
        
    }
    
    private int acelerar(int velocidad) {
        return velocidad+1;
    }
    
    /**
     carros
     */
    private void tomarDesvio() {
    
    }
}
