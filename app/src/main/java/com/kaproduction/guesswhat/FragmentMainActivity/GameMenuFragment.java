package com.kaproduction.guesswhat.FragmentMainActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaproduction.guesswhat.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by reis on 30/11/16.
 */

public class GameMenuFragment extends Fragment implements View.OnClickListener {
    String mGreeting = "Hello...";
    View v;
    boolean mShowSignIn = true;
    ImageView imageViewLogin,imageViewLogout,imageViewUserIcon;
    TextView textViewUsername;


    public interface Listener {
        public void onStartSingleGameRequested();
        public void onStartMultiPlayGameRequest();
        public void onShowAchievementsRequested();
        public void onShowLeaderboardsRequested();
        public void onSignInButtonClicked();
        public void onSignOutButtonClicked();
    }

    Listener mListener = null;

    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }

    public void setUserName(String name){
        if (textViewUsername ==null)  return;
        textViewUsername.setText("Welcome :" + name);
    }

    public void setIconUser(Uri uri){
        if (imageViewUserIcon ==null)  return;
        imageViewUserIcon.setImageURI(uri);
        updateScreen(v);
    }
    public void setGreeting(String greeting) {
        mGreeting = greeting;
        updateScreen(v);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_gamemenu, container, false);
        updateScreen(v);
        return v;
    }

    private void updateScreen(View v) {
        if (getActivity() == null) return;
        imageViewLogin = (ImageView) v.findViewById(R.id.imageViewLogin);
        imageViewLogin.setOnClickListener(this);
        imageViewLogout = (ImageView) v.findViewById(R.id.imageViewLogout);
        imageViewLogout.setOnClickListener(this);
        imageViewUserIcon = (ImageView) v.findViewById(R.id.imageViewUserIcon);
        textViewUsername = (TextView) v.findViewById(R.id.textViewUsername);
        if (textViewUsername != null) textViewUsername.setText(mGreeting);
        imageViewLogin.setVisibility(mShowSignIn ?
                View.VISIBLE : View.GONE);
        imageViewLogout.setVisibility(mShowSignIn ?
                View.GONE : View.VISIBLE);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setShowSignInButton(boolean showSignIn) {
        mShowSignIn = showSignIn;
        updateScreen(v);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.imageViewLogin:
                mListener.onSignInButtonClicked();
            break;

            case R.id.imageViewLogout:
                mListener.onSignOutButtonClicked();
            break;


        }

    }

}
