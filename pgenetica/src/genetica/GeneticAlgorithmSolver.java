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
public class GeneticAlgorithmSolver implements Runnable {
    private GeneticAlgorithm ga;
    private Population gt;
    private int maxIter;
    private float mutationRate;
    private float crossOverRate;
    private float eliteRate;
    private float minFitness;
    private int curIter;

    private transient Thread solverThread;

    public GeneticAlgorithmSolver(GeneticAlgorithm ga) {
        this.ga = ga;
        this.gt = null;
        this.maxIter = 5000;
        this.mutationRate = 0.2f;
        this.eliteRate = 1/3.0f;
        this.minFitness = 0;
        this.crossOverRate = 0.5f;
        this.curIter = 0;
    }

    public int getCurIter() {
        return curIter;
    }

    public GeneticAlgorithm getGeneticAlgorithm() {
        return ga;
    }

    public int getMaxIter() {
        return maxIter;
    }

    public void setMaxIter(int maxIter) {
        this.maxIter = maxIter;
    }

    public float getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(float mutationRate) {
        this.mutationRate = mutationRate;
    }

    public float getCrossOverRate() {
        return crossOverRate;
    }

    public void setCrossOverRate(float crossOverRate) {
        this.crossOverRate = crossOverRate;
    }
    
    public float getEliteRate() {
        return eliteRate;
    }

    public void setEliteRate(float eliteRate) {
        this.eliteRate = eliteRate;
    }

    public Population getGt() {
        return gt;
    }

    public void setGt(Population gt) {
        this.gt = gt;
    }

    public float getMinFitness() {
        return minFitness;
    }

    public void setMinFitness(float minFitness) {
        this.minFitness = minFitness;
    }
    
    public Chromosome getBestChromosome() {
        return gt.getChromosome(0);
    }
    
    public float getBestFitness() {
        return getBestChromosome().getFitness();
    }
    
    public void solve() {
        int i;
        Chromosome M, offsprings[], mom, dad;
        int elite;
        int N, NC;
        float p1, p2;
        

        setGt( ga.populate() );
        gt.calcFitness();
        gt.sortByFitness();
        
        N = gt.getNchromosomes();
        elite = (int)(N * getEliteRate());
        if ( elite % 2 != N % 2 )
            NC = N-1;
        else
            NC = N;

        offsprings = new Chromosome[2];
        System.out.println("Inicio: (elite= " + elite +")");
        
        for ( i = 0; i < N; i++ ) {
            System.out.println(i+" = "+gt.getChromosome(i));
        }

        for ( curIter = 0; curIter < maxIter &&
                getBestFitness() > minFitness; curIter++ ) {

//            if ( curIter % 1000 == 0 )
//               System.out.println(curIter+" mejor: "+getBestChromosome());

            if ( crossOverRate > 0 ) {
                 //crossover
                 Population np = new Population();
                 for ( i = 0; i < elite; i++ )
                     np.addChromosome( gt.getChromosome(i) );

                 //una variacion de seleccion por Rank
                 p1 = crossOverRate / (NC/2 + 1);
                 p2 = crossOverRate;
                 for ( i = 0; i < NC-elite; i+=2 ) {
                     mom = gt.getChromosome(i);
                     dad = gt.getChromosome(i+1);
                     if ( Math.random() <= p2 ) {
                         mom.crossOver(dad, offsprings);
                         if ( offsprings[0] != null ) {
                             mom = offsprings[0];
                         }
                         if ( offsprings[1] != null ) {
                             dad = offsprings[1];
                         }
                     }
                     M = mom.mutate(mutationRate);
                     if ( M == null ) M = mom;
                     np.addChromosome(M);

                     M = dad.mutate(mutationRate);
                     if ( M == null ) M = dad;
                     np.addChromosome(M);

                     p2 = p2 - p1;
                 }

                 if ( NC != N ) {
                     mom = gt.getChromosome(N-1);
                     M = mom.mutate(mutationRate);
                     if ( M == null ) M = mom;
                     np.addChromosome(M);
                 }
                 setGt(np);
            }
            else {
             //mutation only
             for ( i = elite; i < N; i++ ) {
                M = gt.getChromosome(i).mutate(mutationRate);
                if ( M != null ) {
                   gt.setChromosome(i, M);
                }
             }
            }
            gt.calcFitness();
            gt.sortByFitness();
        }
        
        
        System.out.println("Iteracion:" + curIter);
        for ( i = 0; i < gt.getNchromosomes(); i++ ) {
            System.out.println(i+" = "+gt.getChromosome(i));
        }
    }

    public void solveInNewThread() {
         solverThread = new Thread(this);
         solverThread.start();
    }

    public void run() {
        solve();
    }
        
    
}
