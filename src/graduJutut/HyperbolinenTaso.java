/**
 * 
 */
package graduJutut;

import java.awt.Color;
import java.util.ArrayList;

import fi.jyu.mit.graphics.EasyWindow;
import fi.jyu.mit.graphics.Line;

/**
 * @author Joona1
 * @version 17.5.2022
 * Hyperbolisen tason kolmiolaatoituksen piirtämiseen poincaren pallo/puolitasomallilla
 * tasomallia varten piirraKaari metodista voi poistaa kahdesta kohdasta transformoinnin pallomalliin
 */
public class HyperbolinenTaso {

    private EasyWindow ikkuna;

    /**
     * konstruktori
     */
    public HyperbolinenTaso() {
        this.ikkuna = new EasyWindow(1000, 1000);
        ikkuna.move(500, 500, 0);
        ikkuna.scale(1, -1, 0);
        Line l = new Line(-200, 0, 200, 0);
        l.setColor(Color.blue);
        // ikkuna.add(l);
    }


    private Mobius piirraKaari(Comp c1, Comp c2) {
        return piirraKaari(c1, c2, Color.black);
    }


    /**
     * piirtää annettuja pisteitä vastaavan hyperbolisen geodeesin kuvajoukon.
     * kommentoimalla pois rivit Mobius.ballMod voidaan valita puolitason ja pallomallin välillä.
     * Palauttaa Mobius kuvauksen, joka kuvaa imaginaari akselin sivuksi, jolla annetut pisteet sijaitsee
     */
    private Mobius piirraKaari(Comp c1, Comp c2, Color c) {
        Comp z1, z2;
        // points are on the same vertical line -> the geodesic is a line
        if (Math.abs(c1.r - c2.r) < 0.0001) {
            // jaetaan viiva pienempiin osiin möbius transformaatiota varten
            for (int i = 0; i < 10; i++) {
                z1 = new Comp(c1.r,
                        Math.min(c1.i, c2.i) + i / 10 * Math.abs(c1.i - c2.i));
                z2 = new Comp(c1.r, Math.min(c1.i, c2.i)
                        + (i + 1) / 10 * Math.abs(c1.i - c2.i));
                // Transform points to poincare ball model
                z1 = Mobius.ballMod(z1);
                z2 = Mobius.ballMod(z2);
                Line l = new Line(z1.r, z1.i, z2.r, z2.i);
                l.setColor(c);
                ikkuna.add(l);
            }
            return new Mobius(1, c1.r, 0, 1);
        }
        // puoliympyran keskipiste
        double kp = (c1.n2() - c2.n2()) / (2 * (c1.r - c2.r));
        Comp tmp = new Comp(c1.r - kp, c1.i);
        // puoliympyran sade
        double rad = Math.sqrt(tmp.n2());
        // approksimoidaan ympyrää lyhyillä viivoilla
        double alkuk = Math.asin(c1.i / rad);
        double loppuk = Math.asin(c2.i / rad);
        if (c1.r < kp)
            alkuk = Math.PI - alkuk;
        if (c2.r < kp)
            loppuk = Math.PI - loppuk;
        if (alkuk > loppuk) {
            double tmp1 = loppuk;
            loppuk = alkuk;
            alkuk = tmp1;
        }
        for (int i = 0; i < 10; i++) {
            double kk = alkuk + (loppuk - alkuk) * i / 10;
            double kk2 = alkuk + (loppuk - alkuk) * (i + 1) / 10;
            z1 = new Comp(kp + rad * Math.cos(kk), rad * Math.sin(kk));
            z2 = new Comp(kp + rad * Math.cos(kk2), rad * Math.sin(kk2));
            // Transform points to poincare ball model
            z1 = Mobius.ballMod(z1);
            z2 = Mobius.ballMod(z2);
            Line l = new Line(z1.r, z1.i, z2.r, z2.i);
            l.setColor(c);
            ikkuna.add(l);
        }
        Mobius A = new Mobius(1, -1, 1, 1);
        Mobius B = new Mobius(rad, 0, 0, 1);
        Mobius C = new Mobius(1, kp, 0, 1);
        return C.kertaa(B.kertaa(A));
    }


