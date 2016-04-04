/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetica;

/**
 *
 * @author ealonso
 */
public class BinaryChromosome implements Chromosome {
    protected byte bits[];
    protected int nb;
    protected float fitness;


    public BinaryChromosome(int nb) {
        this.nb = nb;
        bits = new byte[nb];
        for ( int i = 0; i < nb; i++ )
            bits[i] = 0;
        fitness = 0;
    }

    public BinaryChromosome(int nb,  Object obj) {
        this.nb = nb;
        bits = new byte[nb];
        encode(obj);
        fitness = 0;
    }

    public BinaryChromosome(byte bits[]) {
        setBits( bits );
        fitness = 0;
    }

    public void encode( Object obj ) {
    }
    
    public byte[] getBits() {
        return bits;
    }

    public void setBits(byte bits[]) {
        this.nb = bits.length;
        this.bits = new byte[nb];
        for (int i = 0; i < nb; i++)
            this.bits[i] = bits[i];
        this.fitness = 0;
    }

    public void setBit( int b, byte v ) {
        bits[b] = v;
    }
    
    public byte getBit(int b) {
        return bits[b];
    }

    public void reverseBit(int b) {
        if ( bits[b] == 0 )
            bits[b] = 1;
        else
            bits[b] = 0;
    }

    public void decode( Object obj ) {
        decode( bits, obj );
    }

    public void decode( byte bits[], Object obj ) {
    }

    public void intToNbin(byte a[] , int begin, int n, int v) {
        int i;

        i = n-1;
        v = Math.abs(v);
        while ( i >= 0 ) {
            a[begin+i] = (byte)(v % 2);
            v /= 2;
            i--;
        }
    }

    public void set8bits(int idx, int v) {
        setNbits(idx, 8, v );
    }

    public void setNbits( int idx, int n, int v  ) {
        intToNbin( bits, idx*n, n, v );
    }

    public int get8int( int idx ) {
        return getNint( idx, 8 );
    }

    public int getNint( int idx, int n ) {
        return binToInt( bits, idx*n, n );
    }

    public int binToInt( byte a[], int begin, int n ) {
        int res, i;
        res = 0;
        for ( i = 0; i < n; i++ ) {
            res = res * 2 + a[begin+i];
        }
        return res;
    }

    public void copyBits( byte src[], int ssrc, byte dst[], int sdst, int n ) {
        int i;
        for ( i = 0; i < n; i++ )
            dst[sdst+i] = src[ssrc+i];
    }

    public float getFitness() {
        return fitness;
    }

    public float calcFitness() {
        return fitness;
    }

    public Chromosome mutate(float rate) {
        int i;

        BinaryChromosome M;

        M = (BinaryChromosome)duplicate();
        for ( i = 0; i < nb; i++ ) {
            if ( Math.random() <= rate )
               M.reverseBit(i);
        }
        return M;
    }

    //one point crossover
    public void crossOver( Chromosome c2, Chromosome offsprings[] ) {
        int c;
        if  (offsprings != null ) {
            c = (int)Math.floor( Math.random() * nb );
            BinaryChromosome h1, h2;
            h1 = (BinaryChromosome)this.duplicate();
            h2 = (BinaryChromosome)c2.duplicate();
            copyBits( h2.bits, c, h1.bits, c, nb-c );
            copyBits( this.bits, c, h2.bits, c, nb-c );
            offsprings[0] = h1;
            offsprings[1] = h2;
        }
    }

    public Chromosome duplicate() {
        return new BinaryChromosome(bits);
    }

}
