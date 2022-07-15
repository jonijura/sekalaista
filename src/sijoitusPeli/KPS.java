package sijoitusPeli;

import java.io.PrintStream;
import fi.jyu.mit.ohj2.Syotto;

/**
 * kivi paperi sakset -pelin logiikka ja toteutus komentorivilta
 * @author Joona1
 * @version 11.4.2020
 *
 */
public class KPS {
    private final String[] muuntaja = {"KIVI","PAPERI","SAKSET"};
    private final String[] tulos = {"tasapeli","pelaaja voitti!","tietokone voitti!"};
    private byte pelaajanLiike = 0;
    private byte tietokoneenLiike = 0;
    private int kierros = 0; //pelaajan liike * 3 + lopputulos
    private int[] pisteet = new int[3];
    private KPSTietokone aly = new KPSTietokone();

    /**
     * pelataan pelia komentorivilta, graafista versiota varten avaa sijoituspeli main
     * @param args .
     */
    public static void main(String[] args) {
        var kps = new KPS();
        kps.pelaaKomentorivilta();
    }
    
    
    /**
     * @param i pelaajan liike
     * @param out minne kierroksen tulos tulostetaan
     */
    public void seuraavaKierros(int i, PrintStream out) {
        tietokoneenLiike();
        pelaajanLiike = (byte) (i%3);
        kierroksenTulos(out);
    }
    
    
    /**
     * pyydetaan seuraavaa liiketta pelaamis algoritmilta
     * luokaasta KPSTietokone
     */
    private void tietokoneenLiike() {
        tietokoneenLiike = aly.annaLiike(kierros); 
    }
    

    private void pelaaKomentorivilta() {
        int i=0;
        while(i<20) {
            tietokoneenLiike();
            String s = Syotto.kysy("Kivi, Paperi, Sakset!");
            if(s.length()==0)break;
            if(!tulkitse(s))System.out.println("virheellinen syote!");
            kierroksenTulos(System.out);
            i++;
        }  
    }
    

    /**
     * lasketaan kuinka kierroksessa kavi, kumpi voitti? ja tulostetaan
     * nama tiedot haluttuun output streamiin.
     * @param out
     */
    private void kierroksenTulos(PrintStream out) {
        out.println("Tietokone: "+muuntaja[tietokoneenLiike]);
        byte lopputulos = (byte) ((pelaajanLiike-tietokoneenLiike+3)%3);
        pisteet[lopputulos]++;
        out.println("tasapelejä: "+pisteet[0]+"\nPelaajan pisteet: "+pisteet[1]+"\nTietokoneen pisteet: "+pisteet[2]);
        out.println(tulos[lopputulos]);
        kierros = pelaajanLiike*3+lopputulos;
    }


    /**
     * tulkitaan (komentorivilta) saatu syote helpommin kasiteltavaksi
     * numeroksi ensimmaisen kirjaimen mukaan.
     * @param s syote
     * @return oliko syote kelvollinen
     */
    private boolean tulkitse(String s) {
        for(byte i=0;i<muuntaja.length;i++) {
            if(s.startsWith(muuntaja[i].substring(0,1).toLowerCase())) {
                pelaajanLiike=i;
                return true;
            }
        }
        return false;
    }
 
}
