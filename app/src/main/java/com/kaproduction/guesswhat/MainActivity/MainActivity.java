package com.kaproduction.guesswhat.MainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


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
import com.kaproduction.guesswhat.SingleGame.SingleGameFragment;

import static android.R.string.no;
import static android.R.string.yes;

public class MainActivity extends AppCompatActivity implements GameMenuFragment.Listener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, SingleGameFragment.Listener, WinFragment.Listener {
    // Fragments
    GameMenuFragment gameMenuFragment;
    LoseFragment loseFragment;
    WinFragment winFragment;

    SingleGameFragment singleGameFragment;

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

    public GameMenuFragment getGameMenuFragment() {
        return gameMenuFragment;
    }

    public LoseFragment getLoseFragment() {
        return loseFragment;
    }

    public WinFragment getWinFragment() {
        return winFragment;
    }

    public SingleGameFragment getSingleGameFragment() {
        return singleGameFragment;
    }

    // playing on hard mode?
    boolean mSingleGame = true;

    // achievements and scores we're pending to push to the cloud
    // (waiting for the user to sign in, for instance)
    AccomplishmentsOutbox mOutbox = new AccomplishmentsOutbox();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);View decorView = getWindow().getDecorView();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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

        singleGameFragment = new SingleGameFragment();

        gameMenuFragment.setListener(this);
        singleGameFragment.setListener(this);
        winFragment.setListener(this);




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
        winFragment.setShowSignInButton(false);

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

        // if we have accomplishments to push, push them
        if (!mOutbox.isEmpty()) {
            pushAccomplishments();
            Toast.makeText(this, getString(R.string.your_progress_will_be_uploaded),
                    Toast.LENGTH_LONG).show();
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
        winFragment.setShowSignInButton(true);
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

        winFragment.setShowSignInButton(true);


    }


    @Override
    public void onSinglePlayScore(float score, int count) {

        float singleGameScore = score;
        winFragment.setFinalScore(singleGameScore);
        winFragment.setExplanation(getString(R.string.singlePlay_explanation));

        // check for achievements
        checkForAchievements(singleGameScore, count);

        // update leaderboards
        updateLeaderboards(singleGameScore);

        // push those accomplishments to the cloud, if signed in
        pushAccomplishments();

        switchToFragment(winFragment);

    }

    void switchToFragment(Fragment newFrag) {
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.fragment_holder, newFrag)
                .commit();


    }

    void checkForAchievements(float requestedScore, int count) {
        // Check if each condition is met; if so, unlock the corresponding
        // achievement.
        switch (count){
            case 9:
                mOutbox.achievement_test10 =true;
                achievementToast("You have succeed in Level 1");
                break;

            case 8:
                mOutbox.achievement_test9 =true;
                achievementToast("You have succeed in Level 2");
                break;
            case 7:
                mOutbox.achievement_test8 =true;
                achievementToast("You have succeed in Level 3");
                break;
            case 6:
                mOutbox.achievement_test7 =true;
                achievementToast("You have succeed in Level 4");
                break;
            case 5:
                mOutbox.achievement_test6 =true;
                achievementToast("You have succeed in Level 5");
                break;
            case 4:
                mOutbox.achievement_test5 =true;
                achievementToast("You have succeed in Level 6");
                break;
            case 3:
                mOutbox.achievement_test4 =true;
                achievementToast("You have succeed in Level 7");
                break;
            case 2:
                mOutbox.achievement_test3 =true;
                achievementToast("You have succeed in Level 8");
                break;
            case 1:
                mOutbox.achievement_test2 =true;
                achievementToast("You have succeed in Level 9");
                break;
            case 0:
                mOutbox.achievement_test1 =true;
                achievementToast("You have succeed in Level 10");
                break;
        }


    }

        void achievementToast(String achievement) {
            // Only show toast if not signed in. If signed in, the standard Google Play
            // toasts will appear, so we don't need to show our own.
            if (!isSignedIn()) {
                Toast.makeText(this, achievement,
                        Toast.LENGTH_LONG).show();
            }

        }

    void pushAccomplishments() {

        if (!isSignedIn()) {
            // can't push to the cloud, so save locally
            mOutbox.saveLocal(this);
            return;
        }

        if(mOutbox.achievement_test10){
            Games.Achievements.increment(mGoogleApiClient,getString(R.string.achievement_test10),10);
            mOutbox.achievement_test10 = false;
        }

        if(mOutbox.achievement_test9){
            Games.Achievements.increment(mGoogleApiClient,getString(R.string.achievement_test9),9);
            mOutbox.achievement_test9 = false;
        }


        if(mOutbox.achievement_test8){
            Games.Achievements.increment(mGoogleApiClient,getString(R.string.achievement_test8),8);
            mOutbox.achievement_test8 = false;
        }


        if(mOutbox.achievement_test7){
            Games.Achievements.increment(mGoogleApiClient,getString(R.string.achievement_test7),7);
            mOutbox.achievement_test7 = false;
        }


        if(mOutbox.achievement_test6){
            Games.Achievements.increment(mGoogleApiClient,getString(R.string.achievement_test6),6);
            mOutbox.achievement_test6 = false;
        }


        if(mOutbox.achievement_test5){
            Games.Achievements.increment(mGoogleApiClient,getString(R.string.achievement_test5),5);
            mOutbox.achievement_test5 = false;
        }


        if(mOutbox.achievement_test4){
            Games.Achievements.increment(mGoogleApiClient,getString(R.string.achievement_test4),4);
            mOutbox.achievement_test4 = false;
        }


        if(mOutbox.achievement_test3){
            Games.Achievements.increment(mGoogleApiClient,getString(R.string.achievement_test3),3);
            mOutbox.achievement_test3 = false;
        }


        if(mOutbox.achievement_test2){
            Games.Achievements.increment(mGoogleApiClient,getString(R.string.achievement_test2),2);
            mOutbox.achievement_test2 = false;
        }


        if(mOutbox.achievement_test1){
            Games.Achievements.increment(mGoogleApiClient,getString(R.string.achievement_test1),1);
            mOutbox.achievement_test1 = false;
        }

        if (mOutbox.mSingleModeScore >= 0) {
            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.single_play_scoreboard),
                    (long) mOutbox.mSingleModeScore);
            mOutbox.mSingleModeScore = -1;
        }
        if (mOutbox.mMultiModeScore >= 0) {
            Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.multi_play_scoreboard),
                    (long) mOutbox.mMultiModeScore);
            mOutbox.mMultiModeScore = -1;
        }

    }

    void updateLeaderboards(float finalScore) {

        //eger single game ise ve oyun sonucu alinan scor buyukse
        if (mSingleGame && mOutbox.mSingleModeScore < finalScore) {
            mOutbox.mSingleModeScore = finalScore*100;
        } else if (!mSingleGame && mOutbox.mMultiModeScore < finalScore) {
            mOutbox.mMultiModeScore = finalScore*100;
        }
    }

    @Override
    public void onWinScreenDismissed() {
        switchToFragment(gameMenuFragment);

    }

    @Override
    public void onWinScreenSignInClicked() {

        mSignInClicked = true;
        mGoogleApiClient.connect();

    }

    class AccomplishmentsOutbox {
        //------------------------------------
        //Burasi tahmin et e ait
        boolean achievement_test1 = false;
        boolean achievement_test2 = false;
        boolean achievement_test3 = false;
        boolean achievement_test4 = false;
        boolean achievement_test5 = false;
        boolean achievement_test6 = false;
        boolean achievement_test7 = false;
        boolean achievement_test8 = false;
        boolean achievement_test9 = false;
        boolean achievement_test10 = false;

        float mSingleModeScore = -1;
        float mMultiModeScore = -1;

        //------------------------------------
        //Burasi tahmin et e ait

        boolean isEmpty() {
            return !achievement_test1 && !achievement_test2 && !achievement_test3 && !achievement_test4 && !achievement_test5 &&
                    !achievement_test6 &&!achievement_test7 && !achievement_test8 && !achievement_test9 && !achievement_test10 &&
                    mSingleModeScore == 0 && mSingleModeScore < 0 &&
                    mMultiModeScore < 0;
        }

        public void saveLocal(Context ctx) {
            /* TODO: This is left as an exercise. To make it more difficult to cheat,
             * this data should be stored in an encrypted file! And remember not to
             * expose your encryption key (obfuscate it by building it from bits and
             * pieces and/or XORing with another string, for instance). */
        }

        public void loadLocal(Context ctx) {
            /* TODO: This is left as an exercise. Write code here that loads data
             * from the file you wrote in saveLocal(). */
        }
    }

}
