package com.kaproduction.guesswhat.SingleGame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.kaproduction.guesswhat.R;

public class SingleGameActivity extends AppCompatActivity {

    SingleGameFragment singleGameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_game);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        singleGameFragment = new SingleGameFragment();


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder_singlegame, singleGameFragment).commit();
    }
}
