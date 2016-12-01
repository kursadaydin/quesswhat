package com.kaproduction.guesswhat.Helper;

import android.content.Context;

import com.kaproduction.guesswhat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by reis on 30/11/16.
 */

public class RandomNumber {
    private Context context;
    private int tahminEdilecekSayi;
    private String ipucu;

    public RandomNumber(Context context) {
        this.context = context;
    }

    public int getTahminEdilecekSayi() {
        return tahminEdilecekSayi;
    }

    public void setTahminEdilecekSayi(int tahminEdilecekSayi) {
        this.tahminEdilecekSayi = tahminEdilecekSayi;
    }



    public String getIpucu(){
        return ipucu;
    }

    public int rastgeleSayiOlustur(){

        Random random = new Random();
        ArrayList<String> dizi = new ArrayList<>();
        //int sayi = random.nextInt(9000)+1000;

        for (int i = 1; i < 10; i++){

            dizi.add(i+"");
        }

        String basamak_1 = dizi.get(random.nextInt(dizi.size()));
        dizi.remove(basamak_1);
        dizi.add("0");
        String basamak_2 = dizi.get(random.nextInt(dizi.size()));
        dizi.remove(basamak_2);
        String basamak_3 = dizi.get(random.nextInt(dizi.size()));
        dizi.remove(basamak_3);
        String basamak_4 = dizi.get(random.nextInt(dizi.size()));

        int result = (Integer.parseInt(basamak_1)*1000)+ (Integer.parseInt(basamak_2)*100)+(Integer.parseInt(basamak_3)*10)+Integer.parseInt(basamak_4);


        return result;
    }


    public String controlDogruMu (int deneme){
        String result = null;

        String arananDeger = String.valueOf(getTahminEdilecekSayi());


        if (arananDeger.equals(String.valueOf(deneme))){

            result = context.getString(R.string.right_choice);

        }else {

            result = context.getString(R.string.wrong_choice);
        }

        return result;
    }

    public String control(String deneme){
        //Tutulan sayi ile deneme yi kontrol ederek sonucu string donduruyoruz

        String result = "";

        ArrayList<String> sayi = new ArrayList<>();
        ArrayList<String> sayiDeneme = new ArrayList<>();


        char[] denemeArray = (deneme).toCharArray();
        for (int i =0; i<denemeArray.length;i++){
            sayiDeneme.add(String.valueOf(denemeArray[i]));
        }

        char[] sayiListArray = String.valueOf(getTahminEdilecekSayi()).toCharArray();
        for (int i =0; i<sayiListArray.length;i++){
            sayi.add(String.valueOf(sayiListArray[i]));
        }

        int countArtilar = 0;
        for (int i =0 ; i< denemeArray.length; i++){
            if (sayi.get(i).contains(sayiDeneme.get(i))){
                result += "+ ";
                countArtilar ++;
            }

        }

        //Ilk once 2 arraylisti tek bir listede topluyoruz
        List<String> allItemsArrayList = new ArrayList<>();
        allItemsArrayList.addAll(sayi);
        allItemsArrayList.addAll(sayiDeneme);
        //Daha sonra bu toplu arraylisti hashsete yukluyoruz
        //Hashsette degeri ayni olanlardan bir tanesi tutuluyor..
        Set<String> allItemsHashSet = new HashSet<>();
        allItemsHashSet.addAll(allItemsArrayList);

        //fark sayisi bize kac tane mukerrer item oldugunu soyluyor.
        // ve o kadar - isareti basiyoruz
        //Ama ayni siradaki benzer rakamlari tutan countArtilar i dusuyoruz ki sadece farkli basamaktaki benzer rakamlari bulalim
        int countEksiler = allItemsArrayList.size() - allItemsHashSet.size() - countArtilar;

        if(countEksiler>0) {
            for (int i = 0; i < countEksiler; i++) {
                result += "- ";

            }

        }
        return result;

    }

    public Map<String,Integer> getNumberofPlusMinus(String deneme){
        //Yapilan tahmin sonucunda kac tane arti veya eksi geldigini arrayliste atiyoruz....
        ArrayList<String> tempArrayList = new ArrayList<>();
        String result = control(deneme);
        int countPlus = 0;
        int countMinus = 0;

        char[] denemeArray = result.toCharArray();
        for (int i =0; i<denemeArray.length;i++){
            tempArrayList.add(String.valueOf(denemeArray[i]));
        }

        for (int i =0; i<tempArrayList.size();i++){
            if(tempArrayList.get(i).equals("+")){
                countPlus++;
            }else if(tempArrayList.get(i).equals("-")){
                countMinus++;
            }

        }

        Map<String,Integer> tempHashMap = new HashMap();
        tempHashMap.put("Plus",countPlus);
        tempHashMap.put("Minus",countMinus);

        return tempHashMap;
    }



}
