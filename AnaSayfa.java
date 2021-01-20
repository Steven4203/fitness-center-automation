import java.io.*;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class AnaSayfa {
    public static void main(String[] args) throws IOException {
        ArrayList<Kursiyer> krsyr = new ArrayList<Kursiyer>(); // Kursiyer
        ArrayList<Kurs> krs = new ArrayList<Kurs>(); // Kurs
        Scanner oku = new Scanner(System.in);
        krsyr = dosyadanKursiyerCekme(krsyr);
        krs = dosyadanKursCekme(krs);
        int secim;
        do {
            System.out.println("1-Kurs Ekle  2-Kurs Listele  3-Kursiyer Ekle  4-Kursiyer Arama(Ad Soyada göre)  5-Kursiyer Sil(IDye göre)  6-Kursiyer Listele  7-Kursiyer Ayrýntýlý Listele  8-Tutar Hesapla  9-Çýkýþ");
            System.out.print("Yapmak istediginiz islemi giriniz:");
            secim = oku.nextInt();
            switch (secim){
                case 1:
                    krs = kursEkle(krs,oku);
                    break;
                case 2:
                    kursListele(krs);
                    break;
                case 3:
                    krsyr = kursiyerEkle(krsyr,krs,oku);
                    break;
                case 4:
                    kursiyerAra(krsyr,oku);
                    break;
                case 5:
                    kursiyerSil(krsyr,oku);
                    break;
                case 6:
                    kursiyerListele(krsyr);
                    break;
                case 7:
                    kursiyerAyrintiliListele(krsyr);
                    break;
                case 8:
                    tutarHesapla(krsyr,oku);
                    break;
                case 9:
                    System.out.println("Programdan çýkýlýyor...");
                    break;
                default:
                    System.out.println("Hatali bir secim yaptiniz. Programdan cikiliyor.");
                    break;
            }
        }while (secim < 9);
        bitir(krsyr,krs);
        System.out.println("Dosya basarili bir sekilde guncellendi.");
    }

    static ArrayList<Kursiyer> dosyadanKursiyerCekme(ArrayList<Kursiyer> krsyr) throws IOException {
        File kursiyerler = new File("kursiyer.txt");
        if(!kursiyerler.exists()){
            kursiyerler.createNewFile();
        }
        BufferedReader read = new BufferedReader(new FileReader(kursiyerler));
        int kursiyercount = 0;

        String str;

        while ((str = read.readLine()) != null) { // Kursiyer ekleme
            if (str.charAt(0) == '#') {
                String yeni = str.substring(1); // ilk karakteri silme
                String[] satir = yeni.split("-"); // okunan satiri bolme
                Kursiyer kursiyerBilgileri = new Kursiyer(Integer.parseInt(satir[0]), satir[1], Integer.parseInt(satir[2])); // Kursiyer tipinde bir veri olusturma
                krsyr.add(kursiyerBilgileri); // Kursiyer ekleme
                kursiyercount++; // ihtiyac halinde kursiyer sayisi tutma
                Arrays.fill(satir, null); // satir arrayini temizleme
            } else if (str.charAt(0) == '*') {
                String yeniyildiz = str.substring(1);
                String[] sat = yeniyildiz.split("-");
                Kurs kurs = new Kurs(Integer.parseInt(sat[0]), sat[1]);
                krsyr.get(kursiyercount - 1).alinanKurslar.add(kurs);
                Arrays.fill(sat, null);
            }

        }
        read.close();
        return krsyr;
    }

    static ArrayList<Kurs> dosyadanKursCekme(ArrayList<Kurs> krs) throws IOException {
        File kurslar = new File("kurs.txt");
        if(!kurslar.exists()){
            kurslar.createNewFile();
        }
        BufferedReader readKurs = new BufferedReader(new FileReader(kurslar));
        String strKurs;


        while ((strKurs = readKurs.readLine()) != null) { // Kurs ekleme
            String[] sat = strKurs.split("-"); // okunan satiri bolme
            Kurs kurs = new Kurs(Integer.parseInt(sat[0]), sat[1]); // Kurs tipinde bir veri olusturma
            krs.add(kurs); // Kurs ekleme
        }
        readKurs.close();
        return krs;
    }

    static ArrayList<Kurs> kursEkle(ArrayList<Kurs> krs, Scanner oku) {
        System.out.print("Eklemek istediginiz kursun idsini giriniz:");
        int kursId = oku.nextInt();
        for (int i = 0; i < krs.size(); i++) {
            if (krs.get(i).getKursId() == kursId) {
                System.out.println("Eklemek istediginiz Id daha once eklenmis. Ana menuye donuluyor.");
                return krs;
            }
        }
        System.out.print("Eklemek istediginiz kursun adini giriniz:");
        oku.nextLine();
        String kursAd = oku.nextLine();
        Kurs yeniKurs = new Kurs(kursId, kursAd);
        krs.add(yeniKurs);
        return krs;
    }

    static void kursListele(ArrayList<Kurs> krs) {
        System.out.println("Kurs Id" + " " + "Kurs Adý");
        for (int i = 0; i < krs.size(); i++)
            System.out.println(krs.get(i).getKursId() + " " + krs.get(i).getKursAd());
    }

    static ArrayList<Kursiyer> kursiyerEkle(ArrayList<Kursiyer> krsyr, ArrayList<Kurs> krs, Scanner oku) {
        Kursiyer yeniKursiyer;
        Kurs yeniKurs;
        System.out.print("Eklemek istediginiz kursiyerin idsini giriniz:");
        int kursiyerId = oku.nextInt();
        for (int i = 0; i < krsyr.size(); i++) {
            if (krsyr.get(i).getKursiyerId() == kursiyerId) {
                System.out.println("Eklemek istediginiz Id daha once eklenmis. Ana menuye donuluyor.");
                return krsyr;
            }
        }
        System.out.print("Eklemek istediginiz kursiyerin yasini giriniz:");
        int kursiyerYas = oku.nextInt();
        System.out.print("Eklemek istediginiz kursiyerin adini ve soyadini giriniz:");
        oku.nextLine();
        String kursiyerAd = oku.nextLine();
        int secim, kursId;
        yeniKursiyer = new Kursiyer(kursiyerId, kursiyerAd, kursiyerYas);
        while (true) {
            System.out.println("1- Kurs Ekle   0- Kursiyeri Kaydet");
            System.out.print("Yapmak istediginiz secimi giriniz:");
            secim = oku.nextInt();
            if (secim == 1) {
                int count = 0;
                for (int i = 0; i < krs.size(); i++)
                    System.out.print(krs.get(i).getKursId() + " " + krs.get(i).getKursAd() + "\t");
                System.out.print("\nEklemek istedginiz kursun idsini giriniz:");
                kursId = oku.nextInt();
                for (int i = 0; i < krs.size(); i++) {
                    if (kursId == krs.get(i).getKursId()) {
                        yeniKurs = new Kurs(krs.get(i).getKursId(), krs.get(i).getKursAd());
                        yeniKursiyer.alinanKurslar.add(yeniKurs);
                        count++;
                    }
                }
                if (count == 0)
                    System.out.println("Aradiginiz Id de kurs bulunamadi.");
            } else if (secim == 0) {
                System.out.println("Kursiyer kaydediliyor.");
                break;
            } else {
                System.out.println("Hatali secim yaptiniz. Kursiyer kaydediliyor");
                break;
            }
        }
        krsyr.add(yeniKursiyer);
        return krsyr;
    }

    static void kursiyerAra(ArrayList<Kursiyer> krsyr, Scanner oku) {
        System.out.print("Aramak istediginiz kursiyerin adini giriniz:");
        oku.nextLine();
        String arananAdSoyad = oku.nextLine();
        System.out.println(arananAdSoyad);
        int count = 0;
        for (int i = 0; i < krsyr.size(); i++) {
            if (krsyr.get(i).getKursiyerAdSoyad().equals(arananAdSoyad)) {
                System.out.println(krsyr.get(i).getKursiyerId() + " " + krsyr.get(i).getKursiyerAdSoyad() + " " + krsyr.get(i).getKursiyerYas());
                for (int j = 0; j < krsyr.get(i).alinanKurslar.size(); j++) {
                    System.out.println("\t" + krsyr.get(i).getAlinanKurslar().get(j).getKursId() + " " + krsyr.get(i).getAlinanKurslar().get(j).getKursAd());
                }
                count++;
            }
        }
        if (count == 0)
            System.out.println("Aradiginiz kullanici bulunamadi.");

    }

    static ArrayList<Kursiyer> kursiyerSil(ArrayList<Kursiyer> krsyr, Scanner oku) {
        System.out.print("Silmek istediginiz kursiyerin idsini giriniz:");
        int arananId = oku.nextInt(), count = 0;
        for (int i = 0; i < krsyr.size(); i++) {
            if (krsyr.get(i).getKursiyerId() == arananId) {
                krsyr.remove(i);
                System.out.println("Kullanici silindi.");
                count++;
            }
        }
        if (count == 0)
            System.out.println("Aradiginiz kullanici bulunamadi.");
        return krsyr;
    }

    static void kursiyerListele(ArrayList<Kursiyer> krsyr) {
        System.out.println("Tüm Kursiyerler");
        for (int i = 0; i < krsyr.size(); i++) {
            System.out.println(krsyr.get(i).getKursiyerId() + " " + krsyr.get(i).getKursiyerAdSoyad() + " " + krsyr.get(i).getKursiyerYas());
        }
    }

    static void kursiyerAyrintiliListele(ArrayList<Kursiyer> krsyr) {
        System.out.println("Tüm Kursiyerler ve Aldýklarý Kurslar");
        for (int i = 0; i < krsyr.size(); i++) {
            System.out.println(krsyr.get(i).getKursiyerId() + " " + krsyr.get(i).getKursiyerAdSoyad() + " " + krsyr.get(i).getKursiyerYas());
            for (int j = 0; j < krsyr.get(i).alinanKurslar.size(); j++) {
                System.out.println("\t" + krsyr.get(i).getAlinanKurslar().get(j).getKursId() + " " + krsyr.get(i).getAlinanKurslar().get(j).getKursAd());
            }
        }
    }

    static void tutarHesapla(ArrayList<Kursiyer> krsyr, Scanner oku) {
        int toplamUcret = 0, count = 0;
        for (int i = 0; i < krsyr.size(); i++)
            System.out.print(krsyr.get(i).getKursiyerId() + " " + krsyr.get(i).getKursiyerAdSoyad() + "\t");

        System.out.print("\nOdemesini hesaplamak istediginiz kullanicinin idsini giriniz:");
        int kursiyerId = oku.nextInt();
        for (int i = 0; i < krsyr.size(); i++) {
            if (krsyr.get(i).getKursiyerId() == kursiyerId) {
                int kursSayisi = krsyr.get(i).alinanKurslar.size();
                if (kursSayisi == 2) {
                    toplamUcret = (100 + 85) * 4;
                    System.out.println("Kursiyerin faydalandigi indirim turu: Kampanya-1 Odemesi gereken tutar:" + toplamUcret);
                } else if (kursSayisi == 3) {
                    toplamUcret = (275) * 4;
                    System.out.println("Kursiyerin faydalandigi indirim turu: Kampanya-2 Odemesi gereken tutar:" + toplamUcret);
                } else if (kursSayisi > 3) {
                    toplamUcret = (kursSayisi * 90) * 4;
                    System.out.println("Kursiyerin faydalandigi indirim turu: Kampanya-3 Odemesi gereken tutar:" + toplamUcret);
                } else if (kursSayisi == 1) {
                    toplamUcret = 400;
                    System.out.println("Kursiyer herhangi bir kampanyadan faydalanamamaktadir. Odemesi gereken tutar:" + toplamUcret);
                }
                count++;
            }
        }
        if (count == 0)
            System.out.println("Aradiginiz ID de kullanici bulunamadi.");
    }

    static void bitir(ArrayList<Kursiyer> krsyr, ArrayList<Kurs> krs) throws IOException {
        File f = new File("kursiyer.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        FileWriter yaz = new FileWriter("kursiyer.txt");
        for (int i = 0; i < krsyr.size(); i++) {
            yaz.write("#" + krsyr.get(i).getKursiyerId() + "-" + krsyr.get(i).getKursiyerAdSoyad() + "-"+ krsyr.get(i).getKursiyerYas() + "\n");
            for (int j = 0; j < krsyr.get(i).alinanKurslar.size(); j++) {
                yaz.write("*" + krsyr.get(i).alinanKurslar.get(j).getKursId() + "-" + krsyr.get(i).alinanKurslar.get(j).getKursAd()+"\n");
            }
        }
        yaz.close();

        File fn = new File("kurs.txt");
        if (!f.exists()) {
            fn.createNewFile();
        }
        FileWriter yazn = new FileWriter("kurs.txt");
        for (int i = 0; i < krs.size(); i++) {
            yazn.write(krs.get(i).getKursId()+"-"+krs.get(i).getKursAd()+"\n");
        }

        yazn.close();
        System.out.println("Dosya yazma islemi basarili. Program kapatiliyor.");
    }
}
