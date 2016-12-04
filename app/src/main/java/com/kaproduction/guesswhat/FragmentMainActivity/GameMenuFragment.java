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
import android.widget.Button;
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
    String imagePath = null;
    View v;
    boolean mShowSignIn = true;
    ImageView imageViewUserIcon;
    ImageButton imageButtonLogin,imageButtonLogout,imageButtonAchievement,imageButtonLeadership;
    Button buttonSinglegame, buttonMultiplaygame;
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
        imagePath = path;
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
        imageButtonLogin = (ImageButton) v.findViewById(R.id.imageButtonLogin);
        imageButtonLogin.setOnClickListener(this);
        imageButtonLogout = (ImageButton) v.findViewById(R.id.imageButtonLogout);
        imageButtonLogout.setOnClickListener(this);
        imageViewUserIcon = (ImageView) v.findViewById(R.id.imageViewUserIcon);
        imageButtonAchievement = (ImageButton) v.findViewById(R.id.imageButtonAchievement);
        imageButtonAchievement.setOnClickListener(this);
        imageButtonLeadership = (ImageButton) v.findViewById(R.id.imageButtonLeadership);
        imageButtonLeadership.setOnClickListener(this);

        buttonSinglegame = (Button) v.findViewById(R.id.buttonSinglegame);
        buttonSinglegame.setOnClickListener(this);
        buttonMultiplaygame = (Button) v.findViewById(R.id.buttonMultiplaygame);
        buttonMultiplaygame.setOnClickListener(this);

        textViewUsername = (TextView) v.findViewById(R.id.textViewUsername);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateScreen(v);
    }

    private void updateScreen(View v) {
        if (getActivity() == null) return;

        if (textViewUsername != null) textViewUsername.setText(mGreeting);
        if (imageViewUserIcon !=null) {
            Picasso.with(getActivity().getApplicationContext()).load(imagePath)
                    .transform(new CircleTransform())
                    .into(imageViewUserIcon);
        }
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

            case R.id.imageButtonAchievement:
                mListener.onShowAchievementsRequested();
                break;
            case R.id.imageButtonLeadership:
                mListener.onShowLeaderboardsRequested();
                break;

            case R.id.buttonSinglegame:
                mListener.onStartSingleGameRequested();
                break;

            case R.id.buttonMultiplaygame:
                mListener.onStartMultiPlayGameRequest();
                break;

        }

    }

}
