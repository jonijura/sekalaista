package sijoitusPeli;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Ohjelmalla voidaan piirtää fazerin sinisen sulkaalevyillä...
 * 
 * @author Joona Räty
 * @version 1.0, 24.03.2021
 */
public class FazerPiirtaja extends PiirtoIkkuna implements MouseListener {

    private static final long serialVersionUID = 1L;
    private boolean kaannetty = false;
    private Graphics ikkuna;
    private int pikselikoko;
    private int[] levyt;
    private int valittuLevy=0;
    private int baseX=0;
    private int baseY=0;
    private int locX=0;
    private int locY=0;
    private int a=165;
    private int b=197;
    private String[] levyNimet = {"tavallinen","domino",
            "vaalea maitosuklaa","suolainen toffeekrokantti",
            "dumle","mansikka vanilja","lontoo rae",
            "popcorn","hasselpähkinä","kokonainen hasselpähkinä",
            "avec","suffeli","mustikka","marianne","tyhja"};

    /**
     * alustetaan piirtoikkuna-olio
     */
    public static void main(String[] args) {
        int[] a = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14};
        FazerPiirtaja piirtaja = new FazerPiirtaja(a,1,500);
        piirtaja.setVisible(true);
    }


    /**
     * olio joka piirtää fraktaaleja
     * @param levyt kuvassa käytettävät levyt ja niiden paikat
     * @param kuvanKoko kuvan koko
     * @param pikselikoko piirrettavien pikseleiden koko varsinaisina pikseleina
     */
    public FazerPiirtaja(int[] levyt, int pikselikoko, int kuvanKoko) {
        super(0, 0, 3*kuvanKoko, kuvanKoko);//koko levyt muuttujasta
        this.pikselikoko = pikselikoko;
        this.levyt = levyt;
        addMouseListener(this);
    }


    /**
     * piirretaan haluttu fraktaali ja tulostetaan sijainti
     * @param g ikkuna jonne piirretaan
     */
    @Override
    public void paint(Graphics g) {
        asetaIkkuna(g);
        piirraLevyt();
        // piirraKomentorivilla(); //vanha koodi kommenteissa
    }


    /**
     * siirretaan ja skaalataan piirtoikkuna sopivaksi
     * @param g ikkuna
     */
    private void asetaIkkuna(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(10, 30);
        g2.scale(1, 1);
        ikkuna = g2;
    }



    /**
     * piirretaan kuvassa käytettävät levyt
     */
    public void piirraLevyt() {
        if(locX==0 & locY==0) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File("./suklaat_ohj.bmp"));
            this.baseX = image.getWidth()/3;
            this.baseY = image.getHeight()/5;
            for(int levyNum=0; levyNum<levyt.length; levyNum++) {
                int levyX = levyt[levyNum]%3;
                int levyY = levyt[levyNum]/3;
                for(double i=a+levyX*baseX; i<(levyX+1)*baseX;i++) {
                    for(double j=levyY*baseY; j<(levyY+1)*baseY; j++) {
                        double jm = j%baseY;
                        double im = i%baseX;
                        if(jm<((im-a)/(b-a)*baseY)) {
                            ikkuna.setColor(new Color(image.getRGB((int)i,(int)j)));
                            ikkuna.fillRect((int)(i-a-levyX*baseX+levyNum*(baseX-a))*pikselikoko, (int)(j-levyY*baseY)*pikselikoko, pikselikoko, pikselikoko);
                        }
                        
                    }
                }
            }
            
        } catch (IOException e) {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            e.printStackTrace();
        }
        }
        else {
            BufferedImage image;
            try {
                image = ImageIO.read(new File("./suklaat_ohj.bmp"));

                    int levyX = levyt[valittuLevy]%3;
                    int levyY = levyt[valittuLevy]/3;
                    if(kaannetty) {
                    for(double i=a+levyX*baseX; i<(levyX+1)*baseX;i++) {
                        for(double j=levyY*baseY; j<(levyY+1)*baseY; j++) {
                            double jm = j%baseY;
                            double im = i%baseX;
                            if(jm<((im-a)/(b-a)*baseY)) {
                                ikkuna.setColor(new Color(image.getRGB((int)i,(int)j)));
                                ikkuna.fillRect((int)(locX+baseX-a-(i-a-levyX*baseX))*pikselikoko, (int)(locY+baseY-(j-levyY*baseY))*pikselikoko, pikselikoko, pikselikoko);
                            }
                            
                        }
                    }
                    }
                    else {
                        for(double i=a+levyX*baseX; i<(levyX+1)*baseX;i++) {
                            for(double j=levyY*baseY; j<(levyY+1)*baseY; j++) {
                                double jm = j%baseY;
                                double im = i%baseX;
                                if(jm<((im-a)/(b-a)*baseY)) {
                                    ikkuna.setColor(new Color(image.getRGB((int)i,(int)j)));
                                    ikkuna.fillRect((int)(i-a-levyX*baseX+locX)*pikselikoko, (int)(j-levyY*baseY+locY)*pikselikoko, pikselikoko, pikselikoko);
                                }
                                
                            }
                        }
                    }
                
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    /**
     * zoomataan kuvaa klikkauskohdassa (hiiren vasen)
     * loitonnetaan kuvaa (hiiren oikea)
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (e.getButton()) {
        case (1):
            if(e.getY()>baseY) {
                locX=e.getX()-(e.getX()-e.getY()/baseY*(b-a))%(2*baseX-a-b)-10;
                kaannetty = false;
                if((locX+baseX-a)<(e.getX()-10)) {
                    locX+=baseX-a;
                    kaannetty = true;
                }
                locY=e.getY()-e.getY()%baseY-30;
                paint(getGraphics());
                return;
            }
            if(e.getX()/(baseX-a)<levyNimet.length) {
                valittuLevy=e.getX()/(baseX-a);
                System.out.println(levyNimet[valittuLevy]);
            }
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
