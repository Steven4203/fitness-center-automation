import java.util.ArrayList;

public class Kursiyer {
    private int kursiyerId;
    private String kursiyerAdSoyad;
    private int kursiyerYas;
    public ArrayList<Kurs> alinanKurslar;

    public Kursiyer(int kursiyerId, String kursiyerAdSoyad, int kursiyerYas) {
        this.kursiyerId = kursiyerId;
        this.kursiyerAdSoyad = kursiyerAdSoyad;
        this.kursiyerYas = kursiyerYas;
        alinanKurslar = new ArrayList<Kurs>();
    }


    public int getKursiyerId() {
        return kursiyerId;
    }

    public String getKursiyerAdSoyad() {
        return kursiyerAdSoyad;
    }

    public int getKursiyerYas() {
        return kursiyerYas;
    }

    public ArrayList<Kurs> getAlinanKurslar() {
        return alinanKurslar;
    }

    public void setAlinanKurslar(ArrayList<Kurs> alinanKurslar) {
        this.alinanKurslar = alinanKurslar;
    }

    public void setKursiyerId(int kursiyerId) {
        this.kursiyerId = kursiyerId;
    }

    public void setKursiyerAdSoyad(String kursiyerAdSoyad) {
        this.kursiyerAdSoyad = kursiyerAdSoyad;
    }

    public void setKursiyerYas(int kursiyerYas) {
        this.kursiyerYas = kursiyerYas;
    }


}
