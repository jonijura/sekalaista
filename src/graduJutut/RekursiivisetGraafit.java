/**
 * 
 */
package graduJutut;

import java.awt.Color;

import fi.jyu.mit.graphics.EasyWindow;
import fi.jyu.mit.graphics.Line;

/**
 * @author Joona1
 * @version 6.3.2022
 * neljä eri versiota säännöllisien puiden piirtämiseen
 */
public class RekursiivisetGraafit {
    private EasyWindow ikkuna;
    private Color[] varit = { Color.black, Color.red, Color.green, Color.orange,
            Color.magenta };

    /**
     * konstruktori
     */
    public RekursiivisetGraafit() {
        this.ikkuna = new EasyWindow(1000, 1000);
        ikkuna.move(500, 500, 0);
        ikkuna.scale(1, -1, 0);
    }


    /**
     * piirtää viivan
     */
    private void piirraViiva(double lx, double ly, double px, double py,
            Color c) {
        Line l = new Line(lx, ly, px, py);
        l.setColor(c);
        ikkuna.add(l);
    }


    @SuppressWarnings("unused")
    private void piirraPuuRek(double k, double kx, double ky, int rekstep,
            double koko, double angle, Color c, int loc, int separ) {
        if (rekstep > 0) {
            for (int i = 1; i < k; i++) {
                double kk = 2 * Math.PI * i / k + angle;
                double tx = kx + koko * Math.cos(kk);
                double ty = ky + koko * Math.sin(kk);
                Color c2 = c;
                if (separ == loc)
                    c2 = varit[i % varit.length];
                piirraViiva(kx, ky, tx, ty, c2);
                piirraPuuRek(k, tx, ty, rekstep - 1, koko / (1.5 * Math.log(k)),
                        kk + Math.PI, c2, 10 * loc + i, separ);
            }
        }
    }


    @SuppressWarnings("unused")
    private void piirraPuuRek2(double k, double kx, double ky, int rekstep,
            int stepnum, double koko, double angle, Color c, int loc,
            int separ) {
        if (rekstep < stepnum) {
            double rotangle = Math.PI
                    / (k * (k - 2) * Math.pow(2, rekstep - 1));
            for (int i = 0; i < k - 1; i++) {
                double kk = i * rotangle + angle - (k - 2) / 2 * rotangle;
                double tx = kx + koko * Math.cos(kk);
                double ty = ky + koko * Math.sin(kk);
                Color c2 = c;
                if (separ == loc)
                    c2 = varit[i % varit.length];
                piirraViiva(kx, ky, tx, ty, c2);
                piirraPuuRek2(k, tx, ty, rekstep + 1, stepnum, koko, kk, c2,
                        10 * loc + i, separ);
            }
        }
    }


    @SuppressWarnings("unused")
    private void piirraPuuRek3(double k, int rekstep, double kx, double ky,
            int stepnum, double koko, double sade, double angle) {
        if (rekstep < stepnum) {
            double rotangle = Math.PI / (k * Math.pow(k - 1, rekstep));
            double sade2 = sade * Math.cos(rotangle) + koko
                    * Math.cos(Math.asin(sade * Math.sin(rotangle) / koko));
            for (int i = 0; i < k - 1; i++) {
                double kk = 2 * i * rotangle + angle - rotangle;
                double tx = sade2 * Math.cos(kk);
                double ty = sade2 * Math.sin(kk);
                piirraViiva(kx, ky, tx, ty, Color.blue);
                piirraPuuRek3(k, rekstep + 1, tx, ty, stepnum, koko, sade2, kk);
            }
        }

    }


    @SuppressWarnings("unused")
    private void piirraPuuRek4(double k, int rekstep, double kx, double ky,
            int stepnum, double koko, int paikka) {
        if (rekstep < stepnum) {
            double viivoja = k * Math.pow(k - 1, rekstep);
            double rotangle = 2 * Math.PI / viivoja;
            double r = (rekstep + 1) * koko;
            for (int i = 0; i < k - 1; i++) {
                double kk = (((k - 1) * paikka + viivoja - 1 + i) % viivoja)
                        * rotangle;
                double tx = r * Math.cos(kk);
                double ty = r * Math.sin(kk);
                double d = Math
                        .sqrt((tx - kx) * (tx - kx) + (ty - ky) * (ty - ky));
                double nx = kx + koko * (tx - kx) / d;// tx;//
                double ny = ky + koko * (ty - ky) / d;// ty;//
                piirraViiva(kx, ky, nx, ny, Color.blue);
                piirraPuuRek4(k, rekstep + 1, nx, ny, stepnum, koko, (int) Math
                        .round(((k - 1) * paikka + viivoja - 1 + i) % viivoja));
            }
        }

    }


