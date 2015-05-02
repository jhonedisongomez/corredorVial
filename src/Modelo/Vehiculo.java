/**
 * fecha        : 30-mayo-2015
 * descripcion  : Implementacion algunos metodos para el movimiento de los carros 
 * 
 * 
 **/
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
    public void avanzarFrente(int[][] corredor,int carrIx,int posIx) {
        
        switch(corredor[carrIx][posIx]){
            
            //los casos son los tipos de carros
            case 1:
                
                if(corredor[carrIx][posIx]== 1 && corredor[carrIx][posIx+1]== 0
                        && (corredor[carrIx+1][posIx] > 0 || corredor[carrIx-1][posIx] == -2 )){

                    corredor[carrIx][posIx]= 0;
                    corredor[carrIx][posIx+1]= 1;
                }
                
                //condicion para adelantar el unico corredor
                if(carrIx == 3){
                    if(corredor[carrIx][posIx]== 1 && corredor[carrIx][posIx+1]== 0){
                        
                        corredor[carrIx][posIx]= 0;
                        corredor[carrIx][posIx+1]= 1;
                    }
                }
                
            break;
                
            case 2:
                
                if(corredor[carrIx][posIx]== 2 && corredor[carrIx][posIx+1]== 0 && corredor[carrIx][posIx+2]== 0
                        && (corredor[carrIx+1][posIx] > 0 || corredor[carrIx-1][posIx] == -2 )){

                    corredor[carrIx][posIx]= 0;
                    corredor[carrIx][posIx-1]= 0;
                    
                    corredor[carrIx][posIx+1]= 1;
                    corredor[carrIx][posIx+2]= 1;
                
                }
                
            break;
                
            case 3:
                
                if(corredor[carrIx][posIx]== 2 && corredor[carrIx][posIx+1]== 0 && corredor[carrIx][posIx+2]== 0
                       && corredor[carrIx][posIx+3]== 0 && (corredor[carrIx+1][posIx] > 0  || corredor[carrIx-1][posIx] == -2 )){

                    corredor[carrIx][posIx]= 0;
                    corredor[carrIx][posIx-1]= 0;
                    corredor[carrIx][posIx-2]= 0;
                    
                    corredor[carrIx][posIx+1]= 3;
                    corredor[carrIx][posIx+2]= 3;
                    corredor[carrIx][posIx+3]= 3;
                
                }
                
            break;
                
            case 4:
                
                if(corredor[carrIx][posIx]== 2 && corredor[carrIx][posIx+1]== 0 && corredor[carrIx][posIx+2]== 0
                       && corredor[carrIx][posIx+3]== 0 && corredor[carrIx][posIx+4]== 0 && (corredor[carrIx+1][posIx] > 0  || corredor[carrIx-1][posIx] == -2 )){

                    corredor[carrIx][posIx]= 0;
                    corredor[carrIx][posIx-1]= 0;
                    corredor[carrIx][posIx-2]= 0;
                    corredor[carrIx][posIx-3]= 0;
                    
                    corredor[carrIx][posIx+1]= 4;
                    corredor[carrIx][posIx+2]= 4;
                    corredor[carrIx][posIx+3]= 4;
                    corredor[carrIx][posIx+4]= 4;
                
                }
                
            break;    
                
        }

    }        
        
    /**
     *
     */
    public void adelantarDerecha(int [][]corredor,int carrIx, int posIx) {
        
        switch(corredor[carrIx][posIx]){
            
            case 1:
                
                if((corredor[2][posIx] == 1) &&  corredor[1][posIx+1] == 0 
                    &&  corredor[1][posIx] == 0  && corredor[2][posIx+1] > 0){
                    
                    corredor[2][posIx] = 0;
                    corredor[1][posIx+1] = 1;
                }
                
            break;
                
            case 2:
                
                if((corredor[2][posIx] == 2) && corredor[1][posIx+1] == 0  && corredor[1][posIx+2] == 0 
                    &&  corredor[1][posIx] == 0  && corredor[2][posIx+1] >0){
                    
                    corredor[2][posIx-1] = 0;
                    corredor[2][posIx] = 0;
                    
                    corredor[1][posIx+1] = 2;
                    corredor[1][posIx+2] = 2;
                }
                
            break;
            
            case 3:
                
                if((corredor[2][posIx] == 2) && corredor[1][posIx+1] == 0  && corredor[1][posIx+2] == 0 
                    && corredor[1][posIx+2] == 0 &&  corredor[1][posIx] == 0  && corredor[2][posIx+1] > 0){
                    
                    corredor[2][posIx-2] = 0;
                    corredor[2][posIx-1] = 0;
                    corredor[2][posIx] = 0;
                    
                    corredor[1][posIx+1] = 3;
                    corredor[1][posIx+2] = 3;
                    corredor[1][posIx+3] = 3;
                }
            break;
            
        }
    }
    
    public void adelantarIzquierda(int [][]corredor, int posIx) {
        
        switch(idTipoVehiculo){
            
            case 1:
                if(corredor[1][posIx] == 1 && corredor[2][posIx+1] == 0 
                    && corredor[2][posIx] == 0  && corredor[1][posIx+1] == 1){
                    
                    corredor[1][posIx] = 0;
                    corredor[2][posIx+1] = 1;
                }
            break;    
            
            case 2:
                if(corredor[1][posIx] == 1 && corredor[2][posIx+1] == 0 && corredor[2][posIx+2] == 0 
                    && corredor[2][posIx] == 0  && corredor[1][posIx+1] == 1){
                    
                    corredor[1][posIx-1] = 0;
                    corredor[1][posIx] = 0;
                    
                    corredor[2][posIx+1] = 2;
                    corredor[2][posIx+2] = 2;
                }
            break;
            
            case 3:
                if(corredor[1][posIx] == 1 && corredor[2][posIx+1] == 0 && corredor[2][posIx+2] == 0 
                    && corredor[2][posIx+3] == 0 && corredor[2][posIx] == 0  && corredor[1][posIx+1] == 1){
                    
                    corredor[1][posIx-2] = 0;
                    corredor[1][posIx-1] = 0;
                    corredor[1][posIx] = 0;
                    
                    corredor[2][posIx+1] = 2;
                    corredor[2][posIx+2] = 2;
                    corredor[2][posIx+3] = 2;
                }
            break;
                    
        }
    }
    
    //todos menos el masivo
    public void tomarCuadra(int [][]corredor,int carrIx, int posIx) {
        
        if(corredor[carrIx][posIx] < 4 && corredor[carrIx][posIx]>0){
            
            int tipoCarro = corredor[carrIx][posIx];
            
            if(corredor[1][posIx]>0 && corredor[0][posIx+1] ==0){
                
                //solo para desocupar los carriles
                switch(tipoCarro){
                    
                    case 1:
                        corredor[1][posIx] = 0;
                    break;
                        
                    case 2:
                        corredor[1][posIx] = 0;
                        corredor[1][posIx-1] = 0;
                    break;
                        
                    case 3:
                        corredor[1][posIx] = 0;
                        corredor[1][posIx-1] = 0;
                        corredor[1][posIx-3] = 0;
                    break;    
                    
                }//cierre switch
            }//cierre condicional
        }//cierre condicional
    }//cierre metodo
    
    public void frenar() {
        
    }
    
    /**
     carros
     */
    public void tomarDesvio() {
    
    }
}
