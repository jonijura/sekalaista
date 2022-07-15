package sijoitusPeli;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.imageio.ImageIO;

/**
 * Ohjelmalla voidaan piirtää sekä tutkia mandelbrotin joukkoa sekä juliajoukkoja.
 * kuvaa voi ohjata hiirellä seuraavasti:
 * vasen painike suurentaa, oikea pienentää, hiiren sivunapeilla voi muuttaa iterointirajaa.
 * rullapainike piirtää tarkemman kuvan png-tiedostoksi hakemistoon, jossa ohjelma ajetaan
 * C:\MyTemp\Ohj2\ws\sekalaista
 * 
 * ohjelman alkuarvoja voi vaihtaa mainin alla muuttamalla Fraktaali-olion luontiparametreja
 * 
 * @author Joona Räty
 * @version 1.0, 24.03.2019
 */
public class Fraktaali extends PiirtoIkkuna implements MouseListener {

    private static final long serialVersionUID = 1L;
    private int maxIter;
    private Color[] paletti;
    private Graphics ikkuna;
    private double sijaintiX, sijaintiY, tarkkuus;
    private int pikselikoko;
    private int kuvanKoko;
    private boolean mandel;
    private double juliaX;
    private double juliaY;
    
    private static Color rajaVari = Color.black;//new Color(135,206,250);

    /**
     * alustetaan piirtoikkuna-olio
     */
    public static void main(String[] args) {
        //Fraktaali piirtaja = new Fraktaali(3,300,3,-0.4,0.6);//[väri] (1-3), [koko], [tarkkuus], ylimääräiset valinnaiset parametrit: [x],[y] julia joukon iterointipisteet
        Fraktaali piirtaja = new Fraktaali(3,300,3); //mandelbrotin joukko
        piirtaja.setVisible(true);
    }


    /**
     * olio joka piirtää fraktaaleja
     * @param varit vari paletti
     * @param koko kuvan koko
     * @param pikselikoko piirrettavien pikseleiden koko varsinaisina pikseleina
     */
    public Fraktaali(int varit, int koko, int pikselikoko) {
        super(50, 50, 2 * koko, 2 * koko);
        
        this.mandel = true;
        this.maxIter = 80;
        this.paletti = new VariPaletti(varit).getPaletti();
        this.sijaintiX = 0;
        this.sijaintiY = 0;
        this.tarkkuus = 1;
        this.pikselikoko = pikselikoko;
        this.kuvanKoko = koko;
        addMouseListener(this);
    }
    

    /**
     * olio joka piirtää fraktaaleja
     * @param varit vari paletti
     * @param koko kuvan koko
     * @param pikselikoko piirrettavien pikseleiden koko varsinaisina pikseleina
     * @param juliaX julia-joukon iterointipiste x
     * @param juliaY julia-joukon iterointipiste y
     */
    public Fraktaali(int varit, int koko, int pikselikoko, double juliaX, double juliaY) {
        super(50, 50, 2 * koko, 2 * koko);
        
        this.mandel = false;
        this.juliaX = juliaX;
        this.juliaY = juliaY;
        this.maxIter = 30;
        this.paletti = new VariPaletti(varit).getPaletti();
        this.sijaintiX = 0;
        this.sijaintiY = 0;
        this.tarkkuus = 1;
        this.pikselikoko = pikselikoko;
        this.kuvanKoko = koko;
        addMouseListener(this);
    }


    /**
     * piirretaan haluttu fraktaali ja tulostetaan sijainti
     * @param g ikkuna jonne piirretaan
     */
    @Override
    public void paint(Graphics g) {
        asetaIkkuna(g);
        if(mandel) {piirraMandelbrot();}
        else {piirraJulia();}
        tulostaSijainti(System.out);
        // piirraKomentorivilla(); //vanha koodi kommenteissa
    }


    /**
     * siirretaan ja skaalataan piirtoikkuna sopivaksi
     * @param g ikkuna
     */
    private void asetaIkkuna(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(kuvanKoko, kuvanKoko);
        g2.scale(1, -1);
        ikkuna = g2;
    }


