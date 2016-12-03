package com.kaproduction.guesswhat.FragmentMainActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kaproduction.guesswhat.R;

/**
 * Created by reis on 30/11/16.
 */

public class WinFragment extends Fragment implements View.OnClickListener {

    View v;
    String mExplanation = "";
    float mScore = 0;
    boolean mShowSignIn = false;

    Button buttonWinFragment,buttonWinFragmentLogin,buttonWinFragmentLogout;
    TextView textViewWinFragmentScore,textViewWinFragmentText;


    public interface Listener {
        public void onWinScreenDismissed();
        public void onWinScreenSignInClicked();
    }

    public void setFinalScore(float i) {
        mScore = i;
    }

    public void setExplanation(String s) {
        mExplanation = s;
    }

    public void setListener(Listener l) {
        mListener = l;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI();
    }
    Listener mListener = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       v = inflater.inflate(R.layout.fragment_win, container, false);
        updateUI();
        return v;
    }

    private void updateUI() {
        if (getActivity() == null) return;
        buttonWinFragment = (Button) v.findViewById(R.id.buttonWinFragment);
        buttonWinFragmentLogin = (Button) v.findViewById(R.id.buttonWinFragmentLogin);
        textViewWinFragmentScore = (TextView) v.findViewById(R.id.textViewWinFragmentScore);
        textViewWinFragmentText = (TextView) v.findViewById(R.id.textViewWinFragmentText);

        buttonWinFragment.setOnClickListener(this);
        buttonWinFragmentLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.buttonWinFragment:
                mListener.onWinScreenDismissed();
                break;
            case R.id.buttonWinFragmentLogin:
                mListener.onWinScreenSignInClicked();
                break;
        }

    }


    public void setShowSignInButton(boolean showSignIn) {
        mShowSignIn = showSignIn;
        updateUI();
    }

}
