package tbc.uncagedmist.biharration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button btnRation, btnVoter, btnLang,btnAwas;

    AdView bottomBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        btnRation = findViewById(R.id.btnRation);
        btnVoter = findViewById(R.id.btnVoter);
        btnLang = findViewById(R.id.btnLang);
        btnAwas = findViewById(R.id.btnAwas);

        bottomBanner = findViewById(R.id.belowBanner);

        AdRequest adRequest = new AdRequest.Builder().build();

        bottomBanner.loadAd(adRequest);

        btnRation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RationActivity.class));
            }
        });

        btnVoter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,VoterActivity.class));
            }
        });

        btnLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAppLang();
            }
        });

        btnAwas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AwasActivity.class));
            }
        });

        adMethod();
    }

    private void adMethod() {
        bottomBanner.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

    private void selectAppLang() {
        final String[] langList = {"English","हिंदी","اردو"};

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Choose Language / भाषा चुनें");
        alertDialog.setSingleChoiceItems(langList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    setLocale("en");
                    recreate();
                }
                else if (i == 1)    {
                    setLocale("hi");
                    recreate();
                }
                else if (i == 2)    {
                    setLocale("ur");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });
        alertDialog.create();
        alertDialog.show();
    }

    //set language
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        //shared prefs
        SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }

    //load language
    private void loadLocale()   {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);
    }
}