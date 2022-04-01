package com.sitbbsr.sitcinemax;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    private AppUpdateManager mAppUpadteManager;

    Animation bottom, side;

    private static final int SPLASH_TIMER = 3400;
    private static final int RC_APP_UPDATE = 895;

    TextView powered;

    SessionManager manager;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        powered = findViewById(R.id.powered);

        mAppUpadteManager = AppUpdateManagerFactory.create(this);

        mAppUpadteManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    try {
                        mAppUpadteManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, MainActivity.this, RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    new Handler().postDelayed(() -> {

                        manager = new SessionManager(getApplicationContext());
                        if (manager.getUserLogin()) {
                            startActivity(new Intent(getApplicationContext(), UserDashBoard.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), UserSignUp.class));
                        }
                        finish();


                    }, SPLASH_TIMER);
                }
            }
        });
        //mAppUpadteManager.registerListener(installStateUpdatedListener);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        //Splash Animation
        side = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        powered.setAnimation(bottom);



    }

    InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(@NonNull InstallState installState) {
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                ShowCompletedUpdate();
            }
        }
    };

    private void ShowCompletedUpdate() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "New App is ready to be Installed!", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAppUpadteManager.completeUpdate();
            }
        });
        snackbar.show();
    }

    @Override
    protected void onStop() {
        //if(mAppUpadteManager != null) mAppUpadteManager.unregisterListener(installStateUpdatedListener);
        finishAffinity();
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RC_APP_UPDATE && resultCode != RESULT_OK) {
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
            finishAffinity();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        mAppUpadteManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    try {
                        mAppUpadteManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, MainActivity.this, RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        super.onResume();
    }
}