    /**
     * piirretään hyperbolisen tason laatoitus annetulla kolmiolla (n,m,k)
     * luvut määräävät fundamentaalisen kolmion kulmien suuruudet a=pi/n, b=pi/m ja c=pi/k
     * kulmaa pi/n vastaavassa kärjessä kohtaa 2n kolmiota. Jos 2n ei ole kokonaisluku,
     * kuvasta tulee odotetusti sekasorto. Parametri rounds määrää montako kertaa
     * kolmiokonstruktio heijastetaan sivujensa yli, suoritusaika kasvaa exponentiaalisesti,
     * lukujen n,m ja k suhteen. jos 1/n+1/m+1/k>=1, kolmiolla ei voi laatoittaa hyperbolista
     * tasoa eikä ohjelma piirrä mitään. äärettömyyspisteet ei toimi, joten kulma ei voi olla
     * nolla. Pienillä kulmilla pallomalliin tulee suuria epätarkkuuksia, en tiedä miksi.
     * pallomallin ja puolitasomallin vaihtaminen piirraKaari-metodissa
     * @param args .
     */
    public static void main(String[] args) {
        double pi = Math.PI;
        // aseta tähän kolmion kulmien suuruudet
        double a = pi / 4;
        double b = pi / 4;
        double c = pi / 4;
        int rounds = 5;// montako peilauskierrosta, noin 5 on useimmiten hyvä
        // kuvan skaalaamiseen
        double s = 100;
        // etsitään kolmion (n,m,k) kärkipisteet, jokseenkin kinkkistä
        double A = (Math.cos(c) * Math.cos(a) + Math.cos(b))
                / (Math.sin(c) * Math.sin(a));
        double r = (A + Math.sqrt(A * A - 1)) * Math.sin(a) / Math.sin(c);
        double x = -r * Math.cos(c) - Math.cos(a);
        double theta = Math.acos((1 + x * x - r * r) / (-2 * x));
        Comp k1 = new Comp(-Math.cos(a), r * Math.sin(c));
        Comp k2 = new Comp(-Math.cos(a), Math.sin(a));
        Comp k3 = new Comp(Math.cos(pi - theta), Math.sin(theta));
        // tulostetaan kolmion kärkipisteet puolitasomallissa
        System.out.println("Valitun kolmion kärkipisteet puolitasomallissa:");
        System.out.println(k1);
        System.out.println(k2);
        System.out.println(k3);

        HyperbolinenTaso ht = new HyperbolinenTaso();
        Comp[] pts = new Comp[3];
        pts[0] = new Comp(k1.r, k1.i, s);
        pts[1] = new Comp(k2.r, k2.i, s);
        pts[2] = new Comp(k3.r, k3.i, s);
        Mobius[] mbs = new Mobius[4];
        mbs[3] = new Mobius(1, 0, 0, 1);
        for (int i = 0; i < 3; i++) {
            mbs[i] = ht.piirraKaari(pts[i % 3], pts[(i + 1) % 3], Color.red);
        }
        // lasketaan kärjet ja sivut iteratiivisesti, sides listaan tulee verts
        // listan indeksipareja, jotka
        // edustavat sivuja.
        ArrayList<Comp> verts = new ArrayList<Comp>();
        ArrayList<Comp> sides = new ArrayList<Comp>();
        verts.add(pts[0]);
        verts.add(pts[1]);
        verts.add(pts[2]);
        sides.add(new Comp(0, 1));
        sides.add(new Comp(1, 2));
        sides.add(new Comp(2, 0));
        for (int i = 0; i < rounds; i++) {// iterointi peilauskierroksien yli
            for (int j = 0; j < 3; j++) {// iterointi sivuja vastaavien
                                         // peilauksien yli
                int lkm = 0;
                int size = sides.size();
                int vsize = verts.size();
                for (int k = 0; k < size; k++) {// iterointi olemassa olevien
                                                // sivujen yli ja niiden peilaus
                    Comp nc1 = mbs[j]
                            .refl(verts.get((int) Math.round(sides.get(k).r)));
                    Comp nc2 = mbs[j]
                            .refl(verts.get((int) Math.round(sides.get(k).i)));
                    boolean flag1 = false;// pidetään kirjaa onko pistettä jo
                                          // olemassa ja jos on niin etsitään se
                    boolean flag2 = false;
                    int nc1loc = 0;
                    int nc2loc = 0;
                    for (int l = 0; l < verts.size(); l++) {
                        Comp c3 = verts.get(l);
                        if (Math.abs(c3.r - nc1.r)
                                + Math.abs(c3.i - nc1.i) < 0.01) {
                            nc1loc = l;
                            flag1 = true;
                        }
                        if (Math.abs(c3.r - nc2.r)
                                + Math.abs(c3.i - nc2.i) < 0.01) {
                            nc2loc = l;
                            flag2 = true;
                        }
                        if (flag1 & flag2)
                            break;
                    }
                    if (!flag1) {
                        verts.add(nc1);
                        nc1loc = vsize + lkm++;
                    }
                    if (!flag2) {
                        verts.add(nc2);
                        nc2loc = vsize + lkm++;
                    }
                    if (!flag2 | !flag1) {
                        sides.add(new Comp(nc1loc, nc2loc));
                    } else {
                        boolean flag = false;
                        // katsotaan onko sivua vielä olemassa
                        for (Comp si : sides) {
                            if (Math.abs(si.r - nc1loc)
                                    + Math.abs(si.i - nc2loc) < 0.1
                                    | Math.abs(si.r - nc2loc)
                                            + Math.abs(si.i - nc1loc) < 0.1) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag)
                            sides.add(new Comp(nc1loc, nc2loc));
                    }
                }
            }
        }
        //piirretään kaikki sivut
        for (Comp cc : sides) {
            ht.piirraKaari(verts.get((int) Math.round(cc.r)),
                    verts.get((int) Math.round(cc.i)));
        }
        //alkuperäinen kolmio punaisella
        for (int i = 0; i < 3; i++) {
            mbs[i] = ht.piirraKaari(pts[i % 3], pts[(i + 1) % 3], Color.red);
        }

    }
}
