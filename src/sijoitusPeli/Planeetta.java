package sijoitusPeli;

import java.awt.Color;

import fi.jyu.mit.graphics.Circle;
import fi.jyu.mit.graphics.Drawable;

/**
 * luokka planeetoille
 * @author Joona1
 * @version 27.3.2020
 *
 */
public class Planeetta {
    private final static double gravitaatiovakio = 0.01;
    private final static double hidastusKerroin = 0.70;
    private String nimi;
    private Circle kuva;
    private int massa;
    private boolean liikkumaton;
    private double nopeusX = 0;
    private double nopeusY = 0;
    private double sijaintiX;
    private double sijaintiY;

    /**
     * muodostaja
     * @param nimi planeetan nimi
     * @param massa planeetan massa
     * @param c planeetalla pitaa tottakai olla vari
     * @param sijaintiX .
     * @param sijaintiY ,
     */
    public Planeetta(String nimi, int massa, Color c, double sijaintiX,
            double sijaintiY) {
        this.sijaintiX = sijaintiX;
        this.sijaintiY = sijaintiY;
        this.nimi = nimi;
        this.massa = massa;
        this.kuva = new Circle(sijaintiX, sijaintiY, Math.sqrt(massa));
        kuva.setColor(c);
    }


    /**
     * @param b onko planeetta liikkumaton
     */
    public void setLiikkuvuus(boolean b) {
        liikkumaton = b;
    }


    /**
     * @return planeetan piirrettava kuva
     */
    public Drawable getKuva() {
        return kuva;
    }


    /**
     * @param i nopeus x
     * @param j nopeus y
     */
    public void setNopeus(int i, int j) {
        nopeusX = i;
        nopeusY = j;
    }


    /**
     * @return nimi
     */
    public String getNimi() {
        return nimi;
    }


    /**
     * siirretaan planeettaa
     */
    public void siirry() {
        if (liikkumaton)
            return;
        sijaintiX += nopeusX;
        sijaintiY += nopeusY;
        if (sijaintiX < 0 || sijaintiY < 0 || sijaintiX > 500
                || sijaintiY > 500) {
            nopeusX = 0;
            nopeusY = 0;
        }
        kuva.move(nopeusX, nopeusY, 0);
    }


    /**
     * muutetaan planeetan nopeus planeetan p2 painovoiman mukaan
     * @param p2 .
     */
    public void muutaNopeutta(Planeetta p2) {
        double dx = (p2.sijaintiX - sijaintiX);
        double dy = (p2.sijaintiY - sijaintiY);
        double voima = gravitaatiovakio * massa * p2.massa / (dx * dx + dy * dy + 0.1);
        nopeusX += hidastusKerroin * voima * dx / massa;
        nopeusY += hidastusKerroin * voima * dy / massa;
    }


    /**
     * @param args .
     */
    public static void main(String[] args) {
        var p1 = new Planeetta("merkurius", 100, Color.blue, 100, 100);
        var p2 = new Planeetta("venus", 200, Color.red, 100, 200);
        System.out.println(p1.nopeusY);
        p1.muutaNopeutta(p2);
        System.out.println(p1.nopeusY);
        p1.muutaNopeutta(p2);
        System.out.println(p1.nopeusY);
    }
}
