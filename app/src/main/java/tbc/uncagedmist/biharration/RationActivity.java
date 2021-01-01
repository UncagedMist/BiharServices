package tbc.uncagedmist.biharration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

import java.util.Locale;

import am.appwise.components.ni.NoInternetDialog;
import tbc.uncagedmist.biharration.Common.Common;

public class RationActivity extends AppCompatActivity {

    AdView aboveBanner, bottomBanner;

    Button btnFormA,btnFormB,btnCheckStatus,btnRationAllot,btnDealer,btnHome,btnAadhaar,btnDownload,btnSearch;

    NoInternetDialog noInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_ration);

        noInternetDialog = new NoInternetDialog.Builder(RationActivity.this).build();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        btnFormA = findViewById(R.id.btnFormA);
        btnFormB = findViewById(R.id.btnFormB);

        btnCheckStatus = findViewById(R.id.btnStatus);
        btnRationAllot = findViewById(R.id.btnChkAllot);
        btnDealer = findViewById(R.id.btnDealer);

        btnHome = findViewById(R.id.btnHome);
        btnAadhaar = findViewById(R.id.btnAadhharLink);
        btnDownload = findViewById(R.id.btnDownload);
        btnSearch = findViewById(R.id.btnSearch);

        aboveBanner = findViewById(R.id.aboveBanner);
        bottomBanner = findViewById(R.id.belowBanner);

        AdRequest adRequest = new AdRequest.Builder().build();

        aboveBanner.loadAd(adRequest);
        bottomBanner.loadAd(adRequest);

        onclickImplement();

        adMethod();
    }

    private void adMethod() {
        aboveBanner.setAdListener(new AdListener() {
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

    private void onclickImplement() {
        btnFormA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RationActivity.this, "Will be added in the next update!", Toast.LENGTH_SHORT).show();
            }
        });

        btnFormB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RationActivity.this, "Will be added in the next update!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCheckStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RationActivity.this,ResultActivity.class);
                intent.putExtra("url", Common.CHECK_STATUS_URL);
                startActivity(intent);
                finish();
            }
        });

        btnRationAllot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RationActivity.this,ResultActivity.class);
                intent.putExtra("url", Common.CHECK_ALLOT_URL);
                startActivity(intent);
                finish();
            }
        });

        btnDealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RationActivity.this,ResultActivity.class);
                intent.putExtra("url", Common.DEALER_URL);
                startActivity(intent);
                finish();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RationActivity.this,ResultActivity.class);
                intent.putExtra("url", Common.OFFICIAL_URL);
                startActivity(intent);
                finish();
            }
        });

        btnAadhaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RationActivity.this,ResultActivity.class);
                intent.putExtra("url", Common.AADHAAR_URL);
                startActivity(intent);
                finish();
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RationActivity.this,ResultActivity.class);
                intent.putExtra("url", Common.DOWNLOAD_URL);
                startActivity(intent);
                finish();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RationActivity.this,ResultActivity.class);
                intent.putExtra("url", Common.SEARCH_URL);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadLocale()   {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);
    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }
}