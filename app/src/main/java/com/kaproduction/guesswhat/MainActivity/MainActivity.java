package com.kaproduction.guesswhat.MainActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.view.View;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.google.example.games.basegameutils.BaseGameUtils;
import com.kaproduction.guesswhat.FragmentMainActivity.GameMenuFragment;
import com.kaproduction.guesswhat.FragmentMainActivity.LoseFragment;
import com.kaproduction.guesswhat.FragmentMainActivity.WinFragment;
import com.kaproduction.guesswhat.MultiPlayGame.MultiPlayGameActivity;
import com.kaproduction.guesswhat.R;
import com.kaproduction.guesswhat.SingleGame.SingleGameActivity;

import static android.R.string.no;
import static android.R.string.yes;

public class MainActivity extends AppCompatActivity implements GameMenuFragment.Listener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    // Fragments
    GameMenuFragment gameMenuFragment;
    LoseFragment loseFragment;
    WinFragment winFragment;

    // Client used to interact with Google APIs
    private GoogleApiClient mGoogleApiClient;

    // Are we currently resolving a connection failure?
    private boolean mResolvingConnectionFailure = false;

    // Has the user clicked the sign-in button?
    private boolean mSignInClicked = false;

    // Automatically start the sign-in flow when the Activity starts
    private boolean mAutoStartSignInFlow = true;

    // request codes we use when invoking an external activity
    private static final int RC_RESOLVE = 5000;
    private static final int RC_UNUSED = 5001;
    private static final int RC_SIGN_IN = 9001;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
               // .addApi(AppIndex.API)
                .build();



        gameMenuFragment = new GameMenuFragment();
        loseFragment = new LoseFragment();
        winFragment = new WinFragment();

        gameMenuFragment.setListener(this);




        // add initial fragment (welcome fragment)
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder,
                gameMenuFragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
     //   AppIndex.AppIndexApi.start(mGoogleApiClient, getIndexApiAction());
    }

    @Override
    protected void onStop() {
        super.onStop();
     //   AppIndex.AppIndexApi.end(mGoogleApiClient, getIndexApiAction());
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {

                BaseGameUtils.showActivityResultError(this, requestCode, resultCode, R.string.signin_other_error);
            }
        }
    }

    @Override
    public void onBackPressed() {
        alertDialogCloseApp();
    }

    private void alertDialogCloseApp() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.closeAppTitle)
                .setMessage(R.string.closeAppMessage)
                .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    // Switch UI to the given fragment
    void goToFragment(Fragment newFrag) {
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.fragment_holder, newFrag)
                .commit();


    }

    private boolean isSignedIn() {
        return (mGoogleApiClient != null && mGoogleApiClient.isConnected());
    }






    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Show sign-out button on main menu
        gameMenuFragment.setShowSignInButton(false);

        // Show "you are signed in" message on win screen, with no sign in button.
       // winFragment.setShowSignInButton(false);

        // Show "you are signed in" message on win screen, with no sign in button.
        // loseFragment.setShowSignInButton(false);
        // Set the greeting appropriately on main menu
        Player p = Games.Players.getCurrentPlayer(mGoogleApiClient);
        String displayName;
        if (p == null) {
            displayName = "???";
        } else {
            displayName = p.getDisplayName();
            String path = p.getIconImageUrl();
            gameMenuFragment.setGreeting(displayName);
            gameMenuFragment.setIconUser(path);

        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) {
            return;
        }

        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;
            if (!BaseGameUtils.resolveConnectionFailure(this, mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, getString(R.string.signin_other_error))) {
                mResolvingConnectionFailure = false;
            }
        }

        gameMenuFragment.setShowSignInButton(true);
        //winFragment.setShowSignInButton(true);
    }

    @Override
    public void onStartSingleGameRequested() {

        Intent i = new Intent(MainActivity.this, SingleGameActivity.class);
        startActivity(i);

    }

    @Override
    public void onStartMultiPlayGameRequest() {

        Intent i = new Intent(MainActivity.this, MultiPlayGameActivity.class);
        startActivity(i);

    }

    @Override
    public void onShowAchievementsRequested() {
        if (isSignedIn()) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient),
                    RC_UNUSED);
        } else {
            BaseGameUtils.makeSimpleDialog(this, getString(R.string.achievements_not_available)).show();
        }

    }

    @Override
    public void onShowLeaderboardsRequested() {
        if (isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(mGoogleApiClient),
                    RC_UNUSED);
        } else {
            BaseGameUtils.makeSimpleDialog(this, getString(R.string.leaderboards_not_available)).show();
        }

    }

    @Override
    public void onSignInButtonClicked() {
        // start the sign-in flow
        mSignInClicked = true;
        mGoogleApiClient.connect();


    }

    @Override
    public void onSignOutButtonClicked() {

        mSignInClicked = false;
        Games.signOut(mGoogleApiClient);
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        gameMenuFragment.setGreeting(getString(R.string.signed_out_greeting));
        gameMenuFragment.setIconUser(null);
        gameMenuFragment.setShowSignInButton(true);


    }


}
