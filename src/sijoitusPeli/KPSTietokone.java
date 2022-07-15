package sijoitusPeli;

/**
 * tietokoneen logiikka tullakseen voittamattomaksi kivi-paperi-sakset pelissa!
 * MUHAHAHHAUAHUAHU
 * @author Joona1
 * @version 11.4.2020
 *
 */
public class KPSTietokone {
    private int maxKierroksia = 10;
    private int[] kierrokset = new int[maxKierroksia];
    private int kierrosNro = 0;
    private byte[] parasLiikeTM = new byte[9];
    private int edellinenKierros;

    /**
     * TODO tee tasta viela parempi!
     * tallentaa mita pelaaja tekee kunkin kierroksen jalkeen ja asettaa
     * tulevaisuudessa voittavan siirron jos pelaaja toistaa saman kuvion uudestaan.
     * esim jos voitat kivella ja pelaat sen jalkeen paperin tulee tietokone
     * seuraavalla kerralla pelaamaan sakset havittyaan kivelle
     * 
     * @param kierros edellinen kierros, joka tallennetaan
     * @return suunnattoman komleksisella algoritmilla paatelty optimaalinen liike.
     */
    public byte annaLiike(int kierros) {
        kierrokset[kierrosNro%maxKierroksia]=kierros;
        parasLiikeTM[edellinenKierros]=(byte) ((kierros/3+1)%3);
        edellinenKierros = kierros;
        kierrosNro++;
        return parasLiikeTM[kierros];
    }


}
