package com.kaproduction.guesswhat.SingleGame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.kaproduction.guesswhat.Helper.RandomNumber;
import com.kaproduction.guesswhat.Helper.Score;
import com.kaproduction.guesswhat.MainActivity.MainActivity;
import com.kaproduction.guesswhat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by reis on 30/11/16.
 */

public class SingleGameFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    View v;

    TextView spTextViewDeneme1Sonuc,spTextViewDeneme2Sonuc , spTextViewDeneme3Sonuc, spTextViewDeneme4Sonuc, spTextViewDeneme5Sonuc;
    TextView spTextViewDeneme6Sonuc,spTextViewDeneme7Sonuc,spTextViewDeneme8Sonuc,spTextViewDeneme9Sonuc,spTextViewDeneme10Sonuc;
    TextView spTextViewDeneme1,spTextViewDeneme2,spTextViewDeneme3,spTextViewDeneme4,
            spTextViewDeneme5,spTextViewDeneme6,spTextViewDeneme7,spTextViewDeneme8,
            spTextViewDeneme9,spTextViewDeneme10;
    TextView spHeader;

    EditText spEditText1,spEditText2,spEditText3,spEditText4,spEditText5;
    EditText spEditText6,spEditText7,spEditText8,spEditText9,spEditText10;

    Button spButtonCheckMyResult;

    InputMethodManager inputMethodManager;

    RandomNumber randomNumber;
    Score mScore;
    int rastgeleSayi;
    int count = 0;


    final int[] MY_EDIT_TEXT_INT = new int[] {
            R.id.spEditText1, R.id.spEditText2,
            R.id.spEditText3, R.id.spEditText4,
            R.id.spEditText5, R.id.spEditText6,
            R.id.spEditText7, R.id.spEditText8,
            R.id.spEditText9, R.id.spEditText10,
    };

    final EditText[] MY_EDIT_TEXT = new EditText[] {
            spEditText1,spEditText2,
            spEditText3,spEditText4,
            spEditText5, spEditText6,
            spEditText7,spEditText8,
            spEditText9,spEditText10
    };

    final int[] MY_TEXT_VIEW_INT = new int[] {
            R.id.spTextViewDeneme1Sonuc, R.id.spTextViewDeneme2Sonuc,
            R.id.spTextViewDeneme3Sonuc, R.id.spTextViewDeneme4Sonuc,
            R.id.spTextViewDeneme5Sonuc, R.id.spTextViewDeneme6Sonuc,
            R.id.spTextViewDeneme7Sonuc, R.id.spTextViewDeneme8Sonuc,
            R.id.spTextViewDeneme9Sonuc, R.id.spTextViewDeneme10Sonuc,
    };

    final TextView[] MY_TEXT_VIEW = new TextView[] {
            spTextViewDeneme1Sonuc, spTextViewDeneme2Sonuc,
            spTextViewDeneme3Sonuc, spTextViewDeneme4Sonuc,
            spTextViewDeneme5Sonuc, spTextViewDeneme6Sonuc,
            spTextViewDeneme7Sonuc,spTextViewDeneme8Sonuc,
            spTextViewDeneme9Sonuc,spTextViewDeneme10Sonuc
    };

    final TextView[] MY_TEXT_VIEW_DENEME = new TextView[] {
            spTextViewDeneme1,spTextViewDeneme2,
            spTextViewDeneme3,spTextViewDeneme4,
            spTextViewDeneme5,spTextViewDeneme6,
            spTextViewDeneme7,spTextViewDeneme8,
            spTextViewDeneme9,spTextViewDeneme10
    };

    final int[] MY_TEXT_VIEW_DENEME_INT = new int[] {
            R.id.spTextViewDeneme1, R.id.spTextViewDeneme2,
            R.id.spTextViewDeneme3, R.id.spTextViewDeneme4,
            R.id.spTextViewDeneme5, R.id.spTextViewDeneme6,
            R.id.spTextViewDeneme7, R.id.spTextViewDeneme8,
            R.id.spTextViewDeneme9, R.id.spTextViewDeneme10,
    };

    public ToggleButton toggleButton0,toggleButton1,
            toggleButton2,toggleButton3,toggleButton4,toggleButton5,
            toggleButton6,toggleButton7,toggleButton8,toggleButton9;

    public boolean booleanToggleButton0,booleanToggleButton1,
            booleanToggleButton2,booleanToggleButton3,booleanToggleButton4,booleanToggleButton5,
            booleanToggleButton6,booleanToggleButton7,booleanToggleButton8,booleanToggleButton9 = false;

    Bundle myBundle;
    FloatingActionButton fab;


    public interface Listener {

        public void onSinglePlayScore(float score, int count);

    }
    Listener mListener = null;

    public void setListener(Listener mListener){
        this.mListener = mListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_single_game,container,false);

        if (savedInstanceState != null) {
            myBundle = savedInstanceState;

        }

        for (int i = 0; i<MY_EDIT_TEXT.length;i++) {
            MY_EDIT_TEXT [i]= (EditText) v.findViewById(MY_EDIT_TEXT_INT[i]);
            MY_EDIT_TEXT[i].setEnabled(false);
        }

        for (int i = 0; i<MY_TEXT_VIEW.length;i++) {
            MY_TEXT_VIEW[i]= (TextView) v.findViewById(MY_TEXT_VIEW_INT[i]);

        }

        for (int i = 0; i<MY_TEXT_VIEW_DENEME.length;i++) {
            MY_TEXT_VIEW_DENEME[i]= (TextView) v.findViewById(MY_TEXT_VIEW_DENEME_INT[i]);
            MY_TEXT_VIEW_DENEME[i].setVisibility(View.GONE);

        }

        spButtonCheckMyResult = (Button) v.findViewById(R.id.singlePlayerButton);
        spButtonCheckMyResult.setOnClickListener(this);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI();
    }

    @Override
    public void onPause() {
        super.onPause();
        updateUI();
       // emptyAll();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.singlePlayerButton:
                closeNumPad(count);
                if (controlInputDigit(count) && controlInput(getEditText(count).getText().toString().trim())) {
                    if (tahminDogruMu()) {
                        ToastMessage("You are the winner : Your try is" +count);
                        mScore.setKullanilanHak(count);
                        mListener.onSinglePlayScore(mScore.puanHesapla(),count);

                    } else {
                        String getNumber = getEditText(count).getText().toString().trim();
                        Map<String, Integer> map = new HashMap<>();
                        map = randomNumber.getNumberofPlusMinus(getNumber);
                        getTextView(count).setText(spannableStringBuilder(map.get("Plus"), map.get("Minus")));
                        count++;

                        if (count < 10) {

                            getEditText(count).setEnabled(true);
                            getPreviousEditText(count).setEnabled(false);
                            setEditTextActive(count);
                            getTextViewDeneme(count).setVisibility(View.VISIBLE);
                        } else {

                            //eger 10 uncu yani son hakta da yanlis giris yaparsak yeni bir edittext olmadigi icin
                            //burada kaliyor....
                            spHeader.setText(rastgeleSayi + "");
                            spHeader.setTextColor(Color.RED);
                            spHeader.setTextSize(30);
                            spButtonCheckMyResult.setEnabled(false);

                            showAlertDialog();

                        }
                    }
                } else {
                    ToastMessage("Hatali Giris Yapiyorsunuz");
                    break;
                }

                break;


            case R.id.fab:

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.layout_helper);

                toggleButton0 = (ToggleButton) dialog.findViewById(R.id.toggleButton0);
                toggleButton0.setOnCheckedChangeListener(this);
                if(myBundle !=null){ if (myBundle.getBoolean("toggleButton0")) toggleButton0.setChecked(true);}
                toggleButton1 = (ToggleButton) dialog.findViewById(R.id.toggleButton1);
                toggleButton1.setOnCheckedChangeListener(this);
                if(myBundle !=null){ if (myBundle.getBoolean("toggleButton1")) toggleButton1.setChecked(true);}
                toggleButton2 = (ToggleButton) dialog.findViewById(R.id.toggleButton2);
                toggleButton2.setOnCheckedChangeListener(this);
                if(myBundle !=null){if (myBundle.getBoolean("toggleButton2")) toggleButton2.setChecked(true);}
                toggleButton3 = (ToggleButton) dialog.findViewById(R.id.toggleButton3);
                toggleButton3.setOnCheckedChangeListener(this);
                if(myBundle !=null){ if (myBundle.getBoolean("toggleButton3")) toggleButton3.setChecked(true);}
                toggleButton4 = (ToggleButton) dialog.findViewById(R.id.toggleButton4);
                toggleButton4.setOnCheckedChangeListener(this);
                if(myBundle !=null){ if (myBundle.getBoolean("toggleButton4")) toggleButton4.setChecked(true);}
                toggleButton5 = (ToggleButton) dialog.findViewById(R.id.toggleButton5);
                toggleButton5.setOnCheckedChangeListener(this);
                if(myBundle !=null){ if (myBundle.getBoolean("toggleButton5")) toggleButton5.setChecked(true);}
                toggleButton6 = (ToggleButton) dialog.findViewById(R.id.toggleButton6);
                toggleButton6.setOnCheckedChangeListener(this);
                if(myBundle !=null){ if (myBundle.getBoolean("toggleButton6")) toggleButton6.setChecked(true);}
                toggleButton7 = (ToggleButton) dialog.findViewById(R.id.toggleButton7);
                toggleButton7.setOnCheckedChangeListener(this);
                if(myBundle !=null){ if (myBundle.getBoolean("toggleButton7")) toggleButton7.setChecked(true);}
                toggleButton8 = (ToggleButton) dialog.findViewById(R.id.toggleButton8);
                toggleButton8.setOnCheckedChangeListener(this);
                if(myBundle !=null){ if (myBundle.getBoolean("toggleButton8")) toggleButton8.setChecked(true);}
                toggleButton9 = (ToggleButton) dialog.findViewById(R.id.toggleButton9);
                toggleButton9.setOnCheckedChangeListener(this);
                if(myBundle !=null){  if (myBundle.getBoolean("toggleButton9")) toggleButton9.setChecked(true);}
                Button btnOk = (Button) dialog.findViewById(R.id.buttonnOK);
                Button btnClear = (Button) dialog.findViewById(R.id.buttonnClear);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clearBundle(myBundle);
                        dialog.dismiss();
                    }
                });

                dialog.show();
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {

        switch (compoundButton.getId()) {
            case R.id.toggleButton0:
                if (ischecked) {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_0_pressed);
                    booleanToggleButton0 = true;
                    myBundle.putBoolean("toggleButton0", true);
                } else {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_0_idle);
                    booleanToggleButton0 = false;
                    myBundle.putBoolean("toggleButton0", false);
                }
                break;
            case R.id.toggleButton1:
                if (ischecked) {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_1_pressed);
                    booleanToggleButton0 = true;
                    myBundle.putBoolean("toggleButton1", booleanToggleButton0);
                } else {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_1_idle);
                    booleanToggleButton1 = false;
                    myBundle.putBoolean("toggleButton1", false);
                }
                break;
            case R.id.toggleButton2:
                if (ischecked) {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_2_pressed);
                    booleanToggleButton0 = true;
                    myBundle.putBoolean("toggleButton2", true);
                } else {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_2_idle);
                    booleanToggleButton2 = false;
                    myBundle.putBoolean("toggleButton2", false);
                }
                break;
            case R.id.toggleButton3:
                if (ischecked) {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_3_pressed);
                    booleanToggleButton0 = true;
                    myBundle.putBoolean("toggleButton3", true);
                } else {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_3_idle);
                    booleanToggleButton3 = false;
                    myBundle.putBoolean("toggleButton3", false);
                }
                break;
            case R.id.toggleButton4:
                if (ischecked) {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_4_pressed);
                    booleanToggleButton0 = true;
                    myBundle.putBoolean("toggleButton4", true);
                } else {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_4_idle);
                    booleanToggleButton4 = false;
                    myBundle.putBoolean("toggleButton4", false);
                }
                break;
            case R.id.toggleButton5:
                if (ischecked) {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_5_pressed);
                    booleanToggleButton0 = true;
                    myBundle.putBoolean("toggleButton5", true);
                } else {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_5_idle);
                    booleanToggleButton5 = false;
                    myBundle.putBoolean("toggleButton5", false);
                }
                break;
            case R.id.toggleButton6:
                if (ischecked) {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_6_pressed);
                    booleanToggleButton0 = true;
                    myBundle.putBoolean("toggleButton6", true);
                } else {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_6_idle);
                    booleanToggleButton6 = false;
                    myBundle.putBoolean("toggleButton6", false);
                }
                break;
            case R.id.toggleButton7:
                if (ischecked) {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_7_pressed);
                    booleanToggleButton0 = true;
                    myBundle.putBoolean("toggleButton7", true);
                } else {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_7_idle);
                    booleanToggleButton7 = false;
                    myBundle.putBoolean("toggleButton7", false);
                }
                break;
            case R.id.toggleButton8:
                if (ischecked) {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_8_pressed);
                    booleanToggleButton0 = true;
                    myBundle.putBoolean("toggleButton8", true);
                } else {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_8_idle);
                    booleanToggleButton8 = false;
                    myBundle.putBoolean("toggleButton8", false);
                }
                break;
            case R.id.toggleButton9:
                if (ischecked) {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_9_pressed);
                    booleanToggleButton0 = true;
                    myBundle.putBoolean("toggleButton9", true);
                } else {
                    compoundButton.setBackgroundResource(R.drawable.ic_action_number_9_idle);
                    booleanToggleButton9 = false;
                    myBundle.putBoolean("toggleButton9", false);
                }
                break;

        }

    }


    private void updateUI() {
        if (getActivity() == null) return;
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        randomNumber = new RandomNumber(getActivity().getApplicationContext());
        mScore = new Score(getActivity().getApplicationContext());
        rastgeleSayi =  randomNumber.rastgeleSayiOlustur();
        randomNumber.setTahminEdilecekSayi(rastgeleSayi);
        spHeader = (TextView) v.findViewById(R.id.spHeader);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(" ");
        builder.setSpan(new ImageSpan(getActivity(), R.drawable.ic_action_questionmark),
                builder.length() - 1, builder.length(), 0);
        builder.append(" ");
        builder.setSpan(new ImageSpan(getActivity(), R.drawable.ic_action_questionmark),
                builder.length() - 1, builder.length(), 0);
        builder.append(" ");
        builder.setSpan(new ImageSpan(getActivity(), R.drawable.ic_action_questionmark),
                builder.length() - 1, builder.length(), 0);
        builder.append(" ");
        builder.setSpan(new ImageSpan(getActivity(), R.drawable.ic_action_questionmark),
                builder.length() - 1, builder.length(), 0);
        builder.append(" ");
        spHeader.setText(builder);
        // singleplayerHeader.setText(rastgeleSayi+"");


        MY_EDIT_TEXT[count].setEnabled(true);
        MY_TEXT_VIEW[count].setEnabled(true);
        MY_TEXT_VIEW_DENEME[count].setVisibility(View.VISIBLE);


        myBundle = new Bundle();


    }


    private void clearBundle(Bundle myInfo){

        myInfo.clear();
    }


    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setCancelable(false)
                .setTitle("The number was "+rastgeleSayi)
                .setMessage(R.string.newGame)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        emptyAll();
                        updateUI();

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_holder,((MainActivity)getActivity()).getGameMenuFragment()).commit();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setGravity(Gravity.BOTTOM|Gravity.CENTER);

        dialog.show();
    }

    public void setEditTextActive(int count){
        getEditText(count).requestFocus();
    }

    public EditText getEditText(int i){
        return MY_EDIT_TEXT[i];
    }

    public EditText getPreviousEditText(int i){

        return MY_EDIT_TEXT[i-1];

    }

    public TextView getTextView(int i){

        return MY_TEXT_VIEW[i];
    }


    public TextView getTextViewDeneme(int i){

        return MY_TEXT_VIEW_DENEME[i];
    }


    public boolean tahminDogruMu(){
        String deneme = getEditText(count).getText().toString();
        if(String.valueOf(rastgeleSayi).equals( deneme )) {

            return true;
        }
        return false;
    }

    public void closeNumPad(int count){
        if(inputMethodManager.isActive()){
            inputMethodManager.hideSoftInputFromWindow(getEditText(count).getWindowToken(), 0);
        }
    }


    public boolean controlInputDigit(int count){
        if(getEditText(count).getText().length()<4)
            return false;
        else
            return true;
    }

    public boolean controlInput(String deneme){
        ArrayList<String> denemeArrayList = new ArrayList<>();
        char[] denemeList = deneme.toCharArray();
        for (int i =0; i<denemeList.length;i++){
            denemeArrayList.add(String.valueOf(denemeList[i]));
        }
        Set<String> hashSet = new HashSet<>();
        hashSet.addAll(denemeArrayList);

        //hashset ayni degerlere sahip olan itemlari siler
        if(denemeArrayList.get(0).equals("0") || hashSet.size()<denemeArrayList.size()){

            return false;
        }else

            return true;
    }


    public void ToastMessage(String message){

        Toast.makeText(getActivity().getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    public void emptyAll(){
        count = 0;
        spButtonCheckMyResult.setEnabled(true);
        for (int i =0; i<MY_EDIT_TEXT.length;i++){
            MY_EDIT_TEXT[i].getText().clear();
            MY_EDIT_TEXT[i].setEnabled(false);
        }

        for (int i =0; i<MY_TEXT_VIEW.length;i++){
            MY_TEXT_VIEW[i].setText("");
        }

        for (int i =1; i<MY_TEXT_VIEW_DENEME.length;i++){
            MY_TEXT_VIEW_DENEME[i].setVisibility(View.GONE);
        }


        if (myBundle !=null) clearBundle(myBundle);

    }


    public SpannableStringBuilder spannableStringBuilder(int countPlus, int countMinus){

        SpannableStringBuilder builder = new SpannableStringBuilder();

        if (countMinus ==0 && countPlus ==0){
            builder.append(" ");
            builder.setSpan(new ImageSpan(getActivity(), R.drawable.ic_action_cross),
                    builder.length() - 1, builder.length(), 0);
        }else{

            for (int i =0; i<countPlus; i++) {
                builder.append(" ");
                builder.setSpan(new ImageSpan(getActivity(), R.drawable.ic_action_add),
                        builder.length() - 1, builder.length(), 0);

            }
            for (int i =0; i<countMinus; i++) {
                builder.append(" ");
                builder.setSpan(new ImageSpan(getActivity(), R.drawable.ic_action_minus),
                        builder.length() - 1, builder.length(), 0);

            }
        }
        return builder;

    }



}
