package tbc.uncagedmist.biharration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import java.util.Locale;

import tbc.uncagedmist.biharration.Common.Common;

public class AwasActivity extends AppCompatActivity {

    AdView adView;

    Button btnBene, btnAwas, btnSearch;

    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_awas);

        loadBanner();
        loadFull();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        btnBene = findViewById(R.id.btnBeneficiary);
        btnAwas = findViewById(R.id.btnAwas);
        btnSearch = findViewById(R.id.btnSearch);

        onClickImplementation();
    }

    private void loadFull() {
        interstitialAd = new InterstitialAd(
                this, "" +
                getString(R.string.FB_FULL)
        );

        // Create listeners for the Interstitial Ad
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e("TAG", "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e("TAG", "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e("TAG", "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d("TAG", "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d("TAG", "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d("TAG", "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

    private void loadBanner() {
        adView = new AdView(
                this,
                getString(R.string.FB_BANNER),
                AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

        AdListener adListener = new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        };

        // Request an ad
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
    }

    private void onClickImplementation() {
        btnBene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                }
                else {
                    Intent intent = new Intent(AwasActivity.this,ResultActivity.class);
                    intent.putExtra("url", Common.BENE_AWAS);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnAwas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                }
                else {
                    Intent intent = new Intent(AwasActivity.this,ResultActivity.class);
                    intent.putExtra("url", Common.LIST_AWAS);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                }
                else {
                    Intent intent = new Intent(AwasActivity.this,ResultActivity.class);
                    intent.putExtra("url", Common.SEARCH_AWAS);
                    startActivity(intent);
                    finish();
                }
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
        if (adView != null) {
            adView.destroy();
        }
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
}