    @SuppressWarnings("unused")
    private void piirraPuu(double k, int rekstep, double koko, int separ) {
        for (int i = 0; i < k; i++) {
            double kk = 2 * Math.PI * i / k;
            double tx = 4 * koko * Math.cos(kk);
            double ty = 4 * koko * Math.sin(kk);
            Color vari = varit[0];
            if (separ == 0) {
                vari = varit[i % varit.length];
            }
            piirraViiva(0, 0, tx, ty, vari);
            piirraPuuRek(k, tx, ty, rekstep, 4 * koko / (1.5 * Math.log(k)),
                    kk + Math.PI, vari, i + 1, separ);
            // piirraPuuRek2(k, tx, ty, 1, rekstep, koko,
            // kk, vari, i+1, separ);
            // piirraPuuRek3(k, 1, tx, ty, rekstep, koko, koko, kk);
            // piirraPuuRek4(k, 1, tx, ty, rekstep, koko, i);
        }
    }


    private void piirra23Cay(int rekstep, double koko) {
        for (int i = 0; i < 3; i++) {
            double kk = 2 * Math.PI * i / 3;
            double kk2 = 2 * Math.PI * (i + 1) / 3;
            double tax = koko * Math.cos(kk) / Math.sqrt(3);
            double tay = koko * Math.sin(kk) / Math.sqrt(3);
            double tax2 = koko * Math.cos(kk2) / Math.sqrt(3);
            double tay2 = koko * Math.sin(kk2) / Math.sqrt(3);
            double tx = koko * Math.cos(kk) * (1 + 1 / Math.sqrt(3));
            double ty = koko * Math.sin(kk) * (1 + 1 / Math.sqrt(3));
            piirraViiva(tax, tay, tx, ty, Color.black);
            piirraViiva(tax, tay, tax2, tay2, Color.black);
            piirra23CayRek(tx, ty, rekstep, 3 * koko / 5, kk);
        }
    }


    private void piirra23CayRek(double tx, double ty, int rekstep, double koko,
            double kk) {
        if (rekstep == 0)
            return;
        double p1x = tx + koko * Math.cos(kk - Math.PI / 6);
        double p1y = ty + koko * Math.sin(kk - Math.PI / 6);
        piirraViiva(tx, ty, p1x, p1y, Color.black);
        double p1jx = p1x + koko * Math.cos(kk - Math.PI / 3);
        double p1jy = p1y + koko * Math.sin(kk - Math.PI / 3);
        piirraViiva(p1jx, p1jy, p1x, p1y, Color.black);
        piirra23CayRek(p1jx, p1jy, rekstep - 1, 3 * koko / 5, kk - Math.PI / 3);
        double p2x = tx + koko * Math.cos(kk + Math.PI / 6);
        double p2y = ty + koko * Math.sin(kk + Math.PI / 6);
        piirraViiva(tx, ty, p2x, p2y, Color.black);
        double p2jx = p2x + koko * Math.cos(kk + Math.PI / 3);
        double p2jy = p2y + koko * Math.sin(kk + Math.PI / 3);
        piirraViiva(p2jx, p2jy, p2x, p2y, Color.black);
        piirra23CayRek(p2jx, p2jy, rekstep - 1, 3 * koko / 5, kk + Math.PI / 3);
        piirraViiva(p1x, p1y, p2x, p2y, Color.black);
    }


    /**
     * @param args .
     */
    public static void main(String[] args) {

        RekursiivisetGraafit rp = new RekursiivisetGraafit();
        // rp.piirraPuu(4,5,30, 55555);
        rp.piirra23Cay(6, 60);

    }

}
