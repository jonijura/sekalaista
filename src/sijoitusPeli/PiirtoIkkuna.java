package sijoitusPeli;

import java.awt.BasicStroke;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
     * Ikkunaluokka johon piirretään
     */
    public class PiirtoIkkuna extends Frame{
        private static final long serialVersionUID = 1L;

        /**
         * Alustetaan ikkunan rajat 
         * @param x ylänurkan x
         * @param y ylänurkan y 
         * @param w ikkunan leveys
         * @param h ikkunan kokrkeus
         */
        public PiirtoIkkuna(int x, int y, int w, int h) {
            super();
            setSize(w, h);
            setLocation(x, y);
            addWindowListener(new SuljeIkkuna());
        }

        /**
         * Ikkunan sulkeva luokka
         */
        protected class SuljeIkkuna extends WindowAdapter {
            /**
             * Sulkemismetodi
             * @param event sulkemistapahtuman tiedot
             */
            @Override
            public void windowClosing(WindowEvent event) {
                System.exit(0); // NOPMD
            }
        }

        /**
         * Metodi jota kutsutaan aina kun ikkuna haluaa
         * piirtää itsensä uudelleen.
         * Piirretään aina sama viiva
         * @param g Grfiikkaolio jolla tiedot piirtopinnasta
         */
        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.translate(300, 300); // siirretään origo keskelle
            g2.scale(1, -1); // skaalataan
            g2.setStroke(new BasicStroke(1 / (float) 400)); // kynän paksuus
            g2.fillRect(0, 0, 5, 5);
        }


    /**
     * Luodaan pääohjelmassa piirto-ikkuna ja laitetaan
     * se näkyville.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        PiirtoIkkuna ikkuna = new PiirtoIkkuna(50, 50, 600, 600);
        ikkuna.setVisible(true); // ikkuna.show();
    }
}