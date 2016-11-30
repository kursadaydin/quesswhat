package com.kaproduction.guesswhat.FragmentMainActivity;

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

public class WinFragment extends Fragment {

    public interface Listener {
        public void onWinScreenDismissed();
        public void onWinScreenSignInClicked();
    }

    Listener mListener = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_win, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
