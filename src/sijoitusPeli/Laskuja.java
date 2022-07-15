package sijoitusPeli;


/**
 * @author Joona1
 * @version 31.3.2020
 *
 */
public class Laskuja {

    /**
     * @param args .
     */
    public static void main(String[] args) {
        System.out.println(OnkoNelio(10000,7));
        System.out.println(OnkoTuloNelio(100,3));
        System.out.println(MontakoNeliota(10));
        System.out.println(TuloSarja(100));
        System.out.println(TuloSarja(1000));
        System.out.println(TuloSarja(10000));
    }

    
    private static double TuloSarja(int lkm) {
        double palautus = 1;
        for(double i=1;i<lkm;i++)
            if(OnkoAlkuluku(i))palautus*=((i*i+1)/((i+1)*(i+1)));
        return palautus;
    }


    private static boolean OnkoAlkuluku(double nro) {
        for(int i=2;i<nro; i++)
            if(nro%i==0)return false;
        return true;
    }


    private static int MontakoNeliota(int raja) {
        int lkm = 0;
        for(int i=1;i<=raja;i++)
            for(int j=1;j<=raja;j++)
                if(OnkoNelio(i*j))lkm++;//System.out.print("("+i+ ","+j+") ")
        return lkm;
    }


    private static double OnkoTuloNelio(int lkm, int p) {
        double todnak=0;
        for(int i=0;i<lkm;i++)
            for(int j=0;j<lkm;j++)
                if(SuurinPotenssi(i*j,p)%2==0)todnak++;
        return todnak/(lkm*lkm);
    }


    private static double OnkoNelio(double lkm,int p) {
        double todnak=0;
        for(int i=0;i<lkm;i++) {
            if(SuurinPotenssi(i,p)%2==0)todnak++;
        }
        return todnak/lkm;
    }
    
    
    private static boolean OnkoNelio(int i) {
        long juuri = Math.round(Math.sqrt(i));
        return juuri*juuri==i;
    }


    /**
     * 
     * @param i .
     * @param p .
     * @return .
     * @example
     * <pre name="test">
     * SuurinPotenssi(10,2)===1;
     * SuurinPotenssi(8,2)===3;
     * SuurinPotenssi(0,2)===-1;
     * SuurinPotenssi(18,3)===2;
     * SuurinPotenssi(15,3)===1;
     * SuurinPotenssi(15,1)===-1;
     * SuurinPotenssi(1,4)===-1;
     * </pre>
     */
    public static int SuurinPotenssi(int i, int p) {
        int tulos = 0;
        if(p<2||i<2)return -1;
        while(i%p==0) {
            //i=i/p;
            tulos++;
        }
        return tulos;
    }

}
