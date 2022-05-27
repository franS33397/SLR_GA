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


class Cromosoma {
    double[] genes;//SUS-CHROMO
    int fitness;
    double Probabilidad;

    public Cromosoma() {
        genes = new double[2];//SUS-CHROMO
        fitness = 0;
        Probabilidad = 0.0;
    }

    public Cromosoma(double[] g) {//SUS-CHROMO2
        genes = g;
        fitness = 0;
        Probabilidad = 0.0;
    }

    public double[] getGenes() {//SUS-VAR
        return genes;
    }

    public void setGenes(double[] genes) {//SUS-VAR
        this.genes = genes;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public double getProbabilidad() {
        return Probabilidad;
    }

    public void setProbabilidad(double Probabilidad) {
        this.Probabilidad = Probabilidad;
    }

    public String toString() {
        StringBuilder bs = new StringBuilder("[");//V-SUS_ALL

        for (int i = 0; i < genes.length; i++) {
            if (i == (genes.length - 1)) {
                bs.append(genes[i]);
            } else {
                bs.append(genes[i] + " ");
            }
        }

        bs.append("]");

        return "Genes: " + bs.toString();
    }//A-SUS_ALL
}