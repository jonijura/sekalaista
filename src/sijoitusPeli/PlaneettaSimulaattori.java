/**
 * 
 */
package sijoitusPeli;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import fi.jyu.mit.graphics.EasyWindow;

/**
 * @author Joona1
 * @version 27.3.2020
 *
 */
public class PlaneettaSimulaattori extends TimerTask{
    private EasyWindow w;
    private Timer t;
    private ArrayList<Planeetta> planeetat;
    /**
     * @param args .
     */
    public static void main(String[] args) {
        var ps = new PlaneettaSimulaattori();
        var p = new Planeetta("venus",70,Color.red,200,200);
        p.setLiikkuvuus(false);
        ps.lisaa(p);
        ps.lisaa(new Planeetta("merkurius",100,Color.blue,100,100));
        ps.lisaa(new Planeetta("maa",85,Color.green,200,100));
        ps.lisaa(new Planeetta("kuu",8,Color.black,200,200));
        ps.lisaa(new Planeetta("kuu2",8,Color.black,300,200));
        ps.aloita();
    }
    
    
    private void lisaa(Planeetta planeetta) {
        planeetat.add(planeetta);
        w.add(planeetta.getKuva());
    }
    
    
    private void aloita() {
        t.schedule(this,0,20);
    }
    /**
     * muodostaja
     */
    public PlaneettaSimulaattori() {
        this.w = new EasyWindow();
        this.t = new Timer();
        this.planeetat = new ArrayList<Planeetta>();
    }



    @Override
    public void run() {
        muutaPlaneettojenNopeuksia();
        liikutaPlaneettoja();
    }


    private void liikutaPlaneettoja() {
        for(Planeetta p:planeetat) {
            p.siirry();
        }
    }


    private void muutaPlaneettojenNopeuksia() {
        for(Planeetta p:planeetat) {
            for(Planeetta p2:planeetat) {
                if(p!=p2)p.muutaNopeutta(p2);
            }
        }
    }

}
