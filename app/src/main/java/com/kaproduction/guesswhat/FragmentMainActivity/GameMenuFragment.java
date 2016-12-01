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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaproduction.guesswhat.Helper.CircleTransform;
import com.kaproduction.guesswhat.R;
import com.squareup.picasso.Picasso;

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
    ImageView imageViewUserIcon;
    ImageButton imageButtonLogin,imageButtonLogout;
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

    public void setIconUser(String path){
        if (imageViewUserIcon ==null)  return;
        Picasso.with(getActivity().getApplicationContext()).load(path)
                .transform(new CircleTransform())
                .into(imageViewUserIcon);
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
        imageButtonLogin = (ImageButton) v.findViewById(R.id.imageButtonLogin);
        imageButtonLogin.setOnClickListener(this);
        imageButtonLogout = (ImageButton) v.findViewById(R.id.imageButtonLogout);
        imageButtonLogout.setOnClickListener(this);
        imageViewUserIcon = (ImageView) v.findViewById(R.id.imageViewUserIcon);
        textViewUsername = (TextView) v.findViewById(R.id.textViewUsername);
        if (textViewUsername != null) textViewUsername.setText(mGreeting);
        imageButtonLogin.setVisibility(mShowSignIn ?
                View.VISIBLE : View.GONE);
        imageButtonLogout.setVisibility(mShowSignIn ?
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
            case R.id.imageButtonLogin:
                mListener.onSignInButtonClicked();
            break;

            case R.id.imageButtonLogout:
                mListener.onSignOutButtonClicked();
            break;


        }

    }

}
