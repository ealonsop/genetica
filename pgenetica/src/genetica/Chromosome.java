/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genetica;

/**
 *
 * @author ealonso
 */
public interface Chromosome {
    public float getFitness();
    public float calcFitness();
    public Chromosome mutate(float mutationRate);
    public void crossOver( Chromosome c2, Chromosome offsprings[] );
    public Chromosome duplicate();
}
