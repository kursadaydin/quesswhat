package com.kaproduction.guesswhat.SingleGame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaproduction.guesswhat.R;

/**
 * Created by reis on 30/11/16.
 */

public class SingleGameFragment extends Fragment {


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


        return inflater.inflate(R.layout.fragment_single_game, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
