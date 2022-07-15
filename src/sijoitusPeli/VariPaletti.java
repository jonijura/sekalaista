package sijoitusPeli;

import java.awt.Color;

/**
 * luodaan taulukollinen vareja kauniilla gradientilla
 * @author Joona1
 * @version 21.4.2020
 *
 */
public class VariPaletti {
    private Color[] paletti;
    
    /**
     * moudostaja variPaletille
     * @param versio paletin versio
     */
    public VariPaletti(int versio) {
        switch (versio) {
            case 1:
                tayta1();
                break;
            case 2:
                tayta2();
                break;
            case 3:
                tayta3();
                break;
            default:
                tayta0();
                break;
        }

    }
    
    
    /**
     * saantimetodi paletille nopeampaa käyttöä varten
     * @return paletti
     */
    public Color[] getPaletti() {
        return paletti;
    }
    
    
    /**
     * mustavalkoinen paletti
     */
    public void tayta0() {
        paletti = new Color[2];
        paletti[0]=Color.black;
        paletti[1]=Color.white;
    }
    

    /**
     * taytetaan varipaletti versio 1 TODO kuvaus
     */
    public void tayta1() {
        this.paletti = new Color[75];
        int[] rgb = { 0, 150, 0 };
        for (int i = 0; i < 25; i++) {
            rgb[0] += 10;
            rgb[1] -= 6;
            paletti[i] = new Color(rgb[0], rgb[1], rgb[2]);
        } // 250,0,0
        for (int i = 25; i < 50; i++) {
            rgb[2] += 10;
            rgb[0] -= 6;
            paletti[i] = new Color(rgb[0], rgb[1], rgb[2]);
        } // 100,0,250
        for (int i = 50; i < 75; i++) {
            rgb[1] += 6;
            rgb[0] -= 4;
            rgb[2] -= 10;
            paletti[i] = new Color(rgb[0], rgb[1], rgb[2]);
        } // 0,150,0
    }

    
    /**
     * taytetaan varipaletti versio 2 tasainen mustavalkionen
     */
    public void tayta2() {
        this.paletti = new Color[500];
        int[] rgb = { 0, 0, 0 };
        for (int i = 0; i < 250; i++) {
            rgb[0]++;
            rgb[1]++;
            rgb[2]++;
            paletti[i] = new Color(rgb[0], rgb[1], rgb[2]);
        }
        for (int i = 250; i < 500; i++) {
            rgb[0]--;
            rgb[1]--;
            rgb[2]--;
            paletti[i] = new Color(rgb[0], rgb[1], rgb[2]);
        }
    }
    
    
    /**
     * 
     */
    public void tayta3() {
        this.paletti = new Color[500];
        for(int i=0;i<500;i++) {
            paletti[i] = new Color(g(i,255,0,2),0,g(i,150,100,2));
        }
    }
    
    private int g(double i, double j,double o, double k) {
        return (int)(Math.round(Math.abs(o+j*Math.sin(k*i/160))));
    }

}