        /**
         * piirretaan suurempi ja tarkempi kuva tiedostoon.
         * Tulostetaan prosessin eteneminen noin 10% valein.
         * tulostaa ok kun valmista.
         */
    private void piirraTiedostoon() {
        System.out.println("Aloitetaan piirtaminen, tama voi hiukan kestaa.");
        int pxkoko = tiedostoTarkkuus();
        BufferedImage image = new BufferedImage(1920, 1080,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        ikkuna = g;
        int rajap = 192;
        int raja = rajap;
        for (double i = 0; i <= 1920; i += pikselikoko) {
            for (double j = 0; j <= 2 * 1080; j += pikselikoko) {
                if(mandel) {ikkuna.setColor(mandelbrot(i - 960, j - 540));}
                else {ikkuna.setColor(julia(i - 960, j - 540));}
                ikkuna.fillRect((int) i, (int) (1080 - j), pikselikoko,
                        pikselikoko);
            }
            if(i==raja) {
                System.out.println(10*raja/rajap+"% suoritettu");
                raja+=rajap;
            }
        }
        try {
            ImageIO.write(image, "png", new File(tiedostonNimi()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Kuva on valmis");
        piirtoTarkkuus(pxkoko);
        // System.exit(0);
    }


    private String tiedostonNimi() {
            return "ManX="+String.format("%.2f", sijaintiX)+"Y="+String.format("%.2f", sijaintiY)+".png";
        }


    private void piirtoTarkkuus(int pxkoko) {
        pikselikoko = pxkoko;
        tarkkuus = tarkkuus / 3;
    }


    private int tiedostoTarkkuus() {
        int palautus = pikselikoko;
        pikselikoko = 1;
        tarkkuus *= 3;
        return palautus;
    }


    /**
     * piirretaan mandelbrot setti
     */
    public void piirraMandelbrot() {
        for (double i = 20 - kuvanKoko; i <= kuvanKoko - 20; i += pikselikoko) {
            for (double j = 20 - kuvanKoko; j <= kuvanKoko
                    - 20; j += pikselikoko) {
                ikkuna.setColor(mandelbrot(i, j));
                ikkuna.fillRect((int) i, (int) j, pikselikoko, pikselikoko);
            }
        }
    }


    /**
     * @param i skaalaamaton piste x akselille
     * @param j skaalaamaton piste x akselille
     * @return vari, joka talle pisteelle kuuluu
     */
    public Color mandelbrot(double i, double j) {
        double ux, vx, uy, vy;
        vx = (i + tarkkuus * sijaintiX) / (tarkkuus * 100);
        vy = (j + tarkkuus * sijaintiY) / (tarkkuus * 100);
        double cx = vx;
        double cy = vy;

        int iter = 0;

        for (int k = 0; k < maxIter; k++) {
            ux =  vx*vx - vy*vy + cx;
            uy = 2 * vx * vy + cy;
            vx = ux;
            vy = uy;
            if (ux*ux+uy*uy > 4)
                return paletti[(iter*paletti.length)/maxIter];//iter % paletti.length
            iter++;
        }
        return rajaVari;
    }


    /**
     * piirretaan julia setti
     */
    public void piirraJulia() {
        for (double i = 20 - kuvanKoko; i <= kuvanKoko - 20; i += pikselikoko) {
            for (double j = 20 - kuvanKoko; j <= kuvanKoko
                    - 20; j += pikselikoko) {
                ikkuna.setColor(julia(i, j));
                ikkuna.fillRect((int) i, (int) j, pikselikoko, pikselikoko);
            }
        }
    }


    /**
     * @param i skaalaamaton piste x akselille
     * @param j skaalaamaton piste x akselille
     * @return vari, joka talle pisteelle kuuluu
     */
    public Color julia(double i, double j) {
        double ux, vx, uy, vy;
        vx = (i + tarkkuus * sijaintiX) / (tarkkuus * 100);
        vy = (j + tarkkuus * sijaintiY) / (tarkkuus * 100);

        int iter = 0;

        for (int k = 0; k < maxIter; k++) {
            ux = vx * vx - vy * vy + juliaX;
            uy = 2 * vx * vy + juliaY;
            vx = ux;
            vy = uy;
            if (ux * ux + uy * uy > 4)
                return paletti[iter % paletti.length];
            iter++;
        }
        return Color.black;
    }


    /**
     * piirretään mandelbrotin fraktaalia, komentorivilta voi tarkentaa ja siirtaa kuvaa
     *  
    public void piirraKomentorivilla() {
        double tarkkuusTemp = 1;
        StringBuilder sb;
        while (true) {
            piirraMandelbrot();
            sb = new StringBuilder(Syotto.kysy("tarkenna, siirrä x, siirrä y"));
            if (sb.length() == 0)
                break;
            if (sb.toString().contentEquals("T"))
                maxIter *= 2;
            tarkkuusTemp *= Mjonot.erotaDouble(sb, 1);
            sijaintiX += Mjonot.erotaDouble(sb, 0) / tarkkuus;
            sijaintiY += Mjonot.erotaDouble(sb, 0) / tarkkuus;
            tarkkuus = tarkkuusTemp;
            System.out.println(
                    " " + tarkkuus + " " + sijaintiX + " " + sijaintiY);
        }
    }*/
   


    /**
     * tulostetaan piirtokohdan tarkkuus ja keskipisteen sijainti
     * @param ps minne tulostetaan
     */
    public void tulostaSijainti(PrintStream ps) {
        ps.println("X=" + sijaintiX + " Y=" + sijaintiY + " Tarkkuus="
                + Math.round(tarkkuus));
    }


    /**
     * zoomataan kuvaa klikkauskohdassa (hiiren vasen)
     * loitonnetaan kuvaa (hiiren oikea)
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (e.getButton()) {
        case (1):
            sijaintiX += (e.getX() - kuvanKoko) / tarkkuus;
            sijaintiY += (kuvanKoko - e.getY()) / tarkkuus;
            tarkkuus *= 2;
            paint(getGraphics());
            return;
        case (2):
            asetaIkkuna(getGraphics());
            piirraTiedostoon();
            return;
        case (3):
            tarkkuus = tarkkuus / 2;
            paint(getGraphics());
            return;
        case (4):
            maxIter += maxIter/3;
            System.out.println("uusi iterointiraja on "+maxIter);
            return;
        case (5):
            maxIter -= maxIter/3;
            System.out.println("uusi iterointiraja on "+maxIter);
            return;
        default:
            return;
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
        //
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        //

    }


    @Override
    public void mouseEntered(MouseEvent e) {
        //

    }


    @Override
    public void mouseExited(MouseEvent e) {
        //
    }
}
