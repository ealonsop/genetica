/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetica;

import java.util.*;

/**
 *
 * @author ealonso
 */
public class Population {
    private List<Chromosome> chromosomes;
    
    public Population() {
        chromosomes = new ArrayList<Chromosome>();
    }
    
    public void addChromosome( Chromosome c ) {
        chromosomes.add(c);
    }

    public void setChromosome( int p, Chromosome c ) {
        chromosomes.set(p,c);
    }
    
    public Chromosome getChromosome(int p) {
        return chromosomes.get(p);
    }
    
    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public int getNchromosomes() {
        return chromosomes.size();
    }
    
    public void calcFitness() {
        int i;
        for ( i = 0; i < getNchromosomes(); i++ ) {
            getChromosome(i).calcFitness();
        }
    }

    public void sortByFitness() {
        int i;
        int j;
        Chromosome tmp;
        boolean seguir;

        seguir = true;
        for ( i = getNchromosomes()-1; i > 0 && seguir; i-- ) {
            seguir = false;
            for ( j = 0; j < i; j++ ) {
                if ( getChromosome(j).getFitness() > getChromosome(j+1).getFitness() ) {
                    seguir = true;
                    tmp = chromosomes.get(j);
                    chromosomes.set(j, chromosomes.get(j+1));
                    chromosomes.set(j+1, tmp);
                }
            }
        }
    }
    
    
    
    
}
