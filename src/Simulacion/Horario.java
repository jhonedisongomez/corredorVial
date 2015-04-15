/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulacion;

/**
 *
 * @author sergio
 */
public class Horario {
    
    private static final int[] tiempoSimulacion = new int[3];
    private static final double[][] probabilidadesSN = new double[3][4];
    private static final double[][] probabilidadesNS = new double[3][4];
    
    public Horario (int[] horariosGui) {
        // se asigna la duracion en segundos de cada horario para la simulacion
        // se asignan las probabilidades para cada horario
        for (int i = 0; i < horariosGui.length; i++) {
            if (i == 0 && horariosGui[i] == 1) {
                tiempoSimulacion[i] = 3600;
                asignarValores(1);
            }
            else if (i == 1 && horariosGui[i] == 1) {
                tiempoSimulacion[i] = 7200;
                asignarValores(2);
            }
            else if (i == 2 && horariosGui[i] == 1) {
                tiempoSimulacion[i] = 7200;
                asignarValores(3);
            }
            else
                tiempoSimulacion[i] = 0;
        }
    }
    
    /**
     * @return the tiempoSimulacion
     */
    public static int[] getTiempoSimulacion() {
        return tiempoSimulacion;
    }
    
    public static double[][] getProbabilidadesSN() {
        return probabilidadesSN;
    }
    
    public static double[][] getProbabilidadesNS() {
        return probabilidadesSN;
    }
    
    private void asignarValores(int franja) {
        switch (franja) {
            case 1:// 7 - 8 am
                // carril sur norte
                probabilidadesSN[0][0] = 0.9233; // carro
                probabilidadesSN[0][1] = 0.017; // Buseta
                probabilidadesSN[0][2] = 0.0341; // Camion
                probabilidadesSN[0][3] = 0.0256; // MIO
                // carril norte sur
                probabilidadesNS[0][0] = 0.897; // carro
                probabilidadesNS[0][1] = 0.018; // Buseta
                probabilidadesNS[0][2] = 0.057; // Camion
                probabilidadesNS[0][3] = 0.028; // MIO
                break;
            case 2:// 12 - 2 pm
                probabilidadesSN[1][0] = 0.9285;
                probabilidadesSN[1][1] = 0.0149;
                probabilidadesSN[1][2] = 0.0262;
                probabilidadesSN[1][3] = 0.0304; //MIO
                probabilidadesNS[1][0] = 0.8645;
                probabilidadesNS[1][1] = 0.0356;
                probabilidadesNS[1][2] = 0.0686;
                probabilidadesNS[1][3] = 0.0313;
                break;
            case 3:// 6 - 8 pm
                probabilidadesSN[2][0] = 0.941;
                probabilidadesSN[2][1] = 0.011;
                probabilidadesSN[2][2] = 0.019;
                probabilidadesSN[2][3] = 0.029;
                probabilidadesNS[2][0] = 0.876;
                probabilidadesNS[2][1] = 0.031;
                probabilidadesNS[2][2] = 0.07;
                probabilidadesNS[2][3] = 0.023;
                break;
        }
    }
}
