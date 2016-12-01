package com.kaproduction.guesswhat.Helper;

import android.content.Context;

import java.math.BigDecimal;

/**
 * Created by reis on 30/11/16.
 */

public class Score {
    private int kullanilanHak;
    private Context context;

    public Score(Context context) {
        this.context = context;
    }


    public int getKullanilanHak() {

        return kullanilanHak;
    }

    public void setKullanilanHak(int kullanilanHak) {
        this.kullanilanHak = kullanilanHak;
    }


    public float puanHesapla(){

        int deneme = getKullanilanHak()+2;
        int oncekiDeneme = deneme-1;

        double katsayi = 2.0;
        double fark = deneme -oncekiDeneme;
        double azalisOrani = fark/oncekiDeneme;

        float puan = (float) (1000*(azalisOrani));
        BigDecimal bd = new BigDecimal(puan);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return (float) bd.doubleValue();

    }

}
