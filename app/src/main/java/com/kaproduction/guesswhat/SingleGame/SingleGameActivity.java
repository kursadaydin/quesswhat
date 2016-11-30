package com.kaproduction.guesswhat.SingleGame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kaproduction.guesswhat.R;

public class SingleGameActivity extends AppCompatActivity {

    SingleGameFragment singleGameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_game);

        singleGameFragment = new SingleGameFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder_singlegame, singleGameFragment).commit();
    }
}
