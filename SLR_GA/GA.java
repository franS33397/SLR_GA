/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SLR_GA;


/**
 *
 * @author Fran33
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

class GA {

    private int tamPoblacion;
    private int tamIndividuo;
    private int numeroGeneraciones;

    ArrayList<Cromosoma> poblacion;
    private final int min = 0;
    private final int max = 300;
    private final double[][] dataset = {
        {23, 651},  {26, 762},  {30, 856},
        {34, 1063}, {43, 1190}, {48, 1298}, 
        {52, 1421}, {57, 1440}, {58, 1518}
    };//A-GA



    public GA(int pSize, int iSize, int nGens) {
        tamPoblacion = pSize;
        tamIndividuo = iSize;
        numeroGeneraciones = nGens;

        poblacion = new ArrayList<Cromosoma>();
    }

    public void generarPoblacion() {//SUS_V-ALL
        for (int i = 0; i < tamPoblacion; i++) {
            // crea random ind
            double[] genes = new double[tamIndividuo];

            for (int j = 0; j < tamIndividuo; j++) {
                double index = Math.random() * (max - min + 1) + min;
                genes[j] = index;
            }

            poblacion.add(new Cromosoma(genes));
        }
    }//SUS_A

    public int fitnessInd(double[] ind) {// ga
        double error = Math.abs(errorCuadrado(ind));
        double fitness = 0;

        error = error / 100.0;
        fitness = Math.abs(100.0 - error);

        return (int) fitness;
    }

    private double predice(double x, double[] ind) {//NEW
        return ind[0] + (x * ind[1]);
    }

    private double errorCuadrado(double[] ind) {//NEW
        double totalError = 0.0;

        for (int i = 0; i < dataset.length; i++) {
            double error = dataset[i][1] - predice(dataset[i][0], ind); //revisar

            totalError += error;
        }

        return totalError / (double) dataset.length;
    }

    public int fitnessPoblacion() {
        int totalFitness = 0;
        int indFitness = 0;

        for (var ind : poblacion) {
            indFitness = fitnessInd(ind.getGenes());
            ind.setFitness(indFitness);

            totalFitness += indFitness;
        }

        return totalFitness;
    }

    public void setpoblacionProbabilidad() {
        int totalFitness = fitnessPoblacion();

        for (var ind : poblacion) {
            double probabilidadInd = (double) ind.getFitness() /
                (double) totalFitness;

            ind.setProbabilidad(probabilidadInd);
        }
    }

    public int[] seleccionPorRuleta() {
        setpoblacionProbabilidad();

        double total = 0.0;
        HashMap<Integer, ArrayList<Double>> slices = new HashMap<>();

        for (int i = 0; i < tamPoblacion; i++) {
            ArrayList<Double> values = new ArrayList<>();
            values.add(total);
            var probabilidadInd = poblacion.get(i).getProbabilidad();
            values.add(total + probabilidadInd);
            slices.put(i, values);

            total += probabilidadInd;
        }

        int[] result = new int[tamPoblacion];

        for (int i = 0; i < tamPoblacion; i++) {
            double spin = Math.random();

            for (var key: slices.keySet()) {
                var slice = slices.get(key);

                if (slice.get(0) < spin && spin <= slice.get(1)) {
                    result[i] = key;
                    break;
                }
            }
        }

        return result;
    }

    public ArrayList<Cromosoma> onePointCrossover(Cromosoma parentA, Cromosoma parentB) {
        /*var genesPadreA = parentA.getGenes();
        var genesPadreB = parentB.getGenes();
        ArrayList<Cromosoma> hijo = new ArrayList<>();
        Random random = new Random();
        int xoverPoint = random.nextInt(genesPadreA.length());

        var primerHijo = genesPadreA.substring(0, xoverPoint) + 
            genesPadreB.substring(xoverPoint, genesPadreA.length());

        var segundoHijo = genesPadreB.substring(0, xoverPoint) + 
            genesPadreA.substring(xoverPoint, genesPadreA.length());

        hijo.add(new Cromosoma(primerHijo));
        hijo.add(new Cromosoma(segundoHijo));

        return hijo;*/
        var genesPadreA = parentA.getGenes();
        var genesPadreB = parentB.getGenes();
        ArrayList<Cromosoma> hijo = new ArrayList<>();
        Random random = new Random();
        int xoverPoint = random.nextInt(tamIndividuo);
        double[] primerHijo = new double[tamIndividuo];//SUS THIS
        double[] segundoHijo = new double[tamIndividuo];
                                                            //ADD FORS
        for (int i = 0; i < xoverPoint; i++) {
            primerHijo[i] = genesPadreA[i];
        }

        for (int i = xoverPoint; i < tamIndividuo; i++) {
            primerHijo[i] = genesPadreB[i];
        }

        for (int i = 0; i < xoverPoint; i++) {
            segundoHijo[i] = genesPadreB[i];
        }

        for (int i = xoverPoint; i < tamIndividuo; i++) {
            segundoHijo[i] = genesPadreA[i];
        }

        hijo.add(new Cromosoma(primerHijo));
        hijo.add(new Cromosoma(segundoHijo));

        return hijo;
    }

    public void mutarIndividuo(ArrayList<Cromosoma> hijo, int probMutacion) {
        /*Random random = new Random();

        for (var child : hijo) {
            int idx = random.nextInt(probMutacion);
            StringBuilder sb = new StringBuilder(child.getGenes());

            if (sb.charAt(idx) == '1') {
                sb.setCharAt(idx, '0');
            } else {
                sb.setCharAt(idx, '1');
            }

            child.setGenes(sb.toString());
        }*/

        Random random = new Random();
        int idx = 0;
        double newValue = 0.0;

        for (var child : hijo) {
            idx = random.nextInt(probMutacion);
            newValue = Math.random() * (max - min + 1) + min;
            var genes = child.getGenes();
            genes[idx] = newValue;
            child.setGenes(genes);
        }
    }

    //}

    public ArrayList<Cromosoma> reproducehijo(int[] elegido) {
        ArrayList<Cromosoma> hijo = new ArrayList<>();

        for (int i = 0; i < (elegido.length / 2 - 1); i++) {
            hijo.addAll(onePointCrossover(poblacion.get(elegido[i]),
                poblacion.get(elegido[i + 1])));
        }

        return hijo;
    }

    public Cromosoma getmejor() {
        int mejorFitness = 0;
        Cromosoma mejorind = new Cromosoma();

        for (var ind : poblacion) {
            if (ind.getFitness() > mejorFitness) {
                mejorFitness = ind.getFitness();
                mejorind = ind;
            }
        }
        System.out.println(mejorind);
        return mejorind;
    } 

    public void runGA() {
        int mejorFitnessGeneral = 0;
        //var poblacionGlobal = generarPoblacion();
        generarPoblacion();

        for (int i = 0; i < numeroGeneraciones; i++) {
            int mejorFitnessActual = fitnessPoblacion();

            if (mejorFitnessActual > mejorFitnessGeneral) {
                mejorFitnessGeneral = mejorFitnessActual;
            }

            var elegido = seleccionPorRuleta();
            var hijo = reproducehijo(elegido);
            mutarIndividuo(hijo, tamIndividuo);
            poblacion.addAll(hijo);
            //
            //var mejor = getmejor(poblacionGlobal);
            System.out.println(mejorFitnessActual+"\t\n");
        }

        var mejor = getmejor();

        System.out.println("Mejor fitness: " + mejorFitnessGeneral);
        System.out.println("Mejor actual: " + mejor.getGenes());
        System.out.println("Mejor fitness actual: " + mejor.getFitness());
    }

}