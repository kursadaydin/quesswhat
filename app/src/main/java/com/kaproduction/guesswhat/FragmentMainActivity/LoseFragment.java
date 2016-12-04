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

public class LoseFragment extends Fragment implements View.OnClickListener {

    View v;
    String mExplanation = "";
    float mScore = 0;
    boolean mShowSignIn = false;

    Button buttonLoseFragment,buttonLoseFragmentLogin;
    TextView textViewLoseFragmentText;



    public interface Listener {
        public void onLoseScreenDismissed();
        public void onLoseScreenSignInClicked();
    }

    Listener mListener = null;

    public void setExplanation(String s) {
        mExplanation = s;

    }

    public void setShowSignInButton(boolean showSignIn) {
        mShowSignIn = showSignIn;
        updateUI();
    }

    public void setListener(LoseFragment.Listener l) {
        mListener = l;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_lose, container, false);
        buttonLoseFragment = (Button) v.findViewById(R.id.buttonLoseFragment);
        buttonLoseFragmentLogin = (Button) v.findViewById(R.id.buttonLoseFragmentLogin);
        textViewLoseFragmentText = (TextView) v.findViewById(R.id.textViewLoseFragmentText);
        buttonLoseFragment.setOnClickListener(this);
        buttonLoseFragmentLogin.setOnClickListener(this);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI();
    }

    private void updateUI() {
        if (getActivity() == null) return;
        if (textViewLoseFragmentText != null)  textViewLoseFragmentText.setText(mExplanation);
        buttonLoseFragmentLogin.setVisibility(
                mShowSignIn ? View.VISIBLE : View.GONE);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.buttonLoseFragment:
                mListener.onLoseScreenDismissed();
                break;
            case R.id.buttonLoseFragmentLogin:
                mListener.onLoseScreenSignInClicked();
                break;
        }

    }



}
