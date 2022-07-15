package graduJutut;


/**
 * @author Joona1
 * @version 17.5.2022
 *
 */
public class Comp {
    double r;
    double i;
    /**
     * @param real reaaliosa
     * @param imag imaginaariosa
     * @param k skaalaus
     */
    public Comp(double real, double imag, double k) {
        r=k*real;
        i=k*imag;
    }
    
    /**
     * @param real reaaliosa
     * @param imag imaginaariosa
     */
    public Comp(double real, double imag) {
        r=real;
        i=imag;
    }
    
    /**
     * @return l2 normin neliö
     */
    public double n2() {
        return r*r+i*i;
    }
    
    @Override
    public String toString() {
        return "Real part "+r+" Imaginary part "+i;
    }
}