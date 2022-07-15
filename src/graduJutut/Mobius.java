package graduJutut;

/**
 * @author Joona1
 * @version 18.5.2022
 * Implementaatio reaalikertoimisista mobius kuvauksista hyperbolisen tason isometrioita varten
 */
public class Mobius {

    double a,b,c,d;
    /**
     * @param i .
     * @param r .
     * @param j .
     * @param k .
     * vastaava matriisi
     * [i r]
     * [j k]
     */
    public Mobius(double i, double r, double j, double k) {
        a=i; b=r; c=j; d=k;
    }
    
    /**
     * evaluate matrix at complex point c as mobius transformation
     * @param z point of evaluation
     * @return evaluated value
     */
    public Comp eval(Comp z) {
        Comp c1 = new Comp(c*z.r+d,c*z.i);
        double n1 = c1.n2();
        double im = (a*d-c*b)*z.i/n1;
        double re = (a*c*z.n2()+(a*d+b*c)*z.r+b*d)/n1;
        return new Comp(re, im);
    }
    
    /**
     * reflect value according to the hyperbolic line that is fixed by this moebius transformation
     * @param z1 point of evaluation
     * @return evaluated value
     */
    public Comp refl(Comp z1) {
        Mobius mb = this.inv();
        return eval(new Comp(-mb.eval(z1).r, mb.eval(z1).i));
    }

    
    /**
     * @param z transformoitava piste tasomallissa
     * @return piste pallomallissa
     */
    public static Comp ballMod(Comp z) {
        Comp z1 = new Comp(z.r/85,z.i/85);
        Comp z2 = new Comp(z1.r,z1.i+1);
        return new Comp(200*(z1.n2()-1)/z2.n2(),200*(-2*z1.r)/z2.n2());
    }
    
    private Mobius inv() {
        return new Mobius(d,-b,-c,a);
    }
    
    /**
     * @param mb matriisi tulo
     * @return .
     */
    public Mobius kertaa(Mobius mb) {
        return new Mobius(a*mb.a+b*mb.c,a*mb.b+b*mb.d,c*mb.a+d*mb.c,c*mb.b+d*mb.d);
    }

